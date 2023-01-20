package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CartAdapter;
import com.example.e_commerce.databinding.ActivityCartBinding;
import com.example.e_commerce.models.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class Cart_Activity extends AppCompatActivity {
ActivityCartBinding binding;
CartAdapter adapter;
ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         products=new ArrayList<>();
         Cart cart= TinyCartHelper.getCart();
         for (Map.Entry<Item,Integer> item:cart.getAllItemsWithQty().entrySet()){
             Product product=(Product) item.getKey();
             int quantity=item.getValue();
             product.setQuantity(quantity);
             products.add(product);
         }

         adapter=new CartAdapter(this, products, new CartAdapter.CartClickListener() {
             @Override
             public void onQuantityChanged() {
                 binding.subTotal.setText(String.format("PKR %.2f ",cart.getTotalPrice()));
             }
         });


         LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,linearLayoutManager.getOrientation());

         binding.cartlist.setLayoutManager(linearLayoutManager);
         binding.cartlist.addItemDecoration(itemDecoration);

         binding.cartlist.setAdapter(adapter);

//         binding.subTotal.setText("PKR " + String.valueOf(cart.getTotalPrice()));

         binding.subTotal.setText(String.format("PKR %.2f ",cart.getTotalPrice()));

binding.continueBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(Cart_Activity.this,CheckoutActivity.class));
    }
});


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}