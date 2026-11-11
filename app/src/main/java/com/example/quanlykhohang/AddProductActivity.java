package com.example.quanlykhohang;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private EditText etName, etPrice, etQuantity, etDescription;
    private Spinner spinnerCategory;
    private Switch switchActive;
    private Button btnSaveProduct;
    private LinearLayout btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etName = findViewById(R.id.etAddName);
        etPrice = findViewById(R.id.etAddPrice);
        etQuantity = findViewById(R.id.etAddQuantity);
        etDescription = findViewById(R.id.etAddDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        switchActive = findViewById(R.id.switchActive);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(v -> finish());

        String[] categories = {"Điện tử", "Thực phẩm", "Văn phòng phẩm", "Gia dụng", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnSaveProduct.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            int active = switchActive.isChecked() ? 1 : 0;
            String description = etDescription.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            if (price <= 0) {
                etPrice.setError("Giá bán phải lớn hơn 0");
                return;
            }

            if (quantity < 0) {
                etQuantity.setError("Số lượng không được âm");
                return;
            }

            Product product = new Product(name, price, quantity, category, active, description);
            dbHelper.insertProduct(product);
            finish();
        });
    }
}