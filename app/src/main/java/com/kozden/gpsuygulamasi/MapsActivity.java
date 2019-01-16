package com.kozden.gpsuygulamasi;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,TaskLoadedCallback
{



    private GoogleMap mMap;
    double latitude, longitude;
    double end_latitude, end_longitude;
    TextView textView;
    Button b1;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;


    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        b1=findViewById(R.id.button);
        getDirection = findViewById(R.id.btnGetDirection);
        t=findViewById(R.id.textView);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }




        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                latitude=39.779638;
                longitude=32.820593;

                end_latitude=39.900406;
                end_longitude=32.861010;
                //  mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(end_latitude,end_longitude));
                markerOptions.title("hedef");

                float results[] = new float[10];


                Location.distanceBetween(latitude, longitude, end_latitude, end_longitude,results);
                markerOptions.snippet("Distance = " + results[0]);
                mMap.addMarker(markerOptions);
                String s = String.valueOf(results[0]);
                t.setText(s);

            }
        });
        //27.658143,85.3199503
        //27.667491,85.3208583
        place1 = new MarkerOptions().position(new LatLng(39.900406, 32.861010)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(39.779638, 32.820593)).title("Location 2");


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        latitude=39.779638;
        longitude=32.820593;


        LatLng wahcantt = new LatLng(latitude,  longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wahcantt, 10F));


    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }


}
