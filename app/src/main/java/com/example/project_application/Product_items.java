package com.example.project_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Product_items extends AppCompatActivity { // activity taffichilk les product kol

    ListView productsList;
    Button btnAdd;

    DBhelper db;








    //-----------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_items);

        db = new DBhelper(this);

        productsList = (ListView) findViewById(R.id.products_List);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Product_items.this, Add_product.class);
                startActivity(intent);

            }
        });

// ki teclicki 3la item fel liste yhezek lel update mte3oo
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product selected_contact = (Product) parent.getItemAtPosition(position);

                Intent intent = new Intent(Product_items.this, Update_product.class);

                intent.putExtra("id", selected_contact.getId());

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() { //  bech kol mataddi product yedzed tool mech lezem to5ro w t3awed tod5ol 5ater kn t7otou fel creat ya3ni ella met7ell lapp
        super.onResume();

        ArrayList<Product> Products = db.getAllProducts();

        ProductAdapter ProductAdapter = new ProductAdapter(this, R.layout.product_items, Products);


        productsList.setAdapter(ProductAdapter);

        // productsList hiya elist view eli fel page xml lkol

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // trakablek lmenu fel action bar

        getMenuInflater().inflate(R.menu.signout, menu);  // inflate trakabhoolek

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_quit:

                Intent intent = new Intent(Product_items.this, Login_activity.class);
                startActivity(intent);
                Toast.makeText(Product_items.this, "BYEE ", Toast.LENGTH_LONG).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
