package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.shopcart.R
import com.shopcart.data.models.User
import com.shopcart.databinding.FragmentSignUpBinding
import com.shopcart.utilities.NetworkUtils.Companion.isConnected
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var fireStore: FirebaseFirestore

    companion object {
        // Flags to update UI
        private const val UPDATE_UI_WHEN_SIGNING_IN = 1
        private const val RESET_UI_WHEN_SIGNING_FAILED = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        binding.apply {
            signImgBack.setOnClickListener(this@SignUpFragment)
            signBtnSign.setOnClickListener(this@SignUpFragment)
            signBtnLogin.setOnClickListener(this@SignUpFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            // Back Button
            binding.signImgBack -> requireActivity().onBackPressed()
            binding.signBtnSign -> {
                if (isConnected((activity)!!)) {
                    updateUIWhenSigning(UPDATE_UI_WHEN_SIGNING_IN)
                    signUp()
                } else Toasty.warning(
                    requireContext(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
            binding.signBtnLogin -> {
                val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.signUpFragment) navController.navigate(
                    action
                )
            }
        }
    }

    private fun signUp() {
        firebaseAuth.createUserWithEmailAndPassword(
            binding.signLayoutBody.signEditEmail.text.toString().trim { it <= ' ' },
            binding.signLayoutBody.signEditPassword.text.toString().trim { it <= ' ' })
            .addOnSuccessListener {
                if (isAdded && it.user != null) {
                    sendVerification(it.user)
                }
            }.addOnFailureListener { e ->
                // First Check if the fragment still added
                if (isAdded) {
                    // If sign in fails, display a message to the user.
                    updateUIWhenSigning(RESET_UI_WHEN_SIGNING_FAILED)
                    Toasty.error(
                        requireContext(),
                        e.localizedMessage,
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
    }

    private fun sendVerification(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnSuccessListener { addUserToDatabase(user) }
            ?.addOnFailureListener { e ->
                user.delete()
                Toasty.error(
                    requireContext(),
                    "Failed to create a new account" + "\n" + e.localizedMessage,
                    Toast.LENGTH_LONG,
                    true
                ).show()
            }
    }

    private fun addUserToDatabase(user: FirebaseUser?) {
        if (user != null) {
            // Create a new user with a name
            val newUser = User()
            newUser.id = user.uid
            newUser.email =
                binding.signLayoutBody.signEditEmail.text.toString().trim { it <= ' ' }
            newUser.name = binding.signLayoutBody.signEditName.text.toString().trim { it <= ' ' }

            // Add a new document with a user ID
            fireStore.collection("users").document(user.uid).set(newUser)
                .addOnSuccessListener {
                    if (isAdded) {
                        Toasty.success(
                            (activity)!!,
                            getString(R.string.sign_toast_sign_success),
                            Toast.LENGTH_LONG,
                            true
                        ).show()
                        val action =
                            SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                        val navController =
                            NavHostFragment.findNavController(this@SignUpFragment)
                        val navDestination = navController.currentDestination
                        if (navDestination != null && navDestination.id == R.id.signUpFragment) navController.navigate(
                            action
                        )
                    }
                }.addOnFailureListener { e ->
                    user.delete()
                    Toasty.error(
                        requireContext(),
                        "Failed to create a new account" + "\n" + e.localizedMessage,
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
        }
    }

    private fun updateUIWhenSigning(flag: Int) {
        // First Check if the fragment still added
        if (isAdded) {
            //Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_SIGNING_IN) {
                // Update UI during sign up
                binding.apply {
                    signLayoutBody.signEditName.isEnabled = false
                    signLayoutBody.signEditEmail.isEnabled = false
                    signLayoutBody.signEditPassword.isEnabled = false
                    signBtnSign.isEnabled = false
                    signBtnSign.text = activity!!.getString(R.string.sign_signing)
                    signSpinKit.visibility = View.VISIBLE
                }

            } else if (flag == RESET_UI_WHEN_SIGNING_FAILED) {
                // Reset UI if sign up failed
                binding.apply {
                    signLayoutBody.signEditName.isEnabled = true
                    signLayoutBody.signEditEmail.isEnabled = true
                    signLayoutBody.signEditPassword.isEnabled = true
                    signBtnSign.isEnabled = true
                    signBtnSign.text = activity!!.getString(R.string.sign_sign)
                    signSpinKit.visibility = View.INVISIBLE
                }

            }
        }
    }
}