package sony.com.loginform;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText eNm, eAdd, eEmil, ePhn, ePass, econPass;
    String nam, addr, email, phNo, pass, conpass;

    Button bReg;
    TextView txtLog;

    AlertDialog.Builder builder;
    String reg_url = "https://unforced-behavior.000webhostapp.com/regis%20(copy).php";
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        bReg = (Button) findViewById(R.id.btnRegister);
        txtLog = (TextView) findViewById(R.id.already_user);


        eNm = (EditText) findViewById(R.id.fullName);
        eAdd = (EditText) findViewById(R.id.address);
        eEmil = (EditText) findViewById(R.id.userEmailId);
        ePhn = (EditText) findViewById(R.id.mobileNumber);
        ePass = (EditText) findViewById(R.id.password);
        econPass = (EditText) findViewById(R.id.confirmPassword);
        builder = new AlertDialog.Builder(Registration.this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.fullName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.address, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.addresserror);
        awesomeValidation.addValidation(this, R.id.mobileNumber, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.userEmailId, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$$", R.string.passworderror);

        txtLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );
        bReg.setOnClickListener(this);
    }


    private void submitForm() {

//        if (awesomeValidation.validate()) {
//            Toast.makeText(this, "Valid Information", Toast.LENGTH_LONG).show();

    }



    public void onClick(View view) {
        if (view == bReg) {
//            submitForm();

            nam = eNm.getText().toString();
            addr = eAdd.getText().toString();
            email = eEmil.getText().toString();
            phNo = ePhn.getText().toString();
            pass = ePass.getText().toString();
            conpass = econPass.getText().toString();
//
            if (nam.equals("") || addr.equals("") || email.equals("") || phNo.equals("") || pass.equals("") || conpass.equals("")) {
//            if (awesomeValidation.validate()) {
            builder.setTitle("Somthing went wrong...");
            builder.setMessage("Please fill all the fields...");

                displayAlert("input_error");


            } else {

                if (!(pass.equals(conpass))) {

//                    Toast.makeText(this, "Valid Information", Toast.LENGTH_LONG).show();

                    builder.setTitle("Somthing went Wrong....");
                    builder.setMessage("Your password are not matching...");
                    displayAlert("input_error");
//                    Toast.makeText(this, "Registration Complited...", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(this, "Registration Successful...", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                String message = jsonObject.getString("message");

                                builder.setTitle("Server Response...");
                                builder.setMessage(message);
                                displayAlert(code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<>();
                            params.put("name", nam);
                            params.put("address", addr);
                            params.put("email", email);
                            params.put("ph_number", phNo);
                            params.put("password", pass);

                            return params;
                        }
                    };

                    MySingleton.getmInstance(Registration.this).addReqQue(stringRequest);

                    //////
                }
                //////
            }

        }

    }
    public void displayAlert(final String code){

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (code.equals("input_error")){

                    ePass.setText("");
                    econPass.setText("");
                }

                else if (code.equals("reg_success")){

                    finish();
                }
                else if (code.equals("reg_faild")){
                    eNm.setText("");
                    eAdd.setText("");
                    eEmil.setText("");
                    ePhn.setText("");
                    ePass.setText("");
                    econPass.setText("");

                }
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
