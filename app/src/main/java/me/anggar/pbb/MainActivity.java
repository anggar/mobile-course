package me.anggar.pbb;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LocationManager locManager;
    private LocationListener locListener;
    ArrayList<String> requestedPerm = new ArrayList();

    private boolean checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                requestedPerm.add(permission);
            }
        }

        return requestedPerm.isEmpty();
    }

    private void requestPermsFlow() {
        String[] listOfRequestedPerms = new String[requestedPerm.size()];
        listOfRequestedPerms = requestedPerm.toArray(listOfRequestedPerms);
        ActivityCompat.requestPermissions(this, listOfRequestedPerms, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, locListener);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locListener = new InnerLocationListener();

        if (checkPermissions()) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, locListener);
        } else {
            requestPermsFlow();
        }
    }

    private class InnerLocationListener implements LocationListener {
        private TextView tvLat, tvLong;

        @Override
        public void onLocationChanged(Location location) {
            tvLat = findViewById(R.id.tvLat);
            tvLong = findViewById(R.id.tvLong);
            tvLat.setText(String.valueOf(location.getLatitude()));
            tvLong.setText(String.valueOf(location.getLongitude()));
            Toast.makeText(getBaseContext(),"GPS captured", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
