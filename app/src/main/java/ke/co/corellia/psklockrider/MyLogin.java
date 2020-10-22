package ke.co.corellia.psklockrider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MyLogin extends AppCompatActivity {

    private  String password,email,driver;
    private TextInputEditText txtpassword,txtemail;
    private MaterialButton btnCancel, btnSignUp,btns;
    private MaterialTextView reps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        driver="0";

        reps  =findViewById(R.id.dresponse);
        btnCancel  =findViewById(R.id.dbtncancel);
        btnSignUp  =findViewById(R.id.dbtnsignup);
       // btns=findViewById(R.id.dbtnsign);

        txtpassword  =findViewById(R.id.spassword);
        txtemail  =findViewById(R.id.semail);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyLogin.this, SignUp.class);
                 startActivity(intent2);// Activity is started with requestCode 2
            }
        });*/

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password=txtpassword.getText().toString();
                email=txtemail.getText().toString();

              cDriverSignIn myTask = new cDriverSignIn(getApplicationContext(),"David@gmail.com");

                myTask.execute();
            }
        });


    }

    private void fin(){
        finish();
    }


    private class cDriverSignIn extends AsyncTask<String, Void, String> {

        private Context context;
        private ProgressDialog dialog;

        private String customer;

        String rep;



        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value




            String h =rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //  tc.setText(result);
            reps.setText(result);
            driver=result;
            LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();



            if (!driver.equals("0")){
                SharedPreferences sharedPref = context.getSharedPreferences("name",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("id", driver);
                editor.putString("driver", driver);
                instance1.setDriver(driver);
                editor.putString("name", email);
                editor.commit();
                fin();
            }
            else
            {
                reps.setText("Check Your email or Password");
            }

        }

        public cDriverSignIn(Context context, String customer) {
            this.context = context;
            this.customer = customer;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}



        public void whenSendPostRequest_thenCorrect()
                throws IOException {


            OkHttpClient client = new OkHttpClient();

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";
            String url="https://corellia.co.ke/rider/one.php?action=driverlogin&email="+email+"&password="+password;


            okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                rep = response.body().string().replaceAll("\\n","");
                rep = rep.replaceAll("\\r","");
                instance1.setDriver(rep);

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Please Confirm");
        builder.setMessage("Exit Klok?");
        builder.setCancelable(true);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when user want to exit the app
                // Let allow the system to handle the event, such as exit the app
               // MainActivity.super.onBackPressed();
                //getActivity().finish();
                finishAffinity();
                finish();
                System.exit(0);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // Create the alert dialog using alert dialog builder
        AlertDialog dialog = builder.create();

        // Finally, display the dialog when user press back button
        dialog.show();
    }
}
