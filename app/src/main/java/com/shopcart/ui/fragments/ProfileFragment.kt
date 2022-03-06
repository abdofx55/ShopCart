package com.shopcart.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shopcart.R
import com.shopcart.data.models.User
import com.shopcart.databinding.FragmentProfileBinding
import com.shopcart.ui.viewModels.MainViewModel
import es.dmoral.toasty.Toasty
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore
    private val viewModel: MainViewModel by viewModels()

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isDataChanged()) binding.profileBtnSave.visibility =
                View.VISIBLE else binding.profileBtnSave.visibility = View.INVISIBLE
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        getUserProfile()

        binding.apply {
            profileImgBack.setOnClickListener(this@ProfileFragment)
            profileBtnSave.setOnClickListener(this@ProfileFragment)
        }

        return binding.root
    }

    private fun getUserProfile() {
        viewModel.user.value?.let { updateUi(it) }
    }

    private fun updateUi(user: User) {
        binding.apply {
            profileBody.profileEditName.setText(if (user.name != null) user.name else "")
            profileBody.profileEditAddress.setText(if (user.address != null) user.address else "")
            profileBody.profileEditCity.setText(if (user.city != null) user.city else "")
            profileBody.profileEditGender.setText(user.gender.toString())
            profileBody.profileEditEmail.setText(if (user.email != null) user.email else "")
            profileBody.profileEditMobile.setText(if (user.mobile != null) user.mobile else "")

            // Add text watcher to enable save button if text changed
            profileBody.profileEditName.addTextChangedListener(textWatcher)
            profileBody.profileEditAddress.addTextChangedListener(textWatcher)
            profileBody.profileEditCity.addTextChangedListener(textWatcher)
            profileBody.profileEditGender.addTextChangedListener(textWatcher)
            profileBody.profileEditEmail.addTextChangedListener(textWatcher)
            profileBody.profileEditMobile.addTextChangedListener(textWatcher)
        }
    }

    private fun isDataChanged(): Boolean {
        return !(viewModel.user.value?.name == binding.profileBody.profileEditName.text.toString()
            .trim()
                && viewModel.user.value?.address == binding.profileBody.profileEditAddress.text.toString()
            .trim()
                && viewModel.user.value?.city == binding.profileBody.profileEditCity.text.toString()
            .trim()
                && viewModel.user.value?.gender.toString() == binding.profileBody.profileEditGender.text.toString()
            .trim()
                && viewModel.user.value?.email == binding.profileBody.profileEditEmail.text.toString()
            .trim()
                && viewModel.user.value?.mobile == binding.profileBody.profileEditMobile.text.toString()
            .trim()
                )
    }

    override fun onClick(v: View) {
        when (v) {
            binding.profileImgBack -> requireActivity().onBackPressed()
            binding.profileBtnSave -> {
                viewModel.user.value?.name =
                    binding.profileBody.profileEditName.text.toString().trim()
                viewModel.user.value?.address =
                    binding.profileBody.profileEditAddress.text.toString().trim()
                viewModel.user.value?.city =
                    binding.profileBody.profileEditCity.text.toString().trim()
                viewModel.user.value?.gender =
                    binding.profileBody.profileEditGender.text.toString().trim()
                        .toInt()
                viewModel.user.value?.email =
                    binding.profileBody.profileEditEmail.text.toString().trim()
                viewModel.user.value?.mobile =
                    binding.profileBody.profileEditMobile.text.toString().trim()
                firebaseAuth.currentUser?.let {
                    viewModel.user.value?.let { it1 ->
                        firestore.collection("users").document(it.uid).set(
                            it1
                        )
                    }
                }
                Toasty.success(requireContext(), "Saved", Toasty.LENGTH_SHORT, true).show()
                requireActivity().onBackPressed()
            }

        }
    }
}