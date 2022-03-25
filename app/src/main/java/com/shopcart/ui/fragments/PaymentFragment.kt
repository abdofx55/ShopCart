package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shopcart.R
import com.shopcart.databinding.FragmentWelcomeBinding
import com.shopcart.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "Payment Fragment"

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)

        return binding.root
    }
}