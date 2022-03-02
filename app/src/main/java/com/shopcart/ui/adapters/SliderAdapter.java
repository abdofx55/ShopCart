package com.shopcart.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.shopcart.Banner;
import com.shopcart.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private final Context context;
    private ArrayList<Banner> list;

    public SliderAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        if (list != null)
             return list.size();
        return 0;
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
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        AppCompatImageView banner = view.findViewById(R.id.banner_img_banner);

        if (list != null && list.size() != 0) {
            Glide.with(context).load(list.get(position).getPhotoUrl()).into(banner);
        }

        container.addView(view);
        if (position == getCount() - 1) {
            position = 0;
        }

        return view;
    }

    public void setList(ArrayList<Banner> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
