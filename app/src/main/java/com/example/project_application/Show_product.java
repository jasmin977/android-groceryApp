package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Show_product extends AppCompatActivity {

    static float soum_9adhya;
    static ArrayList<String> facture_products_names = new ArrayList<>();
    static  ArrayList<String> facture_products_prices = new ArrayList<>();
    static  ArrayList<String> facture_products_qte= new ArrayList<>();
    static  ArrayList<String> facture_products_total= new ArrayList<>();
    DBhelper db;
    TextView name;
EditText quantitee;
    TextView price;
    ImageView product_pic;
    Button add, supp;

   int product_qte;
    byte[] image = null;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        quantitee= (EditText) findViewById(R.id.qte);


        db = new DBhelper(this);


    // taw chne5dou lproduit mel intent
        Product product = Parcels.unwrap(getIntent().getParcelableExtra("produit_scanee"));



        name = (TextView) findViewById(R.id.product_namee);

        price = (TextView) findViewById(R.id.product_pricee);


        product_pic = (ImageView) findViewById(R.id.imageeView);


        add = (Button) findViewById(R.id.add_btn);
        supp = (Button) findViewById(R.id.add_supp);


        // lena chn7adhrou les elements du factures------------------------------------------
      //  facture_products_names = new ArrayList<>();
      //  facture_products_prices = new ArrayList<>();
      //  facture_products_qte = new ArrayList<>();
      //  facture_products_total = new ArrayList<>();


        //-----------------------------------------------------------------------------------



        name.setText(product.getName());
        price.setText(product.getPrice()+"");

        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        product_pic.setImageBitmap(bitmap);
        image = getBytes(bitmap);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lenna chtabda te7seb fel 9adhya
                product_qte = Integer.parseInt(quantitee.getText().toString());
                soum_9adhya += product.getPrice() * product_qte;

                // tahdhir lfacture ladies and gentelmans :D ----------------------------------------

                facture_products_names.add(product.getName());
                facture_products_prices.add(String.valueOf(product.getPrice()));
                facture_products_qte.add(String.valueOf(product_qte));
                facture_products_total.add(String.valueOf(product.getPrice() * product_qte));
                //------------------------------------------------------------------------------------


               // Toast.makeText(Show_product.this, String.valueOf(soum_9adhya), Toast.LENGTH_LONG).show();

                StyleableToast.makeText(Show_product.this, "Successfully added",R.style.add).show();

                Intent intent= new Intent(Show_product.this,Scan_product.class);
                intent.putExtra("SOMME", soum_9adhya);
                startActivity(intent);


                // al hamdou lellahhh <3
            }
        });


    }


    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }




    public void supp_de_panier(View view) {

        StyleableToast.makeText(Show_product.this, "Canceled!",R.style.cancel).show();
        Intent intent= new Intent(Show_product.this,Scan_product.class);

        startActivity(intent);

    }
}