package com.myapplicationdev.android.p08_locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnCentral, btnEast;
    private GoogleMap map;
    Spinner spn;
    ArrayList<String> al;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        btnNorth = findViewById(R.id.btn1);
        btnCentral = findViewById(R.id.btn2);
        btnEast = findViewById(R.id.btn3);

        spn = findViewById(R.id.spinner);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                // zoom in to causeway point
                LatLng poi_Original = new LatLng(1.3578828,103.83543);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Original, 10));

                // show current location
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                }else{
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                // place marker "North"
                LatLng poi_North = new LatLng(1.4234492,103.8118007);
                Marker north = map.addMarker(new MarkerOptions()
                        .position(poi_North)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));

                // place marker "Central"
                LatLng poi_Central = new LatLng(1.3107162,103.8396721);
                Marker central = map.addMarker(new MarkerOptions()
                        .position(poi_Central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                                "Operating hours: 11am-8pm\n" +
                                "Tel:67788652\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                // place marker "East"
                LatLng poi_East = new LatLng(1.3571797,103.9014768);
                Marker east = map.addMarker(new MarkerOptions()
                        .position(poi_East)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North, 13));
                        String msg = north.getTitle();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

                btnCentral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central, 13));
                        String msg = central.getTitle();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East, 13));
                        String msg = east.getTitle();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North, 13));
                            String msg = north.getTitle();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } else if (i == 1){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central, 13));
                            String msg = central.getTitle();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } else if (i == 2){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East, 13));
                            String msg = east.getTitle();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });

            }
        });
    }
}