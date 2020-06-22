package com.shopcart.Activities.FirstScreenActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.shopcart.R;
import com.shopcart.databinding.ActivityFirstScreenBinding;

public class FirstScreenActivity extends AppCompatActivity {
    ActivityFirstScreenBinding binding;
    public int abdo = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_first_screen);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.first_frame_fragment_container, new WelcomeFragment() )
                .commit();
    }

}
