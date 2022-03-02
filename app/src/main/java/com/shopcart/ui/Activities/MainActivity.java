package com.shopcart.ui.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shopcart.R;
import com.shopcart.Utilities.Tasks;
import com.shopcart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tasks.hideStatusBar(this);
        Tasks.colorNavigationBar(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
