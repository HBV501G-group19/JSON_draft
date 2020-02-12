package com.example.draft4.entities;

import com.example.draft4.entities.entityInterfaces.EntityInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class User implements EntityInterface {
    private static final String JSON_ID = "id";
    private static final String JSON_USERNAME = "userName";

    private String mId, mUserName;

    public User(UUID id, String userName){
        mId = id.toString();
        mUserName = userName;
    }

    @Override
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();

        try {
            obj.put(JSON_ID, mId);
            obj.put(JSON_USERNAME, mUserName);
        }catch (JSONException je){
            je.getStackTrace();
        }

        return obj;
    }

}
