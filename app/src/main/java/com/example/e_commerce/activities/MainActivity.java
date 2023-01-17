package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.ProductAdapter;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.models.Category;
import com.example.e_commerce.models.Product;
import com.example.e_commerce.utils.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
CategoryAdapter categoryAdapter;
ArrayList<Category> categories;

ProductAdapter productAdapter;
ArrayList<Product> products;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


      initCategories();
      initProducts();
      initSlider();

    }

    private void initSlider() {
        binding.carousel.addData(new CarouselItem("https://i.pinimg.com/564x/39/74/6e/39746eea29ef15a08a05e9365c2d6be4.jpg","Some captions here"));
        binding.carousel.addData(new CarouselItem("https://i.pinimg.com/236x/f9/f8/48/f9f848b6a9e164a865849b18e3dae29f.jpg","Some captions here"));
        binding.carousel.addData(new CarouselItem("https://i.pinimg.com/564x/90/50/f2/9050f210aae7812b97eae66666a160a6.jpg","Some captions here"));
        binding.carousel.addData(new CarouselItem("https://i.pinimg.com/564x/40/fc/13/40fc13aced8c7e575e585cf5e765cb56.jpg","Some captions here"));
        binding.carousel.addData(new CarouselItem("https://i.pinimg.com/564x/6a/ee/f6/6aeef641a93514f541415c80fa88aee9.jpg","Some captions here"));
    }

    void initCategories(){

        categories=new ArrayList<>();

        categoryAdapter=new CategoryAdapter(this,categories);
        getCategories();
        GridLayoutManager layoutManager=new GridLayoutManager(this,4);
        binding.categroiesList.setLayoutManager(layoutManager);
        binding.categroiesList.setAdapter(categoryAdapter);
    }
    void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject mainObj=new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray categoriesArray=mainObj.getJSONArray("categories");
                        for (int i=0;i<categoriesArray.length();i++){
                            JSONObject object=categoriesArray.getJSONObject(i);
                            Category category=new Category(
                                    object.getString("name"),
                                   Constants.CATEGORIES_IMAGE_URL+ object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();

                    }else{

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    void  getProducts(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url=Constants.GET_PRODUCTS_URL + "?count=15";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject Object=new JSONObject(response);
                    if(Object.getString("status").equals("success")){
                        JSONArray productsArray=Object.getJSONArray("products");
                        for (int i=0;i<productsArray.length();i++){
                            JSONObject childObj=productsArray.getJSONObject(i);
                            Product product=new Product(
                                    childObj.getString("name"),
                                  Constants.PRODUCTS_IMAGE_URL+  childObj.getString("image"),
                                    childObj.getString("status"),
                                    childObj.getDouble("price"),
                                    childObj.getDouble("price_discount"),
                                    childObj.getInt("stock"),
                                    childObj.getInt("id")
                            );
                            products.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    void initProducts(){

        products=new ArrayList<>();


        productAdapter=new ProductAdapter(this,products);
        getProducts();
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);


    }
}