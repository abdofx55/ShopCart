package com.shopcart.Activities.MainActivity.Fragments.OnBoardingFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.shopcart.R;

class OnBoardingPagerAdapter extends PagerAdapter {

    private Context context;

    OnBoardingPagerAdapter(Context context) {
        this.context = context;
    }

    // ِArrays

    private int[] icons = {R.drawable.onboarding_shipping,
            R.drawable.onboarding_returns,
            R.drawable.onboarding_support,
            R.drawable.onboarding_payments
    };

    private int[] titles = {R.string.onboarding_title_1,
            R.string.onboarding_title_2,
            R.string.onboarding_title_3,
            R.string.onboarding_title_4
    };

    private int[] texts = {R.string.onboarding_text_1,
            R.string.onboarding_text_2,
            R.string.onboarding_text_3,
            R.string.onboarding_text_4
    };



    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.item_on_boarding, container, false);

        AppCompatImageView mIcon = view.findViewById(R.id.onboarding_img_icon);
        TextView mTitle = view.findViewById(R.id.onboarding_txt_title);
        TextView mText = view.findViewById(R.id.onboarding_txt_text);

        mIcon.setImageResource(icons[position]);
        mTitle.setText(context.getString(titles[position]));
        mText.setText(context.getString(texts[position]));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}