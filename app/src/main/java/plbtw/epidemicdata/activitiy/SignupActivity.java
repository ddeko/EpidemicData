package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiUsers;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.SignupBody;
import plbtw.epidemicdata.model.SignupPostResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signupEmail;
    EditText signupPassword;
    EditText signupConfirmPassword;
    EditText signupName;
    EditText signupPhone;
    EditText signupUsername;
    LinearLayout signupButton;

    ApiUsers apiUsers;

    AwesomeValidation mAwesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupConfirmPassword = (EditText) findViewById(R.id.signup_confirm_password);
        signupName = (EditText) findViewById(R.id.signup_name);
        signupUsername = (EditText) findViewById(R.id.signup_username);
        signupPhone = (EditText) findViewById(R.id.signup_phonenumber);
        signupButton = (LinearLayout) findViewById(R.id.signup_button);

        apiUsers = ServiceGenerator.createService(ApiUsers.class);

        clearValidation();
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        addValidation(this);

        signupButton.setOnClickListener(this);
        signupEmail.setOnClickListener(this);
        signupPassword.setOnClickListener(this);
        signupConfirmPassword.setOnClickListener(this);
        signupName.setOnClickListener(this);
        signupUsername.setOnClickListener(this);
        signupPhone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == signupButton)
        {
            if(signupConfirmPassword.getText().toString().isEmpty() ||  signupEmail.getText().toString().isEmpty() ||
                    signupName.getText().toString().isEmpty() || signupPassword.getText().toString().isEmpty() ||
                    signupPhone.getText().toString().isEmpty() || signupUsername.getText().toString().isEmpty()) {
                mAwesomeValidation.clear();
                mAwesomeValidation.validate();
            }
            else {
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Please wait...");
                dialog.show();
                Call<SignupPostResponse> call = null;
                SignupBody signupBody = new SignupBody();
                signupBody.setEmail(signupEmail.getText().toString());
                signupBody.setNama(signupName.getText().toString());
                signupBody.setPassword(signupPassword.getText().toString());
                signupBody.setPoin("0");
                signupBody.setRole("Member");
                signupBody.setUsername(signupUsername.getText().toString());
                signupBody.setTelp(signupPhone.getText().toString());


                call = apiUsers.postSignup(signupBody);
                call.enqueue(new Callback<SignupPostResponse>() {
                    @Override
                    public void onResponse(Response<SignupPostResponse> response, Retrofit retrofit) {
                        dialog.dismiss();
                        if (201 == response.code()) {

                            Toast.makeText(SignupActivity.this, "Sign Up Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignupActivity.this, "Kesalahan sign up", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Gagal sign up", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
        else if(v == signupConfirmPassword || v == signupEmail || v == signupName || v == signupPassword || v == signupPhone || v == signupUsername)
        {
            mAwesomeValidation.clear();
        }
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
//        mAwesomeValidation.addValidation(activity, R.id.signup_email,"[^$|^.*@.*\\..*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_name, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_password, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_confirm_password, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_username, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_phonenumber, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.signup_phonenumber, RegexTemplate.TELEPHONE, R.string.field_phone);
        mAwesomeValidation.addValidation(activity, R.id.signup_email, android.util.Patterns.EMAIL_ADDRESS, R.string.field_email);
    }
}
