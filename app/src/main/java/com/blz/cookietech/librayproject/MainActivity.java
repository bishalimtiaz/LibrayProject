package com.blz.cookietech.librayproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blz.cookietech.invoice.CustomDesign;
import com.blz.cookietech.invoice.CustomerDetails;
import com.blz.cookietech.invoice.Invoice;
import com.blz.cookietech.invoice.Services;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_invoice_service = findViewById(R.id.btn_invoice_service);
        btn_invoice_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Services> services = new ArrayList<>();
                services.add(new Services("Wall painting",50));
                services.add(new Services("Room Painting",90,3));

                String due_date = "12/7/2020";
                String customer_name = "Amanullah Asraf";
                String customer_phone = "+8801521304517";
                String customer_email ="amanullahoasraf@gmail.com";
                String customer_address = "57/3, Gulshan, Dhaka";
                String service_provider = "Akash Shahriar";
                String service_category = "Paint Job";
                String service_id = "101";
                double vat =7.5;
                CustomerDetails detailsForServices = new CustomerDetails(due_date,customer_name,customer_phone,customer_email,customer_address,service_provider,service_category,service_id,vat);
                Intent intent = new Intent(MainActivity.this,Invoice.class);
                intent.putExtra("service_details",detailsForServices);
                intent.putExtra("service_list",services);
                startActivity(intent);
            }
        });
    }
}
