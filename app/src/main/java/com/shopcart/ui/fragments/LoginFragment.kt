package com.shopcart.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.shopcart.R
import com.shopcart.databinding.FragmentLoginBinding
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.NetworkUtils.Companion.isConnected
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.apply {
            loginImgBack.setOnClickListener(this@LoginFragment)
            loginBtnLogin.setOnClickListener(this@LoginFragment)
            loginBtnSign.setOnClickListener(this@LoginFragment)
            loginBtnForgot.setOnClickListener(this@LoginFragment)
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            // Back Button
            binding.loginImgBack -> requireActivity().onBackPressed()

            // Login Button
            binding.loginBtnLogin -> {
                // first check is network connected
                if (isConnected(activity!!)) {
                    updateUIWhenLogging(UPDATE_UI_WHEN_LOGGING)
                    Handler(Looper.getMainLooper()).postDelayed({
                        // Log in
                        login()
                    }, 300)
                } else Toasty.warning(
                    activity!!,
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
            // Sign Up Button
            binding.loginBtnSign -> {
                val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.loginFragment) navController.navigate(
                    action
                )
            }
            // Forgot Password Button
            binding.loginBtnForgot -> {
                val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.loginFragment) navController.navigate(
                    action
                )
            }
        }
    }

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(
            binding.loginLayoutBody.loginEditEmail.text.toString().trim(),
            binding.loginLayoutBody.loginEditPassword.text.toString().trim()
        )
            .addOnSuccessListener { authResult ->
                if (isAdded) {
                    authResult.user?.let {
                        if (it.isEmailVerified) {
                            // Sign in success, Download Data then update UI with the signed-in user's information1
                            viewModel.getData()
                            Toasty.success(
                                requireContext(),
                                getString(R.string.login_toast_login_success),
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                            openHomeFragment()

                        } else {
                            updateUIWhenLogging(RESET_UI_WHEN_LOGGING_FAILED)
                            Toasty.info(
                                activity!!,
                                getString(R.string.login_toast_please_verify),
                                Toast.LENGTH_LONG,
                                true
                            ).show()
                        }
                    }
                }
            }.addOnFailureListener { e ->
                // First Check if the fragment still added
                if (isAdded) {
                    // If sign in fails, display a message to the user.
                    updateUIWhenLogging(RESET_UI_WHEN_LOGGING_FAILED)
                    Toasty.error(
                        requireContext(),
                        "${e.localizedMessage} ",
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
    }

    private fun openHomeFragment() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        val navController =
            NavHostFragment.findNavController(this@LoginFragment)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.loginFragment) navController.navigate(
            action
        )
    }

    private fun updateUIWhenLogging(flag: Int) {
        // First Check if the fragment still added
        if (isAdded) {
            // Second Check Flag whether update or reset UI
            if (flag == UPDATE_UI_WHEN_LOGGING) {
                // Update UI during login
                binding.loginLayoutBody.loginEditEmail.isEnabled = false
                binding.loginLayoutBody.loginEditPassword.isEnabled = false
                binding.loginBtnLogin.isEnabled = false
                binding.loginBtnLogin.text = activity!!.getString(R.string.login_logging)
                binding.loginSpinKit.visibility = View.VISIBLE
            } else if (flag == RESET_UI_WHEN_LOGGING_FAILED) {
                // Reset UI if login failed
                binding.loginLayoutBody.loginEditEmail.isEnabled = true
                binding.loginLayoutBody.loginEditPassword.isEnabled = true
                binding.loginBtnLogin.isEnabled = true
                binding.loginBtnLogin.text = activity!!.getString(R.string.login_login)
                binding.loginSpinKit.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        // Flags to update UI
        private const val UPDATE_UI_WHEN_LOGGING = 1
        private const val RESET_UI_WHEN_LOGGING_FAILED = 0
    }
}