package com.shopcart.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shopcart.R;
import com.shopcart.data.models.Product;

import java.util.ArrayList;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.FeaturedAdapterViewHolder> {
    private final Context context;
    private ArrayList<Product> list;

    public ProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductsAdapter.FeaturedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.vertical_product_layout, parent, false);

        return new FeaturedAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.FeaturedAdapterViewHolder holder, int position) {
        holder.bind(list, position);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class FeaturedAdapterViewHolder extends RecyclerView.ViewHolder {
        final AppCompatImageView photo;
        final TextView price;
        final TextView name;

        FeaturedAdapterViewHolder(@NonNull View view) {
            super(view);

            photo = view.findViewById(R.id.product_img_photo);
            price = view.findViewById(R.id.product_txt_price);
            name = view.findViewById(R.id.product_txt_name);
        }

        void bind(ArrayList<Product> list, int position) {
            if (list != null && !list.isEmpty()) {
                Product product = list.get(position);
                Glide.with(context).load(product.getPhotosUrl().get(0)).into(photo);
                price.setText(String.format(Locale.ENGLISH, "%.2f", product.getPrice()));
                name.setText(product.getName());
            }
        }
    }
}
