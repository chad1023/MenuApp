package com.example.chad.menuapp;


import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_STORE= "ARG_STORE=";
//    private static final String ARG_STOREINFO = "ARG_STOREINFO ";
//    private static final String ARG_LAT = "ARG_LAT";
//    private static final String ARG_LNG = "ARG_LNG";
//    private static final String ARG_LATLNG = "ARG_LATLNG";
//

//    static StoreFragment storeFragment;
    // TODO: Rename and change types of parameters
    String [] storeinfo;
    LatLng[]latlngs;
    String[] store;

    private GoogleMap map;
    MapView mapview;

    protected StoreInterface storeInterface;
    SupportMapFragment mapFragment;//地圖 fragment


//    String[] store = new String[]{"公館店","和平店","師大店","台大店","辛亥店"};

    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String [] store, String [] info, LatLng [] latlngs) {
        StoreFragment fragment = new StoreFragment();
//        Bundle arg = new Bundle();
//        arg.putStringArray(ARG_STORE,store);
//        arg.putStringArray(ARG_STOREINFO,info);
//        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            store=getArguments().getStringArray(ARG_STORE);
//            storeinfo=getArguments().getStringArray(ARG_STOREINFO);
//            double [] lat = getArguments().getDoubleArray(ARG_LAT);
//            double [] lng = getArguments().getDoubleArray(ARG_LNG);

//            latlngs=(LatLng[])getArguments().getParcelableArray(ARG_LATLNG);
//            for(int i=0;i<lat.length;i++){
//                latlngs[i]=new LatLng(parcel[i]);
////                latlngs[i].longitude=lng[i];
//            }

//        }
        Log.d("Fragment","StoreFragment onCreate");

        if (getActivity() instanceof StoreInterface){
            storeInterface=(StoreInterface)getActivity();
        }
        else{
            throw new ClassCastException("Hosting activity must implement storeInterface");
        }

        findStoreLat();
    }


    public void findStoreLat(){
        Resources resources=getResources();//取得 res
        storeinfo=resources.getStringArray(R.array.storeinfos);//透過 res 取得 array.xml 裡的 storeinfos
        String []latlngstring = resources.getStringArray(R.array.latlng);
        latlngs=new LatLng[storeinfo.length];
        store=new String[storeinfo.length];
        for(int i=0;i<storeinfo.length;i++)
        {
            store[i]=storeinfo[i].split(",")[0];
            storeinfo[i]=storeinfo[i].split(",")[1];//split是字串分割的function，參數是用來分割的字元: 1,122,22-> 1 122 22
            double lat = Double.parseDouble(latlngstring[i].split(",")[0]);
            double lng = Double.parseDouble(latlngstring[i].split(",")[1]);
            latlngs[i] = new LatLng(lat,lng);
        }
    }
    public interface StoreInterface{
        void orderStore(String store);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("Fragment","StoreFragment onAttach");

    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("Fragment","StoreFragment onActivityCreate");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("Fragment","StoreFragment onCreateView");
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_store, container, false);
        ListView listview = (ListView)view.findViewById(R.id.storelist);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                store
        );
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 moveMap(latlngs[i]);
                 Log.d("tag","click"+store[i]);
                 Log.d("tag",latlngs[i].toString());
//                 LatLng place=((StoreActivity)getActivity()).getLatlngs(i);

                map.addMarker(
                        new MarkerOptions()
                                .position(latlngs[i])
                                .title(store[i])
                                .snippet(storeinfo[i]));
                storeInterface.orderStore(store[i]);

            }
        });

        mapview = (MapView)view.findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        mapview.getMapAsync(this);

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }
//
//
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        map.animateCamera(cameraUpdate);


        return view;


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        setMap();
    }


    private void moveMap(LatLng place) {
        // 建立地圖攝影機的位置物件
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(place)
                        .zoom(17)
                        .build();
        // 使用動畫的效果移動地圖
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    private void setMap(){
        for (int i=0;i<latlngs.length;i++)
        {
            map.addMarker(
                    new MarkerOptions()
                            .position(latlngs[i])
                            .title(store[i])
                            .snippet(storeinfo[i]));
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragment","StoreFragment onStart");
    }

    @Override
    public void onResume() {
        mapview.onResume();
        super.onResume();
        Log.d("Fragment","StoreFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Fragment","StoreFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Fragment","StoreFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Fragment","StoreFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
        Log.d("Fragment","StoreFragment onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragment","StoreFragment onDetach");
    }




}
