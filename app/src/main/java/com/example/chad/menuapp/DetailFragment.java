package com.example.chad.menuapp;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DrinkInfo drink;
    TextView nametext,heattext,sugartext;
    ImageView image;


    public DetailFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(DrinkInfo drinkinfo) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("drinkinfo",drinkinfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           drink=getArguments().getParcelable("drinkinfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_drink_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nametext=(TextView)getView().findViewById(R.id.drinkname);
        heattext=(TextView)getView().findViewById(R.id.caloriestext);
        sugartext=(TextView)getView().findViewById(R.id.sugartext);
        image=(ImageView)getView().findViewById(R.id.drinkimage);
        nametext.setText(drink.getName());
        heattext.setText("Calories:"+ String.valueOf(drink.getHeat()));
        sugartext.setText("Sugar:"+ String.valueOf(drink.getSugar()));
        image.setImageResource(drink.getImgId());


    }
}
