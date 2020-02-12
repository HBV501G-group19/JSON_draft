package com.example.draft4;

/*
    This class will be responsible for triggering reading and writing data to and from JSON.
    This is a singleton as only one writer/reader should be doing such jobs to avoid data race.
 */

import android.content.Context;
import android.util.Log;

import com.example.draft4.entities.entityInterfaces.EntityInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


public class DataModifier{
    private static final String FILENAME = "data.json";
    private static final String TAG = "DataModifier";

    private static DataModifier sDataModifier;

    private ArrayList<EntityInterface> mEntities;
    private JSONSerializer mSerializer;
    private Context mContext;

    public static DataModifier get(Context context){
        if(sDataModifier == null){
            sDataModifier = new DataModifier(context);
        }

        return sDataModifier;
    }

    // -------------------------------------------------------------------------------------------//
    /*
        The addEntity and getEntities methods are proxies for
        the writeToJson and readFromJson methods. This is done
        in order to save time as a read from a large json file
        will make the program look sluggish.
     */

    // This method will add an entity to the pool of entities,
    // but it will not make any writing to disk.
    // Call writeToJson() when a writing should occur.
    public void addEntity(EntityInterface entity){
        mEntities.add(entity);
    }

    // Returning the currently available entities.
    public ArrayList<EntityInterface> getEntities(){
        return mEntities;
    }

    // Retrieving the data from json.
    // Should only be called if needed.
    public void readFromJson(){
        try{
         JSONArray array =  mSerializer.readFromJSON();

         for(int i = 0; i < array.length(); i++){
             // TODO: write the objects to the screen.
             // writing to the Locgat instead...
             Log.i("LOGGER", "L: " + array.getJSONObject(i));
         }

        }catch(IOException | JSONException e){
            e.getStackTrace();
        }
    }

    // Call this method only when writing to disk is convenient.
    public void writeToJson(){
        try{
            mSerializer.writeToJSON(mEntities);
        }catch(JSONException joe){
            joe.getStackTrace();
        }
    }

    // The constructor for the singleton.
    private DataModifier(Context context){
        mContext = context;
        mEntities = new ArrayList<>();
        mSerializer = new JSONSerializer(mContext, FILENAME);
    }

    //--------------------------------------------------------------------------------------------//

    /////////////////////////////////////////////
    ///             JSON SERIALIZER           ///
    ///                                       ///
    /// The idea for this class comes from    ///
    /// the book Android Programming,         ///
    //  by The Big Nerd Ranch, from the       ///
    /// authors Brian Hardy and Bill Phillips ///
    /////////////////////////////////////////////

    private class JSONSerializer {

        private Context mContext;
        private String mFileName;

        public JSONSerializer(Context context, String fileName) {
            mContext = context;
            mFileName = fileName;
        }

        // This method fetches the json file and returns the data found in it.
        // It puts the data in a container which in the role of a proxy server.
        public JSONArray readFromJSON() throws IOException, JSONException{
            BufferedReader reader = null;
            JSONArray array = null;

            try{
                InputStream in = mContext.openFileInput(mFileName);
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    builder.append(line);
                }

                array = (JSONArray) new JSONTokener(builder.toString()).nextValue();

            }catch (FileNotFoundException fe){
                fe.getStackTrace();
            }finally {
                if(reader != null){
                    reader.close();
                }
            }

            return array;
        }

        // This method will write to the json file, should only be done
        // when really needed as writing might be slow.
        public void writeToJSON(ArrayList<EntityInterface> entities) throws JSONException {

            JSONArray array = new JSONArray();
            for (EntityInterface entity : entities){
                array.put(entity.toJSON());
            }

            try {
                OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
                Writer writer = new OutputStreamWriter(out);
                writer.write(array.toString());
                writer.close();
                out.close();

            } catch (IOException ioe){
                ioe.getStackTrace();
            }
        }
    }
}
