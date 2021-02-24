package ar.com.portlander.sendmeal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Random;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private GoogleMap myMap;
    private LatLng posicionLocal;
    private LatLng posicionOriginal;
    private Marker localMarker;
    private Marker originalMarker;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap=googleMap;
        myMap.setOnMapLongClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        myMap.setMyLocationEnabled(true);
        organizarMapa();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 23: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myMap.setMyLocationEnabled(true);
                    organizarMapa();
                } else {}
                return;
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void organizarMapa() {
        myMap.setMyLocationEnabled(true);
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMap.setTrafficEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.getUiSettings().setRotateGesturesEnabled(true);
        myMap.getUiSettings().setTiltGesturesEnabled(true);

        Location location = ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        posicionOriginal = new LatLng(location.getLatitude(), location.getLongitude());

        myMap.moveCamera(CameraUpdateFactory.newLatLng(posicionOriginal));

        generarLocal();

        MarkerOptions localMarker = new MarkerOptions();
        localMarker.position(posicionLocal);
        localMarker.title("Local");
        localMarker.icon(BitmapDescriptorFactory.defaultMarker(69));
        this.localMarker = myMap.addMarker(localMarker);

        MarkerOptions originalMarker = new MarkerOptions();
        originalMarker.position(posicionOriginal);
        originalMarker.title("Envio");
        this.originalMarker = myMap.addMarker(originalMarker);
    }

    private void generarLocal(){
        Random r = new Random();

        int direccionRandomEnGrados = r.nextInt(360);

        int distanciaMinima = 100;
        int distanciaMaxima = 1000;
        int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

        LatLng nuevaPosicion = SphericalUtil.computeOffset(
                posicionOriginal,
                distanciaRandomEnMetros,
                direccionRandomEnGrados
        );

        posicionLocal = nuevaPosicion;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        this.originalMarker.remove();
        posicionOriginal = latLng;
        MarkerOptions originalMarker = new MarkerOptions();
        originalMarker.position(posicionOriginal);
        originalMarker.title("Envio");
        this.originalMarker = myMap.addMarker(originalMarker);

        if(polyline != null)    polyline.remove();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(0x66FF0000);

        polylineOptions.add(posicionOriginal, posicionLocal);

        Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();

        polyline = myMap.addPolyline(polylineOptions);
    }

    public void confirmarUbicacion(View view){
        Intent intent = new Intent();
        intent.putExtra("localization", posicionOriginal);
        MapActivity.this.setResult(Activity.RESULT_OK, intent);
        MapActivity.this.finish();
    }
}
