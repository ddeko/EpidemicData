package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiUsers;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.LoginBody;
import plbtw.epidemicdata.model.LoginResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginEmail;
    EditText loginPassword;
    LinearLayout loginButton;
    TextView signupButton;

    //init validator
    AwesomeValidation mAwesomeValidation;
    ApiUsers apiUsers;


    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(name, mode);


        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (LinearLayout) findViewById(R.id.login_button);
        signupButton = (TextView) findViewById(R.id.signUp_button);

        //validator
        clearValidation();
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        addValidation(this);

        apiUsers = ServiceGenerator.createService(ApiUsers.class);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        loginEmail.setOnClickListener(this);
        loginPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton)
        {
            if(loginEmail.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty())
            {
                mAwesomeValidation.validate();
            }
            else{
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Please wait...");
                dialog.show();
                Call<LoginResponse> call = null;
                LoginBody loginBody = new LoginBody();

                call = apiUsers.cekLogin(loginEmail.getText().toString(), loginPassword.getText().toString());
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                        dialog.dismiss();
                        if (200 == response.code()) {

                            LoginResponse loginResponse = response.body();

                            if (loginResponse.getListLoginBody().size() != 0) {

                                saveSharedPreference(loginResponse);
                                mAwesomeValidation.clear();
                                finish();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.username_dan_password_salah, Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
        else if(v == signupButton)
        {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
            mAwesomeValidation.clear();
        }
        else if(v == loginEmail)
        {
            mAwesomeValidation.clear();
        }
        else if(v == loginPassword)
        {
            mAwesomeValidation.clear();
        }
    }

    private void saveSharedPreference(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",loginResponse.getListLoginBody().get(0).getEmail());
        editor.putString("username",loginResponse.getListLoginBody().get(0).getUsername());
        editor.putString("id_user",loginResponse.getListLoginBody().get(0).getId_users());
        editor.putString("nama",loginResponse.getListLoginBody().get(0).getNama());
        editor.putString("password",loginResponse.getListLoginBody().get(0).getPassword());
        editor.putString("poin",loginResponse.getListLoginBody().get(0).getPoin());
        editor.putString("role",loginResponse.getListLoginBody().get(0).getRole());
        editor.putString("telp",loginResponse.getListLoginBody().get(0).getTelp());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void clearValidation() {
        if (mAwesomeValidation != null) {
            mAwesomeValidation.clear();
        }
    }

    private void addValidation(final Activity activity){
        mAwesomeValidation.addValidation(activity, R.id.login_email,"[^\\s*$]", R.string.username_kosong);
        mAwesomeValidation.addValidation(activity, R.id.login_password, "[^\\s*$]", R.string.password_kosong);
    }
}
