package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.shopcart.R
import com.shopcart.databinding.FragmentSignInBinding
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.NetworkUtils.Companion.isConnected
import com.shopcart.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

private const val TAG = "Sign In Fragment"

@AndroidEntryPoint
class SignInFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        binding.apply {
            loginImgBack.setOnClickListener(this@SignInFragment)
            loginBtnLogin.setOnClickListener(this@SignInFragment)
            loginBtnSign.setOnClickListener(this@SignInFragment)
            loginBtnForgot.setOnClickListener(this@SignInFragment)
        }

        viewModel.signInStatus.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    updateUIWhenLogging(UPDATE_UI_WHEN_LOGGING)
                }

                is Resource.Success -> {
                    // Sign in success, Download Data then update UI with the signed-in user's information1
                    viewModel.getData()
                    Toasty.success(
                        requireContext(),
                        getString(R.string.login_toast_login_success),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()

                    openHomeFragment()
                }

                is Resource.Error -> {
                    updateUIWhenLogging(RESET_UI_WHEN_LOGGING_FAILED)
                    Toasty.info(
                        requireContext(),
                        "${it.message}",
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
        })

        return binding.root
    }

    override fun onClick(v: View) {
        when (v) {
            // Back Button
            binding.loginImgBack -> requireActivity().onBackPressed()

            // Login Button
            binding.loginBtnLogin -> {
                // check is network connected
                if (isConnected(requireContext())) {
                    viewModel.signIn(
                        binding.loginLayoutBody.loginEditEmail.text.toString().trim(),
                        binding.loginLayoutBody.loginEditPassword.text.toString().trim()
                    )

                } else Toasty.warning(
                    requireContext(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
            // Go to Sign Up Button
            binding.loginBtnSign -> {
                val action = SignInFragmentDirections.actionLoginFragmentToSignUpFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.loginFragment) navController.navigate(
                    action
                )
            }
            // Forgot Password Button
            binding.loginBtnForgot -> {
                val action = SignInFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
                val navController = NavHostFragment.findNavController(this)
                val navDestination = navController.currentDestination
                if (navDestination != null && navDestination.id == R.id.loginFragment) navController.navigate(
                    action
                )
            }
        }
    }

    private fun openHomeFragment() {
        val action =
            SignInFragmentDirections.actionLoginFragmentToHomeFragment()
        val navController =
            NavHostFragment.findNavController(this@SignInFragment)
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