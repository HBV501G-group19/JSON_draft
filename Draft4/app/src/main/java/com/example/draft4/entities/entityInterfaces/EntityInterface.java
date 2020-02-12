package com.example.draft4.entities.entityInterfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface EntityInterface {
    public abstract JSONObject toJSON() throws JSONException;
}
