package com.shopcart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shopcart.R
import com.shopcart.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        return binding.root
    }
}