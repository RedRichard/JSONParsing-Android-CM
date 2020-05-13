package com.ricardohg.ejercicio3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductItem extends RecyclerView.Adapter<AdapterProductItem.ViewHolderDatos> {

    // Product data:
    ArrayList<Product> productList;

    // Image:
    ImageView platformImage;

    private Context context;

    public AdapterProductItem(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public AdapterProductItem.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        context = parent.getContext();

        return new AdapterProductItem.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductItem.ViewHolderDatos holder, int position) {
        holder.setItem(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView productNameTV, productPriceTV, productProviderTV, productDeliveryTV;

        ImageView productImageIV;

        String productID;

        Product auxProduct;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            productNameTV = (TextView) itemView.findViewById(R.id.productNameTV);
            productPriceTV = (TextView) itemView.findViewById(R.id.productPriceTV);
            productProviderTV = (TextView) itemView.findViewById(R.id.productProviderTV);
            productDeliveryTV = (TextView) itemView.findViewById(R.id.productDeliveryTV);

            productImageIV = (ImageView) itemView.findViewById(R.id.productImageIV);

            itemView.setOnClickListener(this);
        }

        public void setItem(Product product) {
            Picasso.with(context)
                    .load(product.getImageURL())
                    .into(productImageIV);

            auxProduct = product;
            productNameTV.setText(product.getName());
            productPriceTV.setText(product.getPrice());
            productProviderTV.setText(product.getProvider());
            productDeliveryTV.setText(product.getDelivery());

            productID = product.getId();
        }


        @Override
        public void onClick(View v) {
            //Toast.makeText(context, auxProduct.getId(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("productData", auxProduct);

            context.startActivity(intent);

            //Intent intent = new Intent(context, EditItemActivity.class);

            //intent.putExtra("auxGame", auxGame);

            //((Activity) context).startActivityForResult(intent, 2);
            //context.startActivity(intent);
        }
    }
}
