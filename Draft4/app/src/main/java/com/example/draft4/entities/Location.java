package com.example.draft4.entities;

import com.example.draft4.entities.entityInterfaces.EntityInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Location implements EntityInterface {
    private static final String JSON_ID = "id";
    private static final String JSON_STARTLOCATION = "startLocation";
    private static final String JSON_ENDLOCATION = "endLocation";

    private String mId, mStart, mEnd;

    public Location(UUID id, String start, String end){
       mId = id.toString();
       mStart = start;
       mEnd = end;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(JSON_ID, mId);
        obj.put(JSON_STARTLOCATION, mStart);
        obj.put(JSON_ENDLOCATION, mEnd);

        return obj;
    }
}
