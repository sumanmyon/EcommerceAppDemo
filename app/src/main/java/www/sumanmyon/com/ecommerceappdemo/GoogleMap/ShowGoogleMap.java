package www.sumanmyon.com.ecommerceappdemo.GoogleMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import www.sumanmyon.com.ecommerceappdemo.R;

public class ShowGoogleMap extends AppCompatActivity implements OnMapReadyCallback {
    Double lat;
    Double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_google_map);

        Bundle bundle = getIntent().getExtras();
        String location = bundle.getString("location");
        String[] split = location.split("::");
        lat = Double.valueOf(split[0]);
        lng = Double.valueOf(split[1]);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        addMarker(googleMap);
        zoomCameraToMarker(googleMap);
    }

    private void addMarker(GoogleMap googleMap) {
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(lat, lng));
        googleMap.addMarker(options);
    }

    private void zoomCameraToMarker(GoogleMap googleMap) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),14);
        googleMap.moveCamera(update);
    }
}
