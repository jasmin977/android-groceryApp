package com.example.project_application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductAdapter extends ArrayAdapter<Product> {

    Context context0; // context global
    int resource0;



    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context0 = context;
        this.resource0 = resource;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //LayoutInflater yrakable style xml 3la objet convert view


        convertView = LayoutInflater.from(context0).inflate(resource0, parent, false  );

        TextView poduct_name = (TextView)convertView.findViewById(R.id.poduct_name_v);
        TextView poduct_code = (TextView)convertView.findViewById(R.id.poduct_code_v);
        TextView poduct_price = (TextView)convertView.findViewById(R.id.poduct_price_v);
        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.img_Pr_v);



        Product currentProduct = getItem(position);

        poduct_name.setText(currentProduct.getName());
        poduct_code.setText(String.valueOf(currentProduct.getCode()));
        poduct_price.setText(String.valueOf(currentProduct.getPrice()));


        Bitmap bitmap = BitmapFactory.decodeByteArray(currentProduct.getImage(), 0, currentProduct.getImage().length);
        imgProduct.setImageBitmap(bitmap);


        return convertView;
    }
}
