package sony.com.loginform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView tName,tAddress,tEmail,tPh_Number,tPassword;
    Button tLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tLogout.findViewById(R.id.buttonLogout);

        tName.findViewById(R.id.name);
        tAddress.findViewById(R.id.address);
        tEmail.findViewById(R.id.email);
        tPh_Number.findViewById(R.id.ph_number);
        tPassword.findViewById(R.id.password);

        Intent intent=getIntent();
        String eName=intent.getStringExtra("name");
        String eAddress=intent.getStringExtra("address");
        String eEmail=intent.getStringExtra("email");
        String ePh_Number=intent.getStringExtra("ph_number");
        String ePassword=intent.getStringExtra("password");

        tName.setText(eName);
        tAddress.setText(eAddress);
        tEmail.setText(eEmail);
        tPh_Number.setText(ePh_Number);
        tPassword.setText(ePassword);

        tLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
