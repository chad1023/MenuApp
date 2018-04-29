package com.example.chad.menuapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;


public class StoreActivity extends AppCompatActivity implements View.OnClickListener,MenuFragment.orderDrinkinterface,StoreFragment.StoreInterface{

    FragmentManager fragmentManager;
    MenuFragment menufragment;
    StoreFragment storeFragment;
    Fragment currentFragment;
    String [] fragmenttag={"menufragment","storefragment"};

    Map<String,Integer> order = new HashMap<>();

    TextView ordertext,storetext;
    Button submitbutton;


//    String [] storeinfo;
//    LatLng[] latlngs;
//    String[] store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
     Log.d("Fragment_Activity","create");



        fragmentManager=getFragmentManager();
        menufragment=MenuFragment.newInstance();
        fragmentManager.beginTransaction()
                .add(R.id.fragment,menufragment,"menufragment")
                .commit();
        currentFragment=menufragment;
        Button aboutbutton=(Button)findViewById(R.id.aboutbutton);
        Button storebutton=(Button)findViewById(R.id.storebutton);
        Button menubutton=(Button)findViewById(R.id.menubutton);

        aboutbutton.setOnClickListener(this);
        storebutton.setOnClickListener(this);
        menubutton.setOnClickListener(this);

        ordertext=(TextView)findViewById(R.id.ordertext);
        storetext=(TextView)findViewById(R.id.orderstore);
        submitbutton=(Button)findViewById(R.id.submitbutton);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag","submit");
            }
        });


    }
//
//
//    public void findStoreLat(){
//        Resources resources=getResources();//取得 res
//        storeinfo=resources.getStringArray(R.array.storeinfos);//透過 res 取得 array.xml 裡的 storeinfos
//        latlngs=new LatLng[storeinfo.length];
//        store=new String[storeinfo.length];
//        for(int i=0;i<storeinfo.length;i++)
//        {
//            store[i]=storeinfo[i].split(",")[0];
//            storeinfo[i]=storeinfo[i].split(",")[1];//split是字串分割的function，參數是用來分割的字元: 1,122,22-> 1 122 22
//            (new GeoCodingTask()).execute(i);
//
//        }
//    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Fragment_Activity","start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Fragment_Activity","resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Fragment_Activity","pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Fragment_Activity","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Fragment_Activity","destroy");
    }

    @Override
    public void onClick(View view) {
//        DetailFragment detailfragment=(DetailFragment)fragmentManager.findFragmentByTag("DetailFragment");
        FragmentTransaction ft=fragmentManager.beginTransaction();
//        if (detailfragment!=null)
//        {
//            ft.remove(detailfragment);
//        }
        Fragment fragment=new Fragment();
        ft.hide(currentFragment);
        switch (view.getId())
        {
            case R.id.aboutbutton:
//                fragment=new AboutFragment();
//                Log.d("tag","latlngLength"+Integer.toString(latlngs.length));
//                for(int i=0;i<latlngs.length;i++){
//                    Log.d("tag",latlngs[i].toString());
//                }
                break;
            case R.id.menubutton:
                fragment = fragmentManager.findFragmentByTag(fragmenttag[0]);
                break;
            case R.id.storebutton:
                fragment = fragmentManager.findFragmentByTag(fragmenttag[1]);
                break;
        }
        if (fragment==null)
        {
            int tagid=0;
            switch (view.getId())
            {

                case R.id.aboutbutton:
//                fragment=new AboutFragment();
                    break;
                case R.id.menubutton:
                    tagid=0;
                    fragment = new MenuFragment();
                    break;
                case R.id.storebutton:
                    tagid=1;
                    fragment = new StoreFragment();
                    break;
            }
            ft.add(R.id.fragment,fragment,fragmenttag[tagid]);
        }
        else
        {
            ft.show(fragment);
        }
        currentFragment=fragment;
        ft.addToBackStack(null);
        ft.commit();

    }
    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getFragmentManager();

        if (fm.getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            fm.popBackStack();
        }
    }


    @Override
    public void orderDrink(DrinkInfo drink) {
        if (menufragment==null){
            menufragment=new MenuFragment();
        }


        int count = 0;

        if (order.containsKey(drink.getName()))
            count=order.get(drink.getName());

        order.put(drink.getName(),count+1);
        Log.d("tag",drink.getName());

        printorder();

    }

    private void printorder(){

        Log.d("tag","order");

        String s="";
        for (String key : order.keySet()) {
            Log.d("tag",key+":" + Integer.toString(order.get(key)));
            s+=(key+": ");
            s+=Integer.toString(order.get(key));
            s+="\n";
        }
        ordertext.setText(s);




    }

    @Override
    public void orderStore(String store) {
        storetext.setText(store);
    }
    //使用 AsynTask 處理透過地址取得經緯度
//    public class GeoCodingTask extends AsyncTask<Integer, Void, double[]> {
//
//        int state_address;
//
//        @Override
//        protected double[] doInBackground(Integer... params) {//在背景執行的 function
//            state_address = params[0];
//            Log.d("Debug","doinback");
//            String address=storeinfo[state_address];
//            double[] latlng = Utils.getLatLngFromGoogleMapAPI(address);//getLatLngFromGoogleMapAPI:透過地址向 server 取得經緯度的 function
//            if (latlng != null) {
//                Log.d("Debug", String.valueOf(latlng[0]));
//                Log.d("Debug", String.valueOf(latlng[1]));
//
//                return latlng;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(double[] latlng ) {//取得經緯度後，透過這經緯度設立 Marker
//            super.onPostExecute(latlng);
//            if (latlng!=null){
//                final LatLng place=new LatLng(latlng[0],latlng[1]);
////                mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                        .findFragmentById(R.id.map);
//                latlngs[state_address]=new LatLng(latlng[0],latlng[1]);
//                Log.d("tag",place.toString());
////                if(map!=null){
//
//            }
//        }
//    }


}
