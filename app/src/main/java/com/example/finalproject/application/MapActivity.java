package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import com.example.finalproject.R;
import com.example.finalproject.utils.LocationUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    private static String location;

    private static String lastPage;

    private static final String LOCATION_KEY = "location";

    private static final String LAST_PAGE_KEY = "lastPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);
        Bundle parameter = getIntent().getExtras();
        if (parameter != null) {
            location = Objects.requireNonNull(parameter.get(LOCATION_KEY)).toString();
            lastPage = Objects.requireNonNull(parameter.get(LAST_PAGE_KEY)).toString();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button button = (Button) findViewById(R.id.backPreviousPage);
        button.setOnClickListener(v -> {
            if (lastPage.equals("courseResult")) {
                startActivity(new Intent(MapActivity.this, CourseResultActivity.class));
            } else {
                startActivity(new Intent(MapActivity.this, CourseListActivity.class));
            }
        });
    }


    /**j
     * Manipulates the map_page once available.
     * This callback is triggered when the map_page is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SneakyThrows
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        List<Double> latlng = LocationUtil.getLangitudeAndLatitude(location);
        LatLng building = new LatLng(latlng.get(0), latlng.get(1));
        mMap.addMarker(new MarkerOptions().position(building));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(building,16.0f));
    }
}