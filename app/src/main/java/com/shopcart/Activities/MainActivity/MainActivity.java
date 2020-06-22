package com.shopcart.Activities.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.shopcart.Activities.MainActivity.HomeFragment.HomeFragment;
import com.shopcart.R;
import com.shopcart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_fragment_container, new HomeFragment() )
                .commit();

    }


}
