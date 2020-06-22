package com.shopcart.Activities.MainActivity.HomeFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopcart.Category;
import com.shopcart.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesAdapterViewHolder> {
    private Context context;
    private ArrayList<Category> list;

    CategoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoriesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_layout, parent, false);

        return new CategoriesAdapter.CategoriesAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CategoriesAdapterViewHolder holder, int position) {
        holder.bind(list , position);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    void setList(ArrayList<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class CategoriesAdapterViewHolder extends RecyclerView.ViewHolder {
        final AppCompatImageView photo;
        final TextView name;

        CategoriesAdapterViewHolder(@NonNull View view) {
            super(view);

            photo = view.findViewById(R.id.category_img_photo);
            name = view.findViewById(R.id.category_txt_name);
        }

        void bind(ArrayList<Category> list, int position){
            if (list != null && !list.isEmpty()) {
                Category category = list.get(position);

                String name = category.getName();
                String photoUrl = category.getPhotoUrl();
                Integer colorFilter = category.getColorFilter();

                if (name != null && !photoUrl.isEmpty()) {
                    this.name.setText(name);
                }

                if (photoUrl != null && !photoUrl.isEmpty()) {
                    Glide.with(context).load(category.getPhotoUrl()).into(photo);
                }

                if (photoUrl != null && !photoUrl.isEmpty()) {
                    photo.setColorFilter(new PorterDuffColorFilter(determineColorFilter(category.getColorFilter()), PorterDuff.Mode.SRC_OVER));                }


            }
        }

        int determineColorFilter(int colorFilter){
            switch (colorFilter) {
                case 1:
                    return Color.argb(100 , 255 , 0 , 0);  //Red
                case 2:
                    return Color.argb(100 , 0 , 255 , 0);  //Green
                case 3:
                default:
                    return Color.argb(100 , 0 , 0 , 255);  //Blue
            }
        }
    }
}
