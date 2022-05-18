package com.jwfeuers.covider2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.material.snackbar.Snackbar;
import com.jwfeuers.covider2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter/*, OnMyLocationButtonClickListener,
        OnMyLocationClickListener*/{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng USC = new LatLng(34.0224, -118.2851);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Create a LatLngBounds that includes the city of Adelaide in Australia.

        // Buildings
        LatLng Doheny_Library = new LatLng(34.02022501127694, -118.28368452946063);
        LatLng Bovard_Auditorium = new LatLng(34.02086197097705, -118.28555463044077);
        LatLng Leavey_Library = new LatLng(34.021749233020074, -118.28278983875579);

        //name, latitude, longitude, risk
        /*
        * loop through building array
        * for each element
        * 1. new LatLng
        * 2. addMarker
        * */

        LatLngBounds uscBounds = new LatLngBounds(
                new LatLng(34.01789052388893, -118.29197724270468), // SW bounds
                new LatLng(34.02845786157247, -118.27567294806313)  // NE bounds
        );

// Constrain the camera target to the Adelaide bounds.

//        mMap.setMyLocationEnabled(true);
//        mMap.setOnMyLocationButtonClickListener(this);
//        mMap.setOnMyLocationClickListener(this);
        mMap.setLatLngBoundsForCameraTarget(uscBounds);

        mMap.addMarker(new MarkerOptions().position(Doheny_Library).title("Doheny Memorial Library").snippet("Tap to view status"));
        mMap.addMarker(new MarkerOptions().position(Bovard_Auditorium).title("Bovard Auditorium").snippet("Tap to view status"));
        mMap.addMarker(new MarkerOptions().position(Leavey_Library).title("Leavey Library").snippet("Tap to view status"));

        mMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Doheny_Library, 18.0f));

        mMap.setInfoWindowAdapter(this);
    }

    public void onViewList(View view){
//        Toast.makeText(this, "View List Clicked", Toast.LENGTH_LONG)
//                .show();
        openList();
    }

    public void openList(){
        Intent intent = new Intent(this, BuildingList.class);
        startActivity(intent);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        String name = marker.getTitle();
        Toast toast = Toast.makeText(this, "Successfully checked in to " + marker.getTitle(), Toast.LENGTH_LONG);
        toast.show();
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        TextView tv1 = (TextView) v.findViewById(R.id.textView1);
        TextView tv2 = (TextView) v.findViewById(R.id.textView2);
        TextView tv3 = (TextView) v.findViewById(R.id.textView3);
        TextView tv4 = (TextView) v.findViewById(R.id.textView4);
        tv1.setText(marker.getTitle());
        if(marker.getTitle().equals("Doheny Memorial Library")){
            tv2.setText("Risk Level: HIGH");
            tv3.setText("Entry Requirements: Trojan Check, mask, social distancing");
        }
        else{
            tv2.setText("Risk Level: LOW");
            tv3.setText("Entry Requirements: none");
        }
        tv4.setText("TAP TO CHECK IN");
        return v;
    }


//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
//                .show();
//    }
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
//                .show();
//        // Return false so that we don't consume the event and the default behavior still occurs
//        // (the camera animates to the user's current position).
//        return false;
//    }
}