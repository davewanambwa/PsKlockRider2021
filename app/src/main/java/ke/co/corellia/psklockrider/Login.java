package ke.co.corellia.psklockrider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Activity {

    private String password,email,driver,phone;
    private EditText txtemail;
    private TextInputEditText txtpassword;
    private MaterialButton btnCancel, btnSignUp,btns;
    private TextView reps;
    CountryCodePicker ccp;
    LazyInitializedSingletonExample lz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.detectSIMCountry(true);
        ccp.setDefaultCountryUsingNameCode("KE");

        AppCompatTextView bt1 =findViewById(R.id.btntx2);
        lz=LazyInitializedSingletonExample.getInstance();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Login.this, forgot.class);
                startActivityForResult(intent2, 2);// Activity is started with requestCode 2btn
            }
        });

        driver="0";
      //  lz=LazyInitializedSingletonExample.getInstance();




        Button btn = findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {








                txtpassword  =findViewById(R.id.code);
                txtemail  =findViewById(R.id.phone);
                email =txtemail.getText().toString();

                String phone =  ccp.getSelectedCountryCodeWithPlus();
                email=phone +email;
                password=txtpassword.getText().toString();
phone =email;

                cSignin myTask = new cSignin(email,password,phone);

                myTask.execute();
            }
        });

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
           // reps.setText(result);
         //   Toast.makeText(context,result,Toast.LENGTH_LONG).show();
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
               // reps.setText("Check Your email or Password");
                Toast.makeText(context,"Wrong Credentials",Toast.LENGTH_LONG).show();
            }

        }
        private void fin(){
            finish();
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

    private class cSignin extends AsyncTask<String, Void, String> {


        private String email,password,rep,phone;



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
            //   tc.setText(result);
            JSONObject reader = null;
            String id ="0";
            String fname ="0";
            String lname ="0";
            String astatus="0";
            String promocode ="0";
            String Email ="0";
            String tt="Wrong phone or password";
            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys  = sysa.getJSONObject(0);

                id= sys.getString("id");
                fname= sys.getString("FirstName");
                lname= sys.getString("LastName");
                promocode= sys.getString("promocode");
                Email = sys.getString("Email");
                astatus= sys.getString("Status");
                //lz.setFcode(sys.getString("promocode"));




            } catch (JSONException e) {
                e.printStackTrace();

            }




            Double rp=0.0;
            rp=Double.valueOf(id);

            if (astatus.equals("Pending"))
            {
                tt ="Please activate your account at psklok.com";
                rp=0.0;
            }

            if (rp>0)
            {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("mydata", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", rp.intValue());
                editor.putString("fname", fname);
                editor.putString("lname", lname);
                editor.putString("fname", fname);


                editor.putString("driver", fname +" "+lname);

                editor.putString("name", email);
                lz.setDriver(id);
                editor.commit(); // commit changes
                Intent intent = getIntent();
                setResult(4, intent);
                finish();


            }
            else
            {

                Toast.makeText(Login.this,tt,Toast.LENGTH_LONG).show();
            }

        }

        public cSignin(String email, String password, String Phone) {



            this.email = email;
            this.password = password;
            this.phone = Phone;





        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}



        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("phone", phone);
                jsonObject.put("password", password);




            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json =jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url="https://corellia.co.ke/rider/two.php?action=getdriver";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://corellia.co.ke/rider/one.php?action=getdr";



            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();




            try{
                //  Call call = client.newCall(request);
                Response response= client.newCall(request).execute();
                // Response response = call.execute();

                rep=response.body().string();
                rep = rep.replaceAll("\\n","");
                rep = rep.replaceAll("\\r","");

                Log.d("dd",rep);
            }
            catch( Exception e) {
                Log.d("dd",e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }
}