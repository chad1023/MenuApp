package com.example.chad.menuapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by lab430 on 2017/8/9.
 */

@ParseClassName("DrinkInfo")
public class DrinkInfo extends ParseObject implements Parcelable {
//    private int imgId;
//    private String name;
//    private int price;
//    private int heat;
//    private float sugar;

    public final static String imgIdkey="imgId";
    public final static String namekey="name";
    public final static String pricekey="price";
    public final static String heatkey="heat";
    public final static String sugarkey="sugar";

    public DrinkInfo()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getImgId());
        dest.writeString(this.getName());
        dest.writeInt(this.getPrice());
        dest.writeInt(this.getHeat());
        dest.writeFloat(this.getSugar());
    }

    protected DrinkInfo(Parcel in) {
        this.setImgId(in.readInt());
        this.setName(in.readString());
        this.setPrice(in.readInt());
        this.setHeat(in.readInt());
        this.setSugar(in.readFloat());
    }

    public static final Parcelable.Creator<DrinkInfo> CREATOR = new Parcelable.Creator<DrinkInfo>() {
        @Override
        public DrinkInfo createFromParcel(Parcel source) {
            return new DrinkInfo(source);
        }

        @Override
        public DrinkInfo[] newArray(int size) {
            return new DrinkInfo[size];
        }
    };

    public int getImgId() {
        return getInt(imgIdkey);
    }

    public void setImgId(int imgId) {
        put(imgIdkey,imgId);
    }

    public String getName() {
        return getString(namekey);
    }

    public void setName(String name) {put(namekey,name);
    }

    public int getPrice() {
        return getInt(pricekey);
    }

    public void setPrice(int price) {
        put(pricekey,price);
    }

    public int getHeat() {
        return getInt(heatkey);
    }

    public void setHeat(int heat) {
        put(heatkey,heat);
    }

    public float getSugar() {
        return getLong(sugarkey);
    }

    public void setSugar(float sugar) {
       put(sugarkey,sugar);
    }

    public static ParseQuery<DrinkInfo> getQuery() { // 方便取得ParseQuery<DrinkInfo>
        return ParseQuery.getQuery(DrinkInfo.class);
    }
    public static DrinkInfo newInstance(String name , int price, int imgId, int heat, float sugar)
    {
        DrinkInfo tmp=new DrinkInfo();
        tmp.setName(name);
        tmp.setPrice(price);
        tmp.setImgId(imgId);
        tmp.setHeat(heat);
        tmp.setSugar(sugar);
        return tmp;
    }
    public static DrinkInfo newInstance(DrinkInfo drinkinfo)
    {
        return DrinkInfo.newInstance(drinkinfo.getName(),drinkinfo.getPrice(),drinkinfo.getImgId(),drinkinfo.getHeat(),drinkinfo.getSugar());
    }





}
