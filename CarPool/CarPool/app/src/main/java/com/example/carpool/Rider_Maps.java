package com.example.carpool;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Rider_Maps extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,com.google.android.gms.location.LocationListener {
    Location mlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private View mapView;
    //              Search View Code
    GoogleMap map;
    private  GoogleApiClient Api;
   // SearchView searchView;
    SupportMapFragment mapFragment;
    private Button request;

    Spinner spinner;
    private static final int Request_Code=101;





    // Create a new Places client instance.
    //PlacesClient placesClient = Places.createClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__maps);
       //  spinner = (Spinner)findViewById(R.id.spinner1);


        //              Search View Code




     //  searchView = (SearchView)findViewById(R.id.location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String location = searchView.getQuery().toString();
////                searchView.setQuery(text,false);
//                searchView.clearFocus();
//                List<Address> addressList = null;
//                if (location!=null||!location.equals("")){
//                    Geocoder geocoder = new Geocoder(Rider_Maps.this);
//                    try {
//                        addressList=geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//
//                    map.addMarker(new MarkerOptions().position(latLng).title(location));
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
          //   mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        GetlastLocation();

    }



    private void GetlastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mlocation = location;
                    Toast.makeText(getApplicationContext(), mlocation.getLatitude() + "" + mlocation.getLongitude(),
                            Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); supportMapFragment.getMapAsync(Rider_Maps.this);
                }
            }
        });

        request = (Button) findViewById(R.id.search_driver);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
//       // DatabaseReference data = FirebaseDatabase.getInstance().getReference("User");
////
                DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("User");
                DatabaseReference zone1Ref = zonesRef.child(Uid);
                DatabaseReference name = zone1Ref.child("name");
                DatabaseReference number = zone1Ref.child("number");



                number.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
                        ref.child(Uid).child("Number").setValue(dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                name.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
                        // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                        //DataSnapshot value = new DataSnapshot().getValue();
                        //  Log.i("SAmi", snapshot.getValue(String.class));
                        ref.child(Uid).child("Name").setValue(snapshot.getValue(String.class));
                     //   final String text = spinner.getSelectedItem().toString();
                     //   ref.child(Uid).child("PickUp_Point").setValue(text);
                        ref.child(Uid).child("Key").setValue(Uid);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }


                });
                DatabaseReference des = FirebaseDatabase.getInstance().getReference("Destination");
                DatabaseReference des1 = des.child(Uid);
               // DatabaseReference des2 = des1.child("Destination");
                des1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Des2 = dataSnapshot.child("Destination").getValue().toString();
                        String Des3 = dataSnapshot.child("Vehicle_Type").getValue().toString();
                        String Des4 = dataSnapshot.child("PickUp_Point").getValue().toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
                        ref.child(Uid).child("Destination").setValue(Des2);
                        ref.child(Uid).child("Vehicle_Type").setValue(Des3);
                        ref.child(Uid).child("PickUp_Point").setValue(Des4);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                GeoFire geoFire = new GeoFire(ref);
                // geoFire.setLocation(Uid,new GeoLocation(location.getLatitude(),location.getLongitude()));
                geoFire.setLocation(Uid,new GeoLocation(mlocation.getLatitude(), mlocation.getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error!=null)
                        {
                            Toast.makeText(Rider_Maps.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Rider_Maps.this,"You are Active",Toast.LENGTH_SHORT).show();
                    }
                });

//                String [] destination = Rider_Maps.this.getResources().getStringArray(R.array.);
//                AlertDialog.Builder builder = new AlertDialog.Builder(Rider_Maps.this);
//                builder.setTitle("Pick up your Destination");
//                builder.setSingleChoiceItems(R.arr)

                Intent intent = new Intent(Rider_Maps.this,rider_waiting.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 250);
        }


        //map = googleMap;
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LatLng latLng = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
//        String address = getAddress(this);
        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("Current Location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(markerOptions);
        buildGoogleApiClient();

        String Uid2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference d= FirebaseDatabase.getInstance().getReference("Destination");
        DatabaseReference d2 = d.child(Uid2);


        d2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Des4 = dataSnapshot.child("PickUp_Point").getValue().toString();

                if(Des4.equals("Sadar")){
                    LatLng sadar = new LatLng(33.5914237, 73.0535122);
                    map.addMarker(new MarkerOptions().position(sadar).title("Sadar"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(sadar, 15));

                }else if(Des4.equals("F8 Markaz")){
                    LatLng f8 = new LatLng(33.712382, 73.036899);
                    //    sydney.showInfoWindow();
                    map.addMarker(new MarkerOptions().position(f8).title("F8 Markaz"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(f8, 15));

                }
               else if(Des4.equals("G9 Markaz")) {
                    LatLng g9 = new LatLng(33.690036, 73.030187);
                    map.addMarker(new MarkerOptions().position(g9).title("G9 Markaz"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(g9, 15));
                }
               else {
                    LatLng Air = new LatLng(33.713818, 73.026399);
                    map.addMarker(new MarkerOptions().position(Air).title("Air University"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(Air, 15));

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void buildGoogleApiClient() {
        Api= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addApi(LocationServices.API).build();
        Api.connect();
    }


    //       Get Address from lat long

    public String getAddress(Context ctx){
        String fullAdd=null;
        try{
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(mlocation.getLatitude(),mlocation.getLongitude(),1);
            if (addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return fullAdd;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_Code:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    GetlastLocation();
                }
                break;
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(Api,locationRequest,this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


    }
}