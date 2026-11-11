package com.example.quanlykhohang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, R.layout.item_product, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Product product = products.get(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvPrice = convertView.findViewById(R.id.tvItemPrice);
        TextView tvQuantity = convertView.findViewById(R.id.tvItemQuantity);
        TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvLowStock = convertView.findViewById(R.id.tvItemLowStock);

        tvName.setText(product.getName());

        DecimalFormat formatter = new DecimalFormat("#,###");
        tvPrice.setText("Giá bán: " + formatter.format(product.getPrice()) + " đ");

        tvQuantity.setText("SL: " + product.getQuantity());
        tvCategory.setText(product.getCategory());

        if (product.getQuantity() < 5) {
            tvLowStock.setVisibility(View.VISIBLE);
            tvQuantity.setTextColor(0xFFD32F2F);
        } else {
            tvLowStock.setVisibility(View.GONE);
            tvQuantity.setTextColor(0xFF333333);
        }

        return convertView;
    }
}