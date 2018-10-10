package sony.com.loginform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText login_emailid, login_password;
    Button loginBtn;
    TextView RegText;

    ////
    String login_url = "https://unforced-behavior.000webhostapp.com/login.php";
//    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        loginBtn = (Button) findViewById(R.id.loginBtn);
        RegText = (TextView) findViewById(R.id.createAccount);
//
        login_emailid = (EditText) findViewById(R.id.login_emailid);
        login_password = (EditText) findViewById(R.id.login_password);

//        String login_url = "https://unforced-behavior.000webhostapp.com/login.php";

        RegText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(LoginActivity.this, Registration.class);
                        startActivity(i);
//                        finish();
                    }
                }
        );

        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mEmail=login_emailid.getText().toString().trim();
                        String mpasswrd=login_password.getText().toString().trim();

                        if (!mEmail.isEmpty() || !mpasswrd.isEmpty()){

                            Login(mEmail,mpasswrd);
                        }
                        else {

                            login_emailid.setError("Email");
                            login_password.setError("Password");
                        }
                    }
                }
        );
    }

    private void Login(final String email, final String password){

        StringRequest request=new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            String success=jsonObject.getString("success");
                            JSONArray jsonArray=jsonObject.getJSONArray("login");

                            if (success.equals("1")){

                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject object=jsonArray.getJSONObject(i);

                                    String name=object.getString("name").trim();
                                    String address=object.getString("address").trim();
                                    String email=object.getString("email").trim();
                                    String ph_number=object.getString("ph_number").trim();
                                    String password=object.getString("password").trim();

//                                    Toast.makeText(LoginActivity.this,
//                                            "Success Login. \nYour Name : "
//                                            +name+"\nYour Address : "
//                                            +address+ "\nYour Email : " +email+
//                                            "\nYour Phone Number : " +ph_number+
//                                            "\nYour Phone Password : " +password,
//                                            Toast.LENGTH_LONG)
//                                            .show();

                                    Intent intent=new Intent(LoginActivity.this,Welcome.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("address",address);
                                    intent.putExtra("email",email);
                                    intent.putExtra("ph_number",ph_number);
                                    intent.putExtra("password",password);

                                    startActivity(intent);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(LoginActivity.this,"Error "+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this,"Error "+error.toString(),Toast.LENGTH_LONG).show();

            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);
    }
}


//        loginBtn.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        getData();
//
//                    }
//                }
//        );
//    }
//
//    public void getData(){
//
//        email = login_emailid.getText().toString();
//        passwrd = login_password.getText().toString();
//
//        if (email.equals("") || passwrd.equals("")) {
//
//
//            Toast.makeText(LoginActivity.this, "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
//            }
//
//            else {
//
//
//            StringRequest request=new StringRequest(Request.Method.POST, login_url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
////                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
//
//                            if (response.equals("LoginActivity")) {
////
//                                startActivity(new Intent(getApplicationContext(), Welcome.class));
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//
//                }
//            }){
//
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("email", login_emailid.getText().toString());
//                    params.put("password", login_password.getText().toString());
//                    return params;
//                }
//            };
//
//            request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        queue.add(request);
//        }
//
//    }
//}



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
//                            Toast.makeText(LoginActivity.this, "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
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
//                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
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
//                                        if (response.equals("LoginActivity")) {
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





//        builder = new AlertDialog.Builder(LoginActivity.this);
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
//                                                    builder.setTitle("LoginActivity Error...");
//                                                    displayAlert(jsonObject.getString("message"));
//                                                } else {
//                                                    Intent intent = new Intent(LoginActivity.this, Welcome.class);
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
//                                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
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
//                            MySingleton.getmInstance(LoginActivity.this).addReqQue(stringRequest);
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