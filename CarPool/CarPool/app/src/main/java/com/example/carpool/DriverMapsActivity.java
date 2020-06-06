package com.example.carpool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.skyfishjy.library.RippleBackground;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,com.google.android.gms.location.LocationListener {

        private GoogleMap mMap;
        private FusedLocationProviderClient mFusedLocationProviderClient;
        private PlacesClient placesClient;
        private List<AutocompletePrediction> predictionList;
        private  GoogleApiClient Api;


        private Location mLastKnownLocation;
        private LocationCallback locationCallback;

        private MaterialSearchBar materialSearchBar;
        private View mapView;
        private Button btnFind;
        private RippleBackground rippleBg;

        private final float DEFAULT_ZOOM = 15;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_driver_maps);

     //       materialSearchBar = findViewById(R.id.searchBar);
            btnFind = findViewById(R.id.btn_find);
            rippleBg = findViewById(R.id.ripple_bg);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mapView = mapFragment.getView();

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DriverMapsActivity.this);
            Places.initialize(DriverMapsActivity.this, getString(R.string.map_api));
            placesClient = Places.createClient(this);
            final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

////            materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
////                @Override
////                public void onSearchStateChanged(boolean enabled) {
////
////                }
////
////                @Override
////                public void onSearchConfirmed(CharSequence text) {
////                    startSearch(text.toString(), true, null, true);
////                }
////
////                @Override
////                public void onButtonClicked(int buttonCode) {
////                    if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
////                        //opening or closing a navigation drawer
////                    } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
////                        //materialSearchBar.disableSearch();
////                    }
////                }
////            });
//
//            materialSearchBar.addTextChangeListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
//                            .setTypeFilter(TypeFilter.ADDRESS)
//                            .setSessionToken(token)
//                            .setQuery(s.toString())
//                            .build();
//                    placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
//                        @Override
//                        public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
//                            if (task.isSuccessful()) {
//                                FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
//                                if (predictionsResponse != null) {
//                                    predictionList = predictionsResponse.getAutocompletePredictions();
//                                    List<String> suggestionsList = new ArrayList<>();
//                                    for (int i = 0; i < predictionList.size(); i++) {
//                                        AutocompletePrediction prediction = predictionList.get(i);
//                                        suggestionsList.add(prediction.getFullText(null).toString());
//                                    }
//                                    materialSearchBar.updateLastSuggestions(suggestionsList);
//                                    if (!materialSearchBar.isSuggestionsVisible()) {
//                                        materialSearchBar.showSuggestionsList();
//                                    }
//                                }
//                            } else {
//                                Log.i("mytag", "prediction fetching task unsuccessful");
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//
//            materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
//                @Override
//                public void OnItemClickListener(int position, View v) {
//                    if (position >= predictionList.size()) {
//                        return;
//                    }
//                    AutocompletePrediction selectedPrediction = predictionList.get(position);
//                    String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
//                    materialSearchBar.setText(suggestion);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            materialSearchBar.clearSuggestions();
//                        }
//                    }, 1000);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    if (imm != null)
//                        imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
//                    final String placeId = selectedPrediction.getPlaceId();
//                    List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
//
//                    FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
//                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
//                        @Override
//                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
//                            Place place = fetchPlaceResponse.getPlace();
//                            Log.i("mytag", "Place found: " + place.getName());
//                            LatLng latLngOfPlace = place.getLatLng();
//                            if (latLngOfPlace != null) {
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            if (e instanceof ApiException) {
//                                ApiException apiException = (ApiException) e;
//                                apiException.printStackTrace();
//                                int statusCode = apiException.getStatusCode();
//                                Log.i("mytag", "place not found: " + e.getMessage());
//                                Log.i("mytag", "status code: " + statusCode);
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void OnItemDeleteListener(int position, View v) {
//
//                }
//            });
            btnFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    rippleBg.startRippleAnimation();
//
//
//                    switch (v.getId())
//                    {
//                        case R.id.btn_find:
//
//                           MaterialSearchBar addressField = (MaterialSearchBar) findViewById(R.id.searchBar);
//                            String address = addressField.getText().toString();
//
//                            List<Address> addressList = null;
//                            MarkerOptions userMarkerOptions = new MarkerOptions();
//
//                            if (!TextUtils.isEmpty(address)) {
//                                Geocoder geocoder = new Geocoder(DriverMapsActivity.this);
//                                try {
//                                    addressList = geocoder.getFromLocationName(address, 6);
//                                    if (addressList != null) {
//                                        for (int i = 0; i <= addressList.size(); i++) {
//                                            Address userAddress = addressList.get(i);
//                                            LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());
//
//                                            userMarkerOptions.position(latLng);
//                                            userMarkerOptions.title(address);
//                                            userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
//                                            mMap.addMarker(userMarkerOptions);
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//                                        }
//                                    }
//
//                                    else
//                                    {
//                                        Toast.makeText(DriverMapsActivity.this,"not found",Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            else
//                            {
//                                Toast.makeText(DriverMapsActivity.this,"Please write any location anme",Toast.LENGTH_SHORT).show();
//                            }
//                            break;
//                    }

                    Intent intent = new Intent(DriverMapsActivity.this,Driver_Rides_Selection.class);
                    startActivity(intent);


                }
            });
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
           // mMap.setMyLocationEnabled(true);
          //  mMap.getUiSettings().setMyLocationButtonEnabled(true);

          /*  if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 40, 180);
            }*/

            //check if gps is enabled or not and then request user to enable it
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(DriverMapsActivity.this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

            task.addOnSuccessListener(DriverMapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    getDeviceLocation();
                    buildGoogleApiClient();
                }


            });

            task.addOnFailureListener(DriverMapsActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        try {
                            resolvable.startResolutionForResult(DriverMapsActivity.this, 51);
                        } catch (IntentSender.SendIntentException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (materialSearchBar.isSuggestionsVisible())
                        materialSearchBar.clearSuggestions();
                    //if (materialSearchBar.isSearchEnabled())
                      //  materialSearchBar.disableSearch();
                    return false;
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 51) {
                if (resultCode == RESULT_OK) {
                    getDeviceLocation();
                }
            }
        }

        @SuppressLint("MissingPermission")
        private void getDeviceLocation() {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                mLastKnownLocation = task.getResult();
                                if (mLastKnownLocation != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                } else {
                                    final LocationRequest locationRequest = LocationRequest.create();
                                    locationRequest.setInterval(10000);
                                    locationRequest.setFastestInterval(5000);
                                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                    locationCallback = new LocationCallback() {
                                        @Override
                                        public void onLocationResult(LocationResult locationResult) {
                                            super.onLocationResult(locationResult);
                                            if (locationResult == null) {
                                                return;
                                            }
                                            mLastKnownLocation = locationResult.getLastLocation();
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                        }
                                    };
                                    mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                                }
                            } else {
                                Toast.makeText(DriverMapsActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    protected synchronized void buildGoogleApiClient() {
        Api= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this).addApi(LocationServices.API).build();
        Api.connect();
    }
    @Override
    public void onLocationChanged(Location location) {
//        mLastKnownLocation = location;
//        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
////       // DatabaseReference data = FirebaseDatabase.getInstance().getReference("User");
//////
//        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("User");
//        DatabaseReference zone1Ref = zonesRef.child(Uid);
//        DatabaseReference name = zone1Ref.child("name");
//
//        name.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
//               // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//                //DataSnapshot value = new DataSnapshot().getValue();
//                //  Log.i("SAmi", snapshot.getValue(String.class));
//                  ref.child(Uid).child("Name").setValue(snapshot.getValue(String.class));
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//
//       // name.setValue("Sami");
//       // ref.child(Uid).child("g").setValue();
//
//        GeoFire geoFire = new GeoFire(ref);
//       // geoFire.setLocation(Uid,new GeoLocation(location.getLatitude(),location.getLongitude()));
//        geoFire.setLocation(Uid,new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error!=null)
//                {
//                    Toast.makeText(DriverMapsActivity.this,"Can't go Active",Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(DriverMapsActivity.this,"You are Active",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(Api,locationRequest,this);

      //  mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);


    }

    @Override
    public void onConnectionSuspended(int i) {
//        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
////       ref.child(Uid).removeValue();
//        GeoFire geoFire = new GeoFire(ref);
//        geoFire.removeLocation(Uid, new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error!=null)
//                {
//                    Toast.makeText(DriverMapsActivity.this,"REHHH",Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(DriverMapsActivity.this,"Request Removed",Toast.LENGTH_SHORT).show();
//            }
//
//        });
//


    }

    @Override
    protected void onStop() {
        super.onStop();
//        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
//
////        ref.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot snapshot) {
////                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RidesAvailable");
////                // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
////                //DataSnapshot value = new DataSnapshot().getValue();
////                //  Log.i("SAmi", snapshot.getValue(String.class));
////                ref.child(Uid).child("Name").removeValue();
////            }
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////            }
////        });
//
//
//
//        GeoFire geoFire = new GeoFire(ref);
//        geoFire.removeLocation(Uid, new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//                if (error!=null)
//                {
//                    Toast.makeText(DriverMapsActivity.this,"REHHH",Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(DriverMapsActivity.this,"Request Removed",Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }


}