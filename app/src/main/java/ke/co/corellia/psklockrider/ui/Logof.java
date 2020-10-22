package ke.co.corellia.psklockrider.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke.co.corellia.psklockrider.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Logof#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Logof extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Logof() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Logof.
     */
    // TODO: Rename and change types and number of parameters
    public static Logof newInstance(String param1, String param2) {
        Logof fragment = new Logof();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setTitle("Please confirm");
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when user want to exit the app
                // Let allow the system to handle the event, such as exit the app
                //  MainActivity.super.onBackPressed();

                SharedPreferences settings = getActivity().getSharedPreferences("mydata", 0);
                settings.edit().clear().commit();
                getActivity().finish();   // stop chronometer here
                System.exit(0);
                //  ridechooser.super.onBackPressed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when want to stay in the app
                // Toast.makeText(getApplicationContext(), "thank you", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });

        // Create the alert dialog using alert dialog builder
        android.app.AlertDialog dialog = builder.create();

        // Finally, display the dialog when user press back button
        dialog.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logof, container, false);


    }
}