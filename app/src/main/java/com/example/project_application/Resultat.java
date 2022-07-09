package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.ACTION_DIAL;
import static com.example.project_application.Show_product.facture_products_names;
import static com.example.project_application.Show_product.facture_products_prices;
import static com.example.project_application.Show_product.facture_products_qte;
import static com.example.project_application.Show_product.facture_products_total;

public class Resultat extends AppCompatActivity {

    static TextView resultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);




        ListView list_names= (ListView) findViewById(R.id.fact_names) ;
        ListView list_prices= (ListView) findViewById(R.id.fact_prices) ;
        ListView list_qte= (ListView) findViewById(R.id.fact_qte) ;
        ListView list_total= (ListView) findViewById(R.id.fact_total) ;
        resultat= (TextView) findViewById(R.id.rest);


        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,facture_products_names);
        list_names.setAdapter(adapter1);

        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,facture_products_prices);
        list_prices.setAdapter(adapter2);


        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,facture_products_qte);
        list_qte.setAdapter(adapter3);


        ArrayAdapter<String> adapter4= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,facture_products_total);
        list_total.setAdapter(adapter4);



        // ne5ou lresultat final ml intent eli jeya ln scan product
        Float result = getIntent().getFloatExtra("RES",1f);
        resultat.setText(String.valueOf("TOTAL " +result));


    }





    // my options menu


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item3:


                String number = "21900520";

                Uri uri = Uri.parse("tel:" + number);
                Intent intent = new Intent(ACTION_DIAL, uri);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    startActivity(intent);

                }
                startActivity(intent);

                return true;
            case R.id.item4:

                bonneAffaireMarket();
                return true;
            case R.id.item5:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void signout() {

        facture_products_names.clear();
        facture_products_prices.clear();
        facture_products_qte.clear();
        facture_products_total.clear();
        Intent intent = new Intent(this, Login_activity.class);

        startActivity(intent);
        Toast.makeText(this, "Sign out ", Toast.LENGTH_SHORT).show();



    }

    private void bonneAffaireMarket() {
        Uri uri = Uri.parse("https://www.facebook.com/BonneAffaireMarket/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}