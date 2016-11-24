package plbtw.epidemicdata.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.activitiy.MainActivity;
import plbtw.epidemicdata.api.ApiUsers;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.fragment.BaseFragment;
import plbtw.epidemicdata.model.LoginBody;
import plbtw.epidemicdata.model.LoginResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ProfileFragment extends BaseFragment {


    private Toolbar toolbar;

    @Bind(R.id.form_email)
    TextView form_email;

    @Bind(R.id.form_username_user)
    TextView form_username_user;

    @Bind(R.id.form_nama_user)
    TextView form_nama_user;

    @Bind(R.id.form_telp)
    TextView form_telp;

    @Bind(R.id.form_poin)
    TextView form_poin;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;





    ApiUsers apiUsers;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = activity.getSharedPreferences(name, mode);
        apiUsers = ServiceGenerator.createService(ApiUsers.class);


    }



    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void setUICallbacks() {
    }


    @Override
    public void updateUI() {
        setupActionBar();
        fetchData();

    }
    private void setupActionBar() {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setLeftIcon(0);
    }

    @Override
    public String getPageTitle() {
        return null;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_profil;
    }

    public void fetchData(){

        form_username_user.setText(sharedPreferences.getString("username", ""));
        form_email.setText(sharedPreferences.getString("email",""));
        form_telp.setText(sharedPreferences.getString("telp",""));
        form_poin.setText(sharedPreferences.getString("poin",""));
        form_nama_user.setText(sharedPreferences.getString("nama", ""));



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

    private void refresh()
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");

        dialog.show();
        Call<LoginResponse> call = null;
        LoginBody loginBody = new LoginBody();

        call = apiUsers.getUser(sharedPreferences.getString("username", ""), sharedPreferences.getString("password", ""));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (200 == response.code()) {

                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getListLoginBody().size() != 0) {


                        saveSharedPreference(loginResponse);
                        fetchData();

                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Data user tidak ditemukan", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error get user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity.getApplicationContext(), "Failed get user", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void run() {
        setupActionBar();
        fetchData();
        refresh();
    }
}
