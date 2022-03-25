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
import com.shopcart.databinding.FragmentSignUpBinding
import com.shopcart.ui.viewModels.MainViewModel
import com.shopcart.utilities.NetworkUtils.Companion.isConnected
import com.shopcart.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

private const val TAG = "Sign Up Fragment"

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: MainViewModel by viewModels()


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

        viewModel.authStatus.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    updateUIWhenSigning(UPDATE_UI_WHEN_SIGNING_IN)
                }
                is Resource.Success -> {
                    Toasty.success(
                        requireContext(),
                        getString(R.string.sign_toast_sign_success),
                        Toast.LENGTH_LONG,
                        true
                    ).show()

                    openSignInFragment()

                }
                is Resource.Error -> {
                    updateUIWhenSigning(RESET_UI_WHEN_SIGNING_FAILED)
                    Toasty.error(
                        requireContext(),
                        "${it.message}",
                        Toast.LENGTH_SHORT,
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
            binding.signImgBack -> requireActivity().onBackPressed()

            // Sign Up Button
            binding.signBtnSign -> {
                // check is network connected
                if (isConnected(requireContext())) {
                    viewModel.signUp(
                        binding.signLayoutBody.signEditEmail.text.toString().trim(),
                        binding.signLayoutBody.signEditPassword.text.toString().trim()
                    )
                } else Toasty.warning(
                    requireContext(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }

            // Go to Sign In Button
            binding.signBtnLogin -> {
                openSignInFragment()
            }
        }
    }

    private fun openSignInFragment() {
        val action =
            SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        val navController =
            NavHostFragment.findNavController(this@SignUpFragment)
        val navDestination = navController.currentDestination
        if (navDestination != null && navDestination.id == R.id.signUpFragment) navController.navigate(
            action
        )
    }

    private fun updateUIWhenSigning(flag: Int) {
        //Check Flag whether update or reset UI
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