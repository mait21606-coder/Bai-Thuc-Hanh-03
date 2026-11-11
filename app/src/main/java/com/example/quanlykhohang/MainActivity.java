package com.example.quanlykhohang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalProducts, tvTotalValue, tvLowStockCount;
    private ListView lvProducts;
    private Button btnAddProduct;
    private DatabaseHelper dbHelper;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalProducts = findViewById(R.id.tvTotalProducts);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvLowStockCount = findViewById(R.id.tvLowStockCount);
        lvProducts = findViewById(R.id.lvProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        dbHelper = new DatabaseHelper(this);

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", selectedProduct.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<Product> productList = dbHelper.getAllProducts();
        adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        tvTotalProducts.setText(String.valueOf(productList.size()));

        long totalVal = dbHelper.getTotalValue();
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalValue.setText(formatter.format(totalVal) + " đ");

        int lowStock = dbHelper.countLowStock();
        tvLowStockCount.setText(String.valueOf(lowStock));
    }
}