package com.example.jaja;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.jaja.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> listProduct;

    public ProductAdapter(Context context, List<Product> listProduct) {
        super(context, 0, listProduct);
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Product product = listProduct.get(position);

        ImageView imageView = convertView.findViewById(R.id.image_avt_product);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);

        Glide.with(context)
                .load(product.getImagePath())
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCorners(20)
                        )))
                .into(imageView);

        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(product.getName(), product.getImagePath());
                    }
                }
        );

        productName.setText(product.getName());
//        productName.setTextColor(context.getResources().getColor(R.color.white_87));
        productName.setTextColor(context.getColor(R.color.white_87));
        productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThirdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("product",product);
                intent.putExtras(bundle);
                startActivity(context,intent,bundle);
            }
        });

        productPrice.setText("$ " + product.getPrice());

        return convertView;
    }

    private void showDialog(String productName, String imagePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(productName);

        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        ImageView imageView = dialogView.findViewById(R.id.dialog_image);
        ImageView iconClose = dialogView.findViewById(R.id.icon_close);
        TextView productNameDialog = dialogView.findViewById(R.id.name_product_dialog);

        // Load the image into the ImageView using Glide
        Glide.with(context)
                .load(imagePath)
                .apply(new RequestOptions().transform(
                        new MultiTransformation<>(
                                new CenterCrop()

                                )))
                .into(imageView);

        builder.setView(dialogView);
        productNameDialog.setText(productName);
        AlertDialog dialog = builder.create();
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());


        dialog.show();
    }


}
