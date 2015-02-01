package com.autoskola.instruktori.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.autoskola.instruktori.R;
import com.autoskola.instruktori.adapters.CommentAdapter;
import com.autoskola.instruktori.map.MapHelper;
import com.autoskola.instruktori.services.GpsWebService;
import com.autoskola.instruktori.services.model.GpsInfo;
import com.autoskola.instruktori.services.model.Komentar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 1/31/15.
 */
public class FragmentZavrseneVoznjeDetalji extends DialogFragment {

    private ListView mListViewKomentari;
    private GoogleMap googleMap;
    private static View view;
    private TextView mTxtEmptyCommentsView;
    private CommentAdapter mCommentAdapter;



    public static final FragmentZavrseneVoznjeDetalji newInstance(String voznjaId)
    {
        FragmentZavrseneVoznjeDetalji fragment = new FragmentZavrseneVoznjeDetalji();
        Bundle bundle = new Bundle();
        bundle.putString("voznjaId",voznjaId);
        fragment.setArguments(bundle);
        return fragment ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_zavrsene_voznje_detalji, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        Button btnClose = (Button)view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentAdapter = new CommentAdapter(getActivity(),new ArrayList<Komentar>());
                mListViewKomentari.setAdapter(mCommentAdapter);
                getDialog().dismiss();
            }
        });

        mTxtEmptyCommentsView = (TextView)view.findViewById(R.id.emptyView);
        mListViewKomentari = (ListView)view.findViewById(R.id.list_view_komentari);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String voznjaId  = getArguments().getString("voznjaId");

        // Get gps map info
        getMapDetails(voznjaId);

        // Get comments
        getComments(voznjaId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map_detalji)).getMap();

            //googleMap.setMyLocationEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void getMapDetails (String voznjaId){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")
                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<GpsInfo>> callback = new Callback<List<GpsInfo>>() {
            @Override
            public void success(List<GpsInfo> gpsList, Response response) {
                Log.d("GET Gps info - success:", "");

                List<LatLng> locations = new ArrayList<LatLng>();
                for(int i=0;i<gpsList.size();i++){
                    locations.add(new LatLng(Double.valueOf(gpsList.get(i).getLatitude()),Double.valueOf(gpsList.get(i).getLongitude())));

                }
                MapHelper.getInstance().drawMapRoute(locations,googleMap,getActivity());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET Gps info - fail:",error.toString());
            }
        };

        // GET request
        service.getGpsInfo(voznjaId,callback);

    }

    private void getComments (String voznjaId){

        // Set endpoint
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://projekt001.app.fit.ba/autoskola")

                .build();

        // Generate service
        GpsWebService service = restAdapter.create(GpsWebService.class);

        // Callback
        Callback<List<Komentar>> callback = new Callback<List<Komentar>>() {
            @Override
            public void success(List<Komentar> commentList, Response response) {
                Log.d("GET comments - success:", "");
                 if (commentList.size()==0)
                     mTxtEmptyCommentsView.setVisibility(View.VISIBLE);
                else
                     mTxtEmptyCommentsView.setVisibility(View.INVISIBLE);

                mCommentAdapter = new CommentAdapter(getActivity(),commentList);
                mListViewKomentari.setAdapter(mCommentAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GET comments - fail:",error.toString());
            }
        };

        // GET request
        service.getGpsKomentar(voznjaId, callback);
    }


}
