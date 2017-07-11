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

public class DetailViewActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private MyApplicationData appState;
    private Button updateButton, deleteButton;
    private EditText nameField, addressField, businessNumberField;
    private String primaryBusiness, province;
    private String[] businessTypes = {" ", "Fisher", "Distributor", "Processor", "Fish Monger"};
    private String[] provinces = {
            " ", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};

    Contact receivedContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedContactInfo = (Contact)getIntent().getSerializableExtra("Contact");
        appState = ((MyApplicationData) getApplicationContext());

        nameField = (EditText) findViewById(R.id.bName);
        addressField = (EditText) findViewById(R.id.address);
        businessNumberField = (EditText) findViewById(R.id.businessNum);
        updateButton = (Button) findViewById(R.id.updateButton);

        if(receivedContactInfo != null){
            businessTypes[0] = receivedContactInfo.businessType;
            provinces[0] = receivedContactInfo.province;

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

            nameField.setText(receivedContactInfo.name);
            addressField.setText(receivedContactInfo.address);
            businessNumberField.setText(receivedContactInfo.businessNumber);
        }
    }

    /**
     * Update contact on DB
     * @param v
     */
    public void updateContact(View v){
        if (businessNumberField.getText().toString().length() != 9) {
            Toast.makeText(
                    getApplicationContext(), "Error: Business number is not of length 9.", Toast.LENGTH_LONG).show();
            return;
        }

        if (addressField.getText().toString().length() > 50) {
            Toast.makeText(
                    getApplicationContext(), "Error: Address must be less than 50 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        if (nameField.getText().toString().length() < 2  || nameField.getText().toString().length() > 48) {
            Toast.makeText(
                    getApplicationContext(), "Error: Business name must be between 2 and 48 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        String businessId = receivedContactInfo.businessId;
        String businessNumber = businessNumberField.getText().toString();
        receivedContactInfo.businessNumber = businessNumber;
        String name = nameField.getText().toString();
        receivedContactInfo.name = name;
        String address = addressField.getText().toString();
        receivedContactInfo.address = address;
        receivedContactInfo.businessType = primaryBusiness;
        receivedContactInfo.province = province;

        appState.firebaseReference.child(businessId).setValue(receivedContactInfo);

        Toast.makeText(
                getApplicationContext(), "Contact Updated!", Toast.LENGTH_LONG).show();
    }

    /**
     * Remove contact from DB
     * @param v
     */
    public void eraseContact(View v)
    {
        String businessID = receivedContactInfo.businessId;
        appState.firebaseReference.child(businessID).removeValue();
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
