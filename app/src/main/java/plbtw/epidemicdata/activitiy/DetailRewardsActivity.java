package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiRedeem;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.model.RedeemBody;
import plbtw.epidemicdata.model.RedeemPostResponse;
import plbtw.epidemicdata.model.RewardsModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailRewardsActivity extends BaseActivity {


    private Toolbar toolbar;

    @Bind(R.id.imageRewards)
    ImageView imageRewards;

    @Bind(R.id.textNamaRewards)
    TextView textNamaRewards;

    @Bind(R.id.textDeskripsiRewards)
    TextView textDeskripsiRewards;

    @Bind(R.id.textPoin)
    TextView textPoin;

    @Bind(R.id.textSisa)
    TextView textSisa;

    @Bind(R.id.buttonRedeem)
    Button buttonRedeem;


    @OnClick(R.id.buttonRedeem)
    void setOnClickRedeemButton(final View view) {

        if(Integer.parseInt(textSisa.getText().toString())<=0)
        {
            Toast.makeText(DetailRewardsActivity.this, "Reward tersebut sudah kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
            alertDialog.setMessage(Html.fromHtml("<strong>Pertanyan<br/><br/>Apakah Anda yakin?</strong>"));
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface d, int which) {

                    final ProgressDialog dialog = new ProgressDialog(view.getContext());
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    Call<RedeemPostResponse> call = null;
                    RedeemBody redeemBody = new RedeemBody();
                    redeemBody.setId_user_app(id_user);
                    redeemBody.setId_rewards(rewardsModel.getId_rewards());



                    call = apiRedeem.postRedeem(redeemBody);
                    call.enqueue(new Callback<RedeemPostResponse>() {
                        @Override
                        public void onResponse(Response<RedeemPostResponse> response, Retrofit retrofit) {
                            dialog.dismiss();
                            if (200 == response.code()) {

                                Intent intent = new Intent();
                                setResult(1, intent);
                                finish();

                            } else {
                                Toast.makeText(DetailRewardsActivity.this, "Error post redeem", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(DetailRewardsActivity.this, "Failed post redeem " + t.getMessage(), Toast.LENGTH_LONG).show();
                            textNamaRewards.setText(t.getMessage());
                            textDeskripsiRewards.setText(t.toString());
                            textPoin.setText(t.getLocalizedMessage());
                        }

                    });
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface d, int which) {

                }
            });
            alertDialog.show();
        }

    }


    RewardsModel rewardsModel;
    ApiRedeem apiRedeem;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;

    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);




        sharedPreferences = getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user","");

        apiRedeem = ServiceGenerator.createService(ApiRedeem.class);

        Bundle b = getIntent().getExtras();

        rewardsModel = (RewardsModel) b.getSerializable("rewardsModel");

        textNamaRewards.setText(rewardsModel.getNama_rewards());
        textDeskripsiRewards.setText(rewardsModel.getDeskripsi_rewards());
        textPoin.setText("Poin dibutuhkan  : " + rewardsModel.getPoin()+" Poin");
        textSisa.setText(rewardsModel.getSisa());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail_rewards;
    }

    @Override
    public void updateUI() {

    }
}
