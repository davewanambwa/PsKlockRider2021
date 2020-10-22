package ke.co.corellia.psklockrider;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Cdialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
    RideRequest rd;

    public Cdialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        instance1 = LazyInitializedSingletonExample.getInstance();
        rd = instance1.getMyRide();
        TextView tx = findViewById(R.id.txt_dia);
        tx.setText(rd.getSource());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                instance1.setConfirm("1");
                break;
            case R.id.btn_no:
                dismiss();
                instance1.setConfirm("2");
                break;
            default:
                break;
        }
        dismiss();
    }
}
