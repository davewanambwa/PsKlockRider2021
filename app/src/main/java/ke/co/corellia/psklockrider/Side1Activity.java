package ke.co.corellia.psklockrider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ke.co.corellia.psklockrider.ui.ui.login.LoginFragment;

public class Side1Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Fragment fragment;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Double distance=0.0;
    private MaterialTextView myinfo;

    NavigationView mNavigationView;
    View mHeaderView;

    TextView textViewUsername;
    TextView textViewEmail;

    LazyInitializedSingletonExample instance1;

    private MaterialButton btnstart, btn2;
    private int state = 0;
    private int pickup=0;
    private String id;
    private String driver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View view) {
          //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //            .setAction("Action", null).show();
           // }
        //});
        ///spalash
        Intent intent5 = new Intent(Side1Activity.this, splash.class);
        startActivity(intent5);// Activity is started with requestCode 2
        state = 1;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_map)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


     /* Fragment fragment2 = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        //get fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //set new fragment in fragment_container (FrameLayout)
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment2);
        fragmentTransaction.commit();*/
try {
    SharedPreferences sharedPref = this.getSharedPreferences("mydata", Context.MODE_PRIVATE);
    String email = sharedPref.getString("mydata", null);
    id = sharedPref.getString("id", null);

    driver = sharedPref.getString("driver", null);
    LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
    instance1.setDriver(driver);





    ////
    mNavigationView = (NavigationView) findViewById(R.id.nav_view);

    // NavigationView Header
    mHeaderView =  mNavigationView.getHeaderView(0);

    // View
    textViewUsername = (TextView) mHeaderView.findViewById(R.id.textViewUsernameNav);
    textViewEmail= (TextView) mHeaderView.findViewById(R.id.textViewEmailNav);

    // Set username & email
    //textViewUsername.setText(email);

    textViewUsername.setVisibility(View.GONE);
    textViewEmail.setText(email);




}
catch( Exception e)
{
    driver =null;
}

        if (driver == null) {

            Intent intent = new Intent(Side1Activity.this, Login.class);
            startActivity(intent );// Activity is started with requestCode 2

        }
        else
        {
            //cGetRequests myTask = new cGetRequests(getApplicationContext(),"David@gmail.com");

            // myTask.execute();



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side1, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}