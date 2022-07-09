package com.example.project_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DBNAME="bonneAffaire_db";


    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_CODE = "CODE";
    private static final String KEY_PRICE = "PRICE";
    private static final String KEY_QTE = "QUANTITY";
    private static final String KEY_IMG = "IMAGE";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_PANIER = "panier";


    public DBhelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username TEXT primary key, password TEXT)");


        myDB.execSQL("create Table "+TABLE_PRODUCTS+ "("+KEY_ID +" integer primary key autoincrement, "
                +KEY_CODE+" varchar(255) ,"
                +KEY_NAME+" varchar(255) ,"
                + KEY_PRICE+" float ,"
                + KEY_IMG +" blob)");

        myDB.execSQL("create Table "+TABLE_PANIER+ "("+KEY_ID +" integer primary key autoincrement, "

                +KEY_NAME+" varchar(255) ,"
                + KEY_PRICE+" float ,"
                + KEY_QTE+" integer ,"
                + KEY_IMG +" blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users" ); // drop table if exists
        myDB.execSQL("drop Table if exists "+TABLE_PRODUCTS ); // drop table if exists
        myDB.execSQL("drop Table if exists "+TABLE_PANIER ); // drop table if exists
        onCreate(myDB);
    }

    // insert users
    public  Boolean insertData (String username, String password){
        SQLiteDatabase myDB =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);

        long result= myDB.insert("users", null,contentValues);
        if(result==-1) return  false;
        else
            return true;

    }



// method check wether user exist fel table ou nn

    public Boolean check_username (String username)
    {
        SQLiteDatabase myDB =this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?" , new String[]{username}); // STAR HEDHA FHEMTOOCH
                if (cursor.getCount() > 0)
                {
                    return true;
                }
                else
                    return false ;

    }

    // method check wether user and password exist fel table ou nn

    public Boolean check_username_password (String username, String password)
    {
        SQLiteDatabase myDB =this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?" , new String[]{username, password}); // STAR HEDHA FHEMTOOCH
        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
            return false ;

    }






    //insert produit
    public void addProduct(Product produit) {

        SQLiteDatabase db = this.getWritableDatabase(); //declari db ta7tek copy lel base

        ContentValues values = new ContentValues();

        values.put(KEY_CODE, produit.getCode());
        values.put(KEY_NAME,produit.getName());
        values.put(KEY_PRICE,produit.getPrice());
        values.put(KEY_IMG, produit.getImage());

        db.insert(TABLE_PRODUCTS, null, values);

    }

    //ajouter au panier
    public void addPanier(Product produit) {

        SQLiteDatabase db = this.getWritableDatabase(); //declari db ta7tek copy lel base

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,produit.getName());
        values.put(KEY_PRICE,produit.getPrice());
        values.put(KEY_IMG, produit.getImage());
        values.put(KEY_QTE,produit.getPrice());

        db.insert(TABLE_PANIER, null, values);

    }

    // get all the products
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String select_query = "select * from " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) { // bech tetka2ad fama data fi kol star (lool)

            do {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String code = cursor.getString(cursor.getColumnIndex(KEY_CODE));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                float price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));

                Product produit = new Product(id, code,name,price, image);

                products.add(produit);

            } while (cursor.moveToNext());

        }

        return products;
    }

    // afficher panier
    public ArrayList<Product> affichePanier() {
        ArrayList<Product> panier = new ArrayList<>();

        String select_query = "select * from " + TABLE_PANIER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) { // bech tetka2ad fama data fi kol star (lool)

            do {

                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));

                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                float price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
                int qte = cursor.getInt(cursor.getColumnIndex(KEY_QTE));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));

                Product produit = new Product(id, name,price, qte, image);

                panier.add(produit);

            } while (cursor.moveToNext());

        }

        return panier;
    }




    public Product getProduct_by_id (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Product product_selected=null;
        String selectQuery= " select * from "+TABLE_PRODUCTS + " where id ="+id;

        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            int id_contact = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String code = cursor.getString(cursor.getColumnIndex(KEY_CODE));
            float phone = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));


            product_selected = new Product(id_contact,code, name, phone, image);

        }
        return product_selected;
    }
    //
    public Product getProduct_from_panier (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Product product_selected=null;
        String selectQuery= " select * from "+TABLE_PANIER + " where id ="+id;

        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            int id_contact = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            int qte = cursor.getInt(cursor.getColumnIndex(KEY_QTE));

            float price = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));


            product_selected = new Product(id_contact, name, price,qte, image);

        }
        return product_selected;
    }


    //-------------------------------get product by code a barre

    public Product getProduct_by_code_a_barre (String code) {
        SQLiteDatabase db = this.getReadableDatabase();

        Product product_selected=null;
        String selectQuery= " select * from "+TABLE_PRODUCTS + " where "+KEY_CODE+" ="+code;

        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String code_product = cursor.getString(cursor.getColumnIndex(KEY_CODE));
            float phone = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));


            product_selected = new Product(id,code_product, name, phone, image);

        }
        return product_selected;
    }







    // update product method

    public void update_Product (Product product){

        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_CODE, product.getCode());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_IMG, product.getImage());

        db.update(TABLE_PRODUCTS,values,"id=?",new String[]{String.valueOf(product.getId())});

    }

    // update panier
    public void update_panier (Product product){

        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_QTE, product.getQte_produit_client());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_IMG, product.getImage());

        db.update(TABLE_PANIER,values,"id=?",new String[]{String.valueOf(product.getId())});

    }




    public void delete_Product (int id){

        SQLiteDatabase db =this.getWritableDatabase();

        db.delete(TABLE_PRODUCTS,"id=?",new String[]{String.valueOf(id)});

    }


    // delet product from panier
    public void delete_from_panier (int id){

        SQLiteDatabase db =this.getWritableDatabase();

        db.delete(TABLE_PANIER,"id=?",new String[]{String.valueOf(id)});

    }


    public void vider_panier (){

        SQLiteDatabase db =this.getWritableDatabase();
        String selectQuery= "delete from " + TABLE_PANIER;

        db.execSQL(selectQuery);

    }



}
