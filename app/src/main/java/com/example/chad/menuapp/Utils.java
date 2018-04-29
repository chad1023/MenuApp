package com.example.chad.menuapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by lab430 on 2017/8/17.
 */

public class Utils {

    public static void writeFile(Context context, String fileName, String content)
    {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String fileName)
    {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            fis.read(buffer, 0, buffer.length);
            fis.close();
            String string = new String(buffer);
            return string;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static byte[] urlToBytes(String urlString)
    {
        Log.d("Debug","urltobytes:"+urlString);
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1)
            {
                Log.d("Debug","write");
                byteArrayOutputStream.write(buffer, 0, len);

            }
            Log.d("Debug","getbytes");
            return  byteArrayOutputStream.toByteArray();

        } catch (MalformedURLException e) {
            Log.d("Debug","urltobyt,MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Debug","urltobyt,IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public static double[] getLatLngFromGoogleMapAPI(String address)
    {
        try {
            address = URLEncoder.encode(address, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("Debug","Utils.getLatLng");
        String apiURL = "http://maps.google.com/maps/api/geocode/json?address=" + address;
        Log.d("Debug",apiURL);
        byte[] bytes = Utils.urlToBytes(apiURL);

        if(bytes == null) {
            Log.d("Debug","byte=null");
            return null;
        }
        String result = new String(bytes);
        Log.d("Debug","Utils.getLatLng"+result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("OK"))
            {
                JSONObject location = jsonObject.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");

                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                Log.d("Debug","Utils.getLatLng"+ String.valueOf(lat)+ String.valueOf(lng));
                return new double[]{lat,lng};
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Debug","Utils.getLatLng"+"null");
        return null;
    }

}
