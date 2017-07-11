package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateContactActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Button submitButton;
    private String primaryBusiness, province;
    private EditText nameField, addressField, businessNumberField;
    private MyApplicationData appState;
    private String[] businessTypes = {"Please Select Business", "Fisher", "Distributor", "Processor", "Fish Monger"};
    private String[] provinces = {
            "Please Select Province", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        //Spinner logic
        Spinner spinnerBusiness = (Spinner) findViewById(R.id.primaryBusiness);
        spinnerBusiness.setOnItemSelectedListener(this);
        ArrayAdapter aaBusiness = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, businessTypes);
        aaBusiness.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBusiness.setAdapter(aaBusiness);

        Spinner spinnerProvince = (Spinner) findViewById(R.id.province);
        spinnerProvince.setOnItemSelectedListener(this);
        ArrayAdapter aaProvince = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, provinces);
        aaProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(aaProvince);

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);
        businessNumberField = (EditText) findViewById(R.id.businessNum);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitInfoButton(v);
            }
        });
    }

    /**
     * Submit info button logic, creates contact object
     * @param v
     */
    public void submitInfoButton(View v) {
        if (primaryBusiness.equals("Please Select Business")) {
            Toast.makeText(
                    getApplicationContext(), "Error: Please select a primary business type.", Toast.LENGTH_LONG).show();
            return;
        }

        if (addressField.getText().toString().length() > 50) {
            Toast.makeText(
                    getApplicationContext(), "Error: Address must be less than 50 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        if (businessNumberField.getText().toString().length() != 9) {
            Toast.makeText(
                    getApplicationContext(), "Error: Business number is not of length 9.", Toast.LENGTH_LONG).show();
            return;
        }

        if (nameField.getText().toString().length() < 2  || nameField.getText().toString().length() > 48) {
            Toast.makeText(
                    getApplicationContext(), "Error: Business name must be between 2 and 48 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        if (province.equals("Please Select Province")) {
            province = "";
        }

        //each entry needs a unique ID
        String businessID = appState.firebaseReference.push().getKey();
        String businessNumber = businessNumberField.getText().toString();
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        Contact business = new Contact(businessNumber, name, address, primaryBusiness, province);
        business.businessId = businessID;

        appState.firebaseReference.child(businessID).setValue(business);

        finish();
    }

    /**
     * Spinner logic on item selection
     * @param arg0
     * @param arg1
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        switch(arg0.getId()) {
            case (R.id.primaryBusiness):
                primaryBusiness = businessTypes[position];
                break;
            case (R.id.province):
                province = provinces[position];
                break;
            default:
                //fall through
        }
    }

    /**
     * Spinner logic on nothing selected.
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //Do Nothing
    }
}
