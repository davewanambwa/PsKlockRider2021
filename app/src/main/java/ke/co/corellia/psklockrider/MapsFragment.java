package ke.co.corellia.psklockrider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MapsFragment extends Fragment {
    LazyInitializedSingletonExample instance1;
    private FusedLocationProviderClient fusedLocationClient;
    private Double distance = 0.0;
    private GoogleMap mMap;
    private String id;
    private MaterialTextView myinfo;


    private Button btnstart, btn2;
    private int state = 0;
    private int pickup = 0;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        AppConstants.LOCATION_REQUEST);

                   googleMap.setMyLocationEnabled(true);
            } else {
                //
            }
            LatLng sydney = new LatLng(-1.2, 36.8);
            // googleMap.addMarker(new MarkerOptions().position(sydney).title("clifftop to langata").icon(BitmapDescriptorFactory.fromResource(R.drawable.human)));
            mMap = googleMap;
            sydney = new LatLng(1.201, 36.801);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("clifftop to langata2").icon(BitmapDescriptorFactory.fromResource(R.drawable.human)));

            instance1.getConfirm().equals("0");
            // googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            final Handler handler = new Handler();
            final Timer timer = new Timer();
            TimerTask doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @SuppressWarnings("unchecked")
                        public void run() {
                            try {

                                cGetRequests myTask = new cGetRequests(getContext(), "David@gmail.com");

                                myTask.execute();

                                //updatemyloctoserver();

                                if (instance1.getConfirm().equals("1")) {
                                    timer.cancel();
                                }


                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };
            timer.schedule(doAsynchronousTask, 0, 10000);

        }


    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    private boolean isPermisionAccess() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        state = 1;

        btn2 = getView().findViewById(R.id.mycancel);
        btn2.setEnabled(false);
        btnstart = getView().findViewById(R.id.mystart);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s = 0;
                if (state == 1) {
                    s = 2;
                    btnstart.setText("End Trip");
                    btn2.setEnabled(false);
                    myinfo.setText("You are on your way!");
                    state = 2;
                } else if (state == 2) {
                    s = 3;
                    btnstart.setEnabled(false);
                    btn2.setEnabled(true);


                    cupdateMyRideb cupdateMyRide = new cupdateMyRideb(instance1.getMyRide().getId());
                    cupdateMyRide.execute();
                    //  Intent intent = new Intent(MapsActivity.this, ratehim.class);
                    //  startActivityForResult(intent,4);// Activity is started with requestCode 2
                    Intent intent = new Intent(getActivity(), ratehim.class);
                    startActivity(intent);
                    instance1.setConfirm("0");
                    myinfo.setText("No Clients Available");
                    btn2.setEnabled(false);
                    btnstart.setText("Start");
                    btnstart.setEnabled(false);
                    final Handler handler = new Handler();
                    final Timer timer = new Timer();
                    TimerTask doAsynchronousTask = new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @SuppressWarnings("unchecked")
                                public void run() {
                                    try {

                                        cGetRequests myTask = new cGetRequests(getContext(), "David@gmail.com");

                                        myTask.execute();

                                        //updatemyloctoserver();

                                        if (instance1.getConfirm().equals("1")) {
                                            timer.cancel();
                                        }


                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(doAsynchronousTask, 0, 10000);

                }
                state = s;

            }
        });

        myinfo = getView().findViewById(R.id.myinfo);

        Button btn = getView().findViewById(R.id.mynavigate);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cCancelMyRide cupdateMyRide = new cCancelMyRide(instance1.getMyRide().getId());
                cupdateMyRide.execute();
                // MapsActivity.super.onBackPressed();

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LatLng gdest = instance1.getGdest();

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + instance1.getMyRide().getClat() + "," + instance1.getMyRide().getClong());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


        instance1 = LazyInitializedSingletonExample.getInstance();


        //per


    }

    private void updatemyloctoserver() {

        //mylocarion
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            Marker m1 = mMap.addMarker(new MarkerOptions().position(sydney).title("Me").icon(BitmapDescriptorFactory.fromResource(R.drawable.fuel)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

                            RideRequest Cride = instance1.getMyRide();
                            LatLng clienl = new LatLng(Double.valueOf(Cride.getClat()), Double.valueOf(Cride.getClong()));
                            Marker m2 = mMap.addMarker(new MarkerOptions().position(clienl).title("Client").icon(BitmapDescriptorFactory.fromResource(R.drawable.human)));


                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

//the include method will calculate the min and max bound.
                            builder.include(m1.getPosition());
                            builder.include(m2.getPosition());


                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.32); // offset from edges of the map 10% of screen

                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);


                            CameraUpdate cu2 = CameraUpdateFactory.newLatLngBounds(bounds, 20, 25, 5);
                            mMap.animateCamera(cu);


                            instance1.setGdest(clienl);
                            //start nav
                            /*
                            Uri gmmIntentUri = Uri.parse("google.navigation:q="+clienl.latitude+","+clienl.longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent); */


                            // Start downloading json data from Google Directions API
                            try {
                                String url = getDirectionsUrl(sydney, clienl);

                                DownloadTask downloadTask = new DownloadTask();
                                downloadTask.execute(url).get();

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            cupdateMyRide cupdateMyRide = new cupdateMyRide(instance1.getMyRide().getId(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                            cupdateMyRide.execute();
                        }
                    }
                });
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyBaG5F4icbXMtc6vBVFJHGsHr4J4wPfpBA";


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class cupdateMyRide extends AsyncTask<String, Void, String> {


        private String rep, rid, clat, clong;


        public cupdateMyRide(String rid, String clat, String clong) {
            this.rid = rid;
            this.clat = clat;
            this.clong = clong;
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            String rid = instance1.getMyRide().getId();
            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url = "https://corellia.co.ke/rider/one.php?action=updaterequest&rid=" + rid + "&RiderLat=" + clat + "&RiderLong=" + clong;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();


                rep = response.body().string().replaceAll("\\n", "");
                rep = response.body().string().replaceAll("\\r", "");
                instance1.setDriver(rep);

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
                String d = data.toLowerCase();
                int distanceo = d.indexOf("distance");
                String resto = d.substring(distanceo, distanceo + 50);
                resto = resto.replace("\"", "");

                int distanceb = resto.indexOf("text");
                resto = resto.substring(distanceb + 6);
                int distancec = resto.indexOf("km");
                resto = resto.substring(0, distancec);

                resto = resto.trim();

                distance = Double.valueOf(resto);

            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);
            try {
                myinfo.setText("Drive to pick up point, " + distance + " kms");
                double k = Double.valueOf(distance);


                if (k < 1) {

                    if (pickup == 0) {
                        // Intent intent = new Intent(MapsActivity.this, photop.class);
                        //  startActivity(intent);// Activity is started with requestCode 2
                        pickup = 1;
                    }
                    btnstart.setEnabled(true);
                    state = 1;

                } else {
                    btnstart.setEnabled(false);
                }

            } catch (Exception e) {
                String h = e.getMessage();
            }
        }
    }

    private class cupdateMyRideb extends AsyncTask<String, Void, String> {


        private String rep, rid, clat, clong;


        public cupdateMyRideb(String rid) {
            this.rid = rid;

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            // MapsActivity.super.onBackPressed();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            String rid = instance1.getMyRide().getId();
            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url = "https://corellia.co.ke/rider/one.php?action=updaterequestb&rid=" + rid;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();


                rep = response.body().string().replaceAll("\\n", "");
                rep = response.body().string().replaceAll("\\r", "");
                instance1.setDriver(rep);

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class cTakeRequest extends AsyncTask<String, Void, String> {


        private String rep, myid, rid;


        public cTakeRequest() {

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            Toast.makeText(getContext(), "Drive to pick up point", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            String rid = instance1.getMyRide().getId();
            String dr = instance1.getDriver();
            id = instance1.getDriver();
            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url = "https://corellia.co.ke/rider/one.php?action=takerequest&rid=" + rid + "&Myid=" + dr;

            // instance1.setMyid(id);


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();


                rep = response.body().string().replaceAll("\\n", "");
                rep = response.body().string().replaceAll("\\r", "");
                instance1.setDriver(rep);

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class cCancelMyRide extends AsyncTask<String, Void, String> {


        private String rep, rid, clat, clong;


        public cCancelMyRide(String rid) {
            this.rid = rid;
            this.clat = clat;
            this.clong = clong;
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            myinfo.setText("Drive to pick up point, " + result + " kms");


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            String rid = instance1.getTripid();
            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url = "https://corellia.co.ke/rider/one.php?action=cancelrequest&rid=" + rid;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();


                rep = response.body().string().replaceAll("\\n", "");
                rep = response.body().string().replaceAll("\\r", "");
                instance1.setDriver(rep);

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class cGetRequests extends AsyncTask<String, Void, String> {

        String rep;
        private Context context;
        private ProgressDialog dialog;
        private String customer;
        private GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();

                } else {
                    marker.showInfoWindow();
                    Cdialog cdd = new Cdialog(getActivity());
                    cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //  Toast.makeText(getContext(), instance1.getMyid(),Toast.LENGTH_LONG).show();
                            MaterialTextView tx = getView().findViewById(R.id.myinfo);
                            final Handler handler = new Handler();
                            Timer timer = new Timer();


                            if (instance1.getConfirm().equals("1")) {

                                tx.setText("Navigate to " + instance1.getMyRide().getCtown());

                                cTakeRequest myTask = new cTakeRequest();

                                myTask.execute();


                                TimerTask doAsynchronousTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(new Runnable() {
                                            @SuppressWarnings("unchecked")
                                            public void run() {
                                                try {

                                                    // cGetRequests myTask = new cGetRequests(getContext(),"David@gmail.com");

                                                    // myTask.execute();
                                                    if (instance1.getConfirm().equals("1")) {
                                                        updatemyloctoserver();
                                                    }


                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                };
                                timer.schedule(doAsynchronousTask, 0, 10000);


                            } else {

                                //timer.cancel();
                                tx.setText("SELECT CLIENT ON MAP");
                                TimerTask doAsynchronousTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(new Runnable() {
                                            @SuppressWarnings("unchecked")
                                            public void run() {
                                                try {
                                                    if (instance1.getConfirm().equals("0")) {

                                                        cGetRequests myTask = new cGetRequests(getContext(), "");

                                                        myTask.execute();
                                                    } else {

                                                    }
                                                    // updatemyloctoserver();


                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                };
                                timer.schedule(doAsynchronousTask, 0, 10000);

                            }
                        }
                    });
                    RideRequest rd = new RideRequest("trip");
                    rd = (RideRequest) marker.getTag();

                    instance1.setMyRide(rd);

                    cdd.show();


                }
                return true;
            }
        };

        public cGetRequests(Context context, String customer) {
            this.context = context;
            this.customer = customer;
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //tv.setText("ACTIVE REQUESTS");
            mMap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            //  adapter.clear();
            try {

                if (result.equals("0")) {

                } else {

                    //   reps.setText("Check Your email or Password");
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); ++i) {

                            JSONObject itemObj = jsonArray.getJSONObject(i);

                            String name = itemObj.getString("customer");

                            RideRequest rd = new RideRequest(name);
                            rd.setId(itemObj.getString("id"));
                            rd.setDestination(itemObj.getString("destination"));
                            rd.setDistance(itemObj.getString("distance"));
                            rd.setSource(itemObj.getString("source"));
                            rd.setClat(itemObj.getString("clat"));
                            rd.setClong(itemObj.getString("clong"));
                            rd.setRlat(itemObj.getString("rlat"));
                            rd.setRlong(itemObj.getString("rlong"));

                            rd.setRtown(itemObj.getString("rtown"));
                            rd.setCtown(itemObj.getString("ctown"));
                            rd.setPack(itemObj.getString("package"));


                            Double lt = Double.parseDouble(rd.getClat());
                            Double ln = Double.parseDouble(rd.getClong());
                            String title = rd.getSource();// "+rd.getDestination();


                            LatLng sydney = new LatLng(lt, ln);
                            Marker marker ;
//marker.showInfoWindow();
                            if(rd.getPack().equals("RIDE")) {
                                 marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Client").snippet(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.human)));
//marker.showInfoWindow();
                                marker.setTag(rd);
                                builder.include(marker.getPosition());


                                mMap.setOnMarkerClickListener(onMarkerClickedListener);
                            }
                            else if (rd.getPack().equals("SMALL PARCEL"))
                            {
                                marker = mMap.addMarker(new MarkerOptions().position(sydney).title("small package").snippet(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.small)));
//marker.showInfoWindow();
                                marker.setTag(rd);
                                builder.include(marker.getPosition());


                                mMap.setOnMarkerClickListener(onMarkerClickedListener);
                            }
                            else if (rd.getPack().equals("MEDIUM PARCEL"))
                            {
                                marker = mMap.addMarker(new MarkerOptions().position(sydney).title("medium package").snippet(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.medium)));
//marker.showInfoWindow();
                                marker.setTag(rd);
                                builder.include(marker.getPosition());


                                mMap.setOnMarkerClickListener(onMarkerClickedListener);
                            }
                            else if (rd.getPack().equals("LARGE PARCEL"))
                            {
                                marker = mMap.addMarker(new MarkerOptions().position(sydney).title("large package").snippet(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.large)));
//marker.showInfoWindow();
                                marker.setTag(rd);
                                builder.include(marker.getPosition());


                                mMap.setOnMarkerClickListener(onMarkerClickedListener);
                            }



                            // adapter.add(rd);

                            //bound


//the include method will calculate the min and max bound.



                        }
                        LatLngBounds bounds = builder.build();

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.33); // offset from edges of the map 10% of screen

                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);


                        CameraUpdate cu2 = CameraUpdateFactory.newLatLngBounds(bounds, 20, 25, 5);
                        mMap.animateCamera(cu);


                        //


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            } catch (Exception c) {

            }

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url = "https://corellia.co.ke/rider/one.php?action=getrequests";


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                rep = response.body().string().replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");


                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }


                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.YELLOW);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

        }
    }
}