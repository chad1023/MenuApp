package com.example.chad.menuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lab430 on 2017/8/9.
 */

public class DrinkInfoAdapter extends ArrayAdapter<DrinkInfo> {
    int raw_layout_id;
    LayoutInflater inflater;
    Picasso picasso;

    public DrinkInfoAdapter(Context context,
                            int resource,
                            ArrayList<DrinkInfo> items)
    {
        super(context, resource, items);
        raw_layout_id=resource;
        inflater= LayoutInflater.from(context);
        picasso=Picasso.with(context);
    }


    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final DrinkInfo dataitem=getItem(pos);
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            convertView=inflater.inflate(raw_layout_id,parent,false);
            viewHolder= new ViewHolder(convertView,picasso);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.setView(dataitem);
        return convertView;
    }



    // ViewHolder
    public static class ViewHolder
    {
        ImageView item_image;
        TextView item_name;
        TextView item_price;
        Picasso item_picasso;

        public ViewHolder(View raw_view, Picasso picasso)
        {
            item_image=(ImageView)raw_view.findViewById(R.id.raw_image);
            item_name=(TextView)raw_view.findViewById(R.id.raw_text);
            item_price=(TextView)raw_view.findViewById(R.id.raw_price);
            item_picasso=picasso;
        }
        public void setView(DrinkInfo data)
        {
            item_picasso.load(data.getImgId()).into(item_image);
            item_name.setText(data.getName());
            item_price.setText("$"+ String.valueOf(data.getPrice()));
        }

    }







}
