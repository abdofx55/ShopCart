package com.shopcart.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shopcart.R
import com.shopcart.databinding.FragmentForgotPasswordBinding
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.NetworkUtils.Companion.isConnected
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: MainViewModel by viewModels()


    companion object {
        // Flags to update UI
        private const val UPDATE_UI_WHEN_RESETTING = 1
        private const val RESET_UI_WHEN_RESETTING_FAILED = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)

        binding.apply {
            binding.forgotImgBack.setOnClickListener(this@ForgotPasswordFragment)
            binding.forgotBtnForgot.setOnClickListener(this@ForgotPasswordFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            // Back Button
            binding.forgotImgBack -> requireActivity().onBackPressed()
            // Forgot Button
            binding.forgotBtnForgot -> {
                // first check is network connected
                if (isConnected(requireContext())) {
                    updateUIWhenResetting(UPDATE_UI_WHEN_RESETTING)
                    Handler().postDelayed({ reset() }, 300)
                } else Toasty.warning(
                    requireContext(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
        }
    }

    private fun reset() {
        viewModel.firebaseAuth.sendPasswordResetEmail(
            binding.forgotEditEmail.text.toString().trim()
        )
            .addOnSuccessListener {
                if (isAdded) {
                    Toasty.success(
                        requireContext(),
                        getString(R.string.forgot_toast_reset_success),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                    requireActivity().onBackPressed()
                }
            }.addOnFailureListener { e ->
                if (isAdded) {
                    updateUIWhenResetting(RESET_UI_WHEN_RESETTING_FAILED)
                    Toasty.error(
                        requireContext(),
                        e.localizedMessage,
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
    }

    private fun updateUIWhenResetting(flag: Int) {
        // First Check if the fragment still added
        if (isAdded) {
            // Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_RESETTING) {
                // Update UI during reset password
                binding.forgotEditEmail.isEnabled = false
                binding.forgotBtnForgot.isEnabled = false
                binding.forgotBtnForgot.text = activity!!.getString(R.string.forgot_resetting)
                binding.forgotSpinKit.visibility = View.VISIBLE
            } else if (flag == RESET_UI_WHEN_RESETTING_FAILED) {
                // Reset UI if login failed
                binding.forgotEditEmail.isEnabled = true
                binding.forgotBtnForgot.isEnabled = true
                binding.forgotBtnForgot.text = activity!!.getString(R.string.forgot_forgot)
                binding.forgotSpinKit.visibility = View.INVISIBLE
            }
        }
    }
}