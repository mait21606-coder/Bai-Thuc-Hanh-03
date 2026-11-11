package com.example.quanlykhohang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvName, tvCategory, tvPrice, tvQuantity, tvTotalValue, tvDescription, tvLowStockTag;
    private Button btnAdd1, btnSell1, btnBackBottom, btnDelete;
    private LinearLayout btnBackHeader;
    private DatabaseHelper dbHelper;
    private int productId;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvName = findViewById(R.id.tvDetailName);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvQuantity = findViewById(R.id.tvDetailQuantity);
        tvTotalValue = findViewById(R.id.tvDetailTotalValue);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvLowStockTag = findViewById(R.id.tvLowStockTag);

        btnAdd1 = findViewById(R.id.btnAddQuantity);
        btnSell1 = findViewById(R.id.btnSellQuantity);
        btnBackBottom = findViewById(R.id.btnBack);
        btnBackHeader = findViewById(R.id.btnBackHeader);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DatabaseHelper(this);
        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        loadProductData();

        btnBackHeader.setOnClickListener(v -> finish());
        btnBackBottom.setOnClickListener(v -> finish());

        btnAdd1.setOnClickListener(v -> {
            if (product != null) {
                int newQty = product.getQuantity() + 1;
                dbHelper.updateQuantity(productId, newQty);
                loadProductData();
            }
        });

        btnSell1.setOnClickListener(v -> {
            if (product != null) {
                if (product.getQuantity() > 0) {
                    int newQty = product.getQuantity() - 1;
                    dbHelper.updateQuantity(productId, newQty);
                    loadProductData();
                } else {
                    Toast.makeText(this, "Hết hàng, không thể bán thêm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (product != null) {
                dbHelper.deleteProduct(productId);
                Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadProductData() {
        product = dbHelper.getProductById(productId);
        if (product != null) {
            tvName.setText(product.getName());
            tvCategory.setText(product.getCategory());

            DecimalFormat formatter = new DecimalFormat("#,###");
            tvPrice.setText(formatter.format(product.getPrice()) + " đ");
            tvQuantity.setText(String.valueOf(product.getQuantity()));

            long totalVal = (long) product.getPrice() * product.getQuantity();
            tvTotalValue.setText(formatter.format(totalVal) + " đ");

            if (product.getDescription() != null && !product.getDescription().isEmpty()) {
                tvDescription.setText(product.getDescription());
            } else {
                tvDescription.setText("Không có mô tả");
            }

            if (product.getQuantity() < 5) {
                tvLowStockTag.setVisibility(View.VISIBLE);
            } else {
                tvLowStockTag.setVisibility(View.GONE);
            }
        }
    }
}