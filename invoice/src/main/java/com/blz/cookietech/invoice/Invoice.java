package com.blz.cookietech.invoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class Invoice extends AppCompatActivity {

    private Menu menuItem;
    private EditText customer_name;
    private EditText customer_phone;
    private EditText customer_email;
    private EditText customer_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        CustomerDetails detailsForServices = intent.getParcelableExtra("service_details");
        ArrayList<Services> serviceList = intent.getParcelableArrayListExtra("service_list");
        assert detailsForServices != null;
        assert serviceList != null;
        CreateInvoiceForService(detailsForServices,serviceList);



        View logoView = toolbar.getChildAt(1);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void CreateInvoiceForService(CustomerDetails detailsForServices, ArrayList<Services> serviceList) {

        TextView invoice_total = findViewById(R.id.invoice_total);
        TextView subtotal = findViewById(R.id.subtotal);
        TextView total_vat = findViewById(R.id.total_vat);
        TextView txt_vat = findViewById(R.id.txt_vat);
        TextView total_amount = findViewById(R.id.total_amount);


        double sumTotal = 0;

        for (Services services: serviceList){
            sumTotal += (services.getService_cost() * services.getService_quantity());
        }
        String subTotal = "$"+ sumTotal;
        subtotal.setText(subTotal);


        if (detailsForServices.getVat() == 0){
            txt_vat.setVisibility(View.GONE);
            total_vat.setVisibility(View.GONE);
            invoice_total.setText(subTotal);
            total_amount.setText(subTotal);
        }

        else{
            String vat = detailsForServices.getVat() + "%";
            total_vat.setText(vat);
            String totalWithVat = "$" + sumTotal + ((sumTotal*detailsForServices.getVat())/100);
            invoice_total.setText(totalWithVat);
            total_amount.setText(totalWithVat);
        }





        TextView issue_date = findViewById(R.id.issue_date);
        issue_date.setText(detailsForServices.getDue_date());

        customer_name = findViewById(R.id.customer_name);
        customer_phone = findViewById(R.id.customer_phone);
        customer_email = findViewById(R.id.customer_email);
        customer_address = findViewById(R.id.customer_address);


        customer_name.setText(detailsForServices.getCustomer_name());
        customer_phone.setText(detailsForServices.getCustomer_phone());
        customer_email.setText(detailsForServices.getCustomer_email());
        customer_address.setText(detailsForServices.getCustomer_address());

        TextView service_provider = findViewById(R.id.service_provider);
        TextView service_category = findViewById(R.id.service_category);
        service_provider.setText(detailsForServices.getService_provider());
        service_category.setText(detailsForServices.getService_category());

        RecyclerView service_details_rv = findViewById(R.id.service_details_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Invoice.this);
        service_details_rv.setLayoutManager(layoutManager);
        service_details_rv.setHasFixedSize(true);
        ServiceDetailsAdapter adapter = new ServiceDetailsAdapter(serviceList);
        service_details_rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        menuItem = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


       if (item.getItemId() == R.id.menu_edit){
           menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
           menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
           EnableEdit();
       }

       else if (item.getItemId() == R.id.menu_done){
           menuItem.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
           menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
           DisableEdit();

       }


        return true;
    }

    private void DisableEdit() {
        customer_name.setEnabled(false);
        customer_phone.setEnabled(false);
        customer_email.setEnabled(false);
        customer_address.setEnabled(false);
        customer_name.setBackgroundResource(0);
        customer_phone.setBackgroundResource(0);
        customer_email.setBackgroundResource(0);
        customer_address.setBackgroundResource(0);
    }

    private void EnableEdit() {
        customer_name.setEnabled(true);
        customer_phone.setEnabled(true);
        customer_email.setEnabled(true);
        customer_address.setEnabled(true);
        customer_name.setBackground(getDrawable(R.drawable.bottom_border_grey));
        customer_phone.setBackground(getDrawable(R.drawable.bottom_border_grey));
        customer_email.setBackground(getDrawable(R.drawable.bottom_border_grey));
        customer_address.setBackground(getDrawable(R.drawable.bottom_border_grey));

    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    private void showAlert() {
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
        exitAlert.setTitle("Are you sure?")
                .setMessage("Do you want to exit? It will delete all your saved progress")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Invoice.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        exitAlert.show();
    }
}
