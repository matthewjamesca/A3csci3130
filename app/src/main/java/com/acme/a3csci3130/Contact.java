package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public String businessId = "";
    public String businessNumber;
    public String name;
    public String address;
    public String businessType;
    public String province;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Contact(String businessNumber, String name, String address, String businessType, String province){
        this.businessNumber = businessNumber;
        this.name = name;
        this.address = address;
        this.businessType = businessType;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("businessNumber", businessNumber);
        result.put("name", name);
        result.put("address", address);
        result.put("businessType", businessType);
        result.put("province", province);

        return result;
    }
}
