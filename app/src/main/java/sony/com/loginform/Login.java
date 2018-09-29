package sony.com.loginform;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Login extends AppCompatActivity {


    EditText login_emailid, login_password;
    Button bLogin;
    TextView RegText;
    //
////
    String email, passwrd;
    String login_url = "https://unforced-behavior.000webhostapp.com/login.php";
//    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bLogin = (Button) findViewById(R.id.loginBtn);
        RegText = (TextView) findViewById(R.id.createAccount);
//
        login_emailid = (EditText) findViewById(R.id.login_emailid);
        login_password = (EditText) findViewById(R.id.login_password);


        RegText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Login.this, Registration.class);
                        startActivity(i);
//                        finish();
                    }
                }
        );

        bLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getData();

                    }
                }
        );
    }

    public void getData(){

        email = login_emailid.getText().toString();
        passwrd = login_password.getText().toString();

        if (email.equals(" ") || passwrd.equals(" ")) {
            Toast.makeText(Login.this, "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
            }

            else {

            RequestQueue queue = Volley.newRequestQueue(Login.this);
            StringRequest request=new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();

                            if (response.equals("Login")) {
//
                                startActivity(new Intent(getApplicationContext(), Welcome.class));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", login_emailid.getText().toString());
                    params.put("password", login_password.getText().toString());
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        queue.add(request);
        }

    }
}



//        bLogin.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        email = login_emailid.getText().toString();
//                        passwrd = login_password.getText().toString();
//
//                        if (email.equals(" ") || passwrd.equals(" ")) {
//
//                            Toast.makeText(Login.this, "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                          );
//                }
//

//        bLogin.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RequestQueue queue = Volley.newRequestQueue(Login.this);
////                        String response = null;
////
////                        final String finalResponse = response;
//
//                        StringRequest postRequest = new StringRequest(Request.Method.POST, login_url,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//
//                                        if (response.equals("Login")) {
//
//                                            startActivity(new Intent(getApplicationContext(), Welcome.class));
//                                        }
//
//
//                                    }
//
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // error
//
////                                        Log.d("ErrorResponse", finalResponse);
//
//                                    }
//                                }
//                        ) {
//                            @Override
//                            protected Map<String, String> getParams() {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("email", login_emailid.getText().toString());
//                                params.put("password", login_password.getText().toString());
//                                return params;
//                            }
//                        };
//                        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        queue.add(postRequest);
//
//                    }
//                }
//        );
//
//
//    }
//}
//    private void loginRequest() {





//        builder = new AlertDialog.Builder(Login.this);
//
//        bLogin.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        email = login_emailid.getText().toString();
//                        passwrd = login_password.getText().toString();
//
//                        if (email.equals(" ") || passwrd.equals(" ")) {
//
//                            builder.setTitle("Somethink went Wrong");
//                            displayAlert("Enter a valid Email and Password");
//
//                        } else {
//                            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
//                                    new Response.Listener<String>() {
//                                        @Override
//                                        public void onResponse(String response) {
//
//                                            try {
//                                                JSONArray jsonArray = new JSONArray(response);
//
//                                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                                String code = jsonObject.getString("code");
//                                                if (code.equals("login_failed")) {
//
//                                                    builder.setTitle("Login Error...");
//                                                    displayAlert(jsonObject.getString("message"));
//                                                } else {
//                                                    Intent intent = new Intent(Login.this, Welcome.class);
//
//                                                    Bundle bundle = new Bundle();
//                                                    bundle.putString("name", jsonObject.getString("name"));
//                                                    bundle.putString("email", jsonObject.getString("email"));
//
//                                                    intent.putExtras(bundle);
//                                                    startActivity(intent);
//                                                }
//                                            } catch (JSONException e) {
//
//
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//
//                                    Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
//                                    error.printStackTrace();
//                                }
//                            }) {
//
//                                @Override
//                                protected Map<String, String> getParams() throws AuthFailureError {
//                                    Map<String, String> params = new HashMap<>();
//                                    params.put("uName", email);
//                                    params.put("uPass", passwrd);
//                                    return params;
//                                }
//                            };
//
//                            MySingleton.getmInstance(Login.this).addReqQue(stringRequest);
//                        }
//                    }
//                }
//        );
//
//    }
//
//    public void displayAlert(String message) {
//
//        builder.setMessage(message);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                login_emailid.setText("");
//                login_password.setText("");
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//
//    }
//}