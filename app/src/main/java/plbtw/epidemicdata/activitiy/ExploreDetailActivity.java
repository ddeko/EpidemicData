package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.GridView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.adapter.DiseaseImageAdapter;
import plbtw.epidemicdata.api.API;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.model.DeletePenyakitResponse;
import plbtw.epidemicdata.model.DiseaseModel;
import plbtw.epidemicdata.model.MultimediaBody;
import plbtw.epidemicdata.model.MultimediaPostResponse;
import plbtw.epidemicdata.utils.FunctionUtil;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ExploreDetailActivity extends BaseActivity {

    private int SELECT_FILE = 1;
    private Toolbar toolbar;

    private TextView detailTxtUserName;
    private TextView detailTxtDiseaseName;
    private TextView detailTxtType;
    private TextView detailTxtNumber;
    private TextView detailTxtDescription;
    private TextView detailTxtSympton;
    private TextView detailTxtSuggest;

    private GridView pictureList;

    private TextView btnDelete;

    private RelativeLayout btnGetMap;

    private DiseaseImageAdapter adapter;

    private ArrayList<String> diseaseImageUrls;

    public DiseaseModel diseaseData;

    private API api;

    private String image_name="";
    private String image_string = "";
    private String id_user = "";

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;
    @OnClick(R.id.buttonPilihImage)
    void setOnClickPilihImageButton(View v) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);
    }

    @OnClick(R.id.btnSimpan)
    void setOnClickSimpanButton(View view) {

     //   Toast.makeText(ExploreDetailActivity.this, image_name, Toast.LENGTH_SHORT).show();
        if(image_name.equalsIgnoreCase(""))
        {
            Toast.makeText(ExploreDetailActivity.this, "Pilih image terlebih dahulu",
                    Toast.LENGTH_LONG).show();
        }
        else {
            final ProgressDialog dialog = new ProgressDialog(view.getContext());
            dialog.setMessage("Please wait...");
            dialog.show();
            Call<MultimediaPostResponse> call = null;
            MultimediaBody multimediaBody = new MultimediaBody();
            multimediaBody.setId_penyakit(diseaseData.getId_penyakit());
            multimediaBody.setId_user(sharedPreferences.getString("id_user", ""));
            multimediaBody.setTipe_multimedia("image");
            multimediaBody.setPhoto_name(image_name);
            multimediaBody.setPhoto_string(image_string);


            call = api.postMultimedia(multimediaBody);
            call.enqueue(new Callback<MultimediaPostResponse>() {
                @Override
                public void onResponse(Response<MultimediaPostResponse> response, Retrofit retrofit) {
                    dialog.dismiss();
                    if (2 == response.code() / 100) {
                        finish();
                    } else {
                        Toast.makeText(ExploreDetailActivity.this, "Error to post image",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ExploreDetailActivity.this, "Error to post image",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Bind(R.id.imagePenyakitBaru)
    ImageView imagePenyakitBaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        adapter = new DiseaseImageAdapter(this);
        diseaseImageUrls = new ArrayList<>();

        Intent i = getIntent();
        Bundle b = i.getExtras();


        diseaseData = (DiseaseModel) i.getSerializableExtra("disease");

        api = ServiceGenerator.createService(API.class);
        sharedPreferences = getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user","");

        fetchData();
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        detailTxtUserName = (TextView) findViewById(R.id.disease_detail_uploader_name);
        detailTxtDiseaseName = (TextView) findViewById(R.id.disease_detail_name);
        detailTxtType = (TextView) findViewById(R.id.disease_detail_type);
        detailTxtNumber = (TextView) findViewById(R.id.disease_detail_number);
        detailTxtDescription = (TextView) findViewById(R.id.disease_detail_desc);
        detailTxtSympton = (TextView) findViewById(R.id.disease_detail_gejala);
        detailTxtSuggest = (TextView) findViewById(R.id.disease_detail_saran);

        pictureList = (GridView) findViewById(R.id.disease_image_list);


        btnDelete = (TextView) findViewById(R.id.btn_delete);
        btnGetMap = (RelativeLayout) findViewById(R.id.get_map_btn);

        int deviceWidth = FunctionUtil.getDeviceSize(this).x;
        RelativeLayout.LayoutParams gridViewParams = (RelativeLayout.LayoutParams) pictureList.getLayoutParams();
        gridViewParams.width = (deviceWidth / 3) * 2;
        gridViewParams.height = deviceWidth;
        gridViewParams.topMargin = -(gridViewParams.width / 4);
        gridViewParams.bottomMargin = gridViewParams.topMargin;
        gridViewParams.leftMargin = -(gridViewParams.topMargin);
        pictureList.setLayoutParams(gridViewParams);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id_user.equalsIgnoreCase(diseaseData.getId_user_app().toString())) {
                    deletePenyakit(v);
                }
                else
                {
                    Toast.makeText(ExploreDetailActivity.this, "Anda tidak dapat menghapus penyakit yang bukan anda buat", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deletePenyakit(View v) {
        final ProgressDialog dialog = new ProgressDialog(v.getContext());
        dialog.setMessage("Please wait...");
        dialog.show();

        Call<DeletePenyakitResponse> call = null;
        call = api.deletePenyakit(diseaseData.getId_penyakit());
        call.enqueue(new Callback<DeletePenyakitResponse>() {
            @Override
            public void onResponse(Response<DeletePenyakitResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (2 == response.code() / 100) {
                    finish();
                } else {
                    Toast.makeText(ExploreDetailActivity.this, "Error delete penyakit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
                Toast.makeText(ExploreDetailActivity.this, "Failed delete penyakit", Toast.LENGTH_SHORT).show();
            }
        });
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
        return R.layout.activity_explore_detail;
    }

    @Override
    public void updateUI() {

        adapter.setData(diseaseImageUrls);
        pictureList.setAdapter(adapter);


    }

    public void fetchData() {

        detailTxtUserName.setText(diseaseData.getNama());
        detailTxtDiseaseName.setText(diseaseData.getNama_penyakit());
        detailTxtType.setText(diseaseData.getNama_tipe_penyakit());
        detailTxtNumber.setText(diseaseData.getJumlah_penderita());
        detailTxtDescription.setText(diseaseData.getDeskripsi());
        detailTxtSympton.setText(diseaseData.getGejala());
        detailTxtSuggest.setText(diseaseData.getSaran_penanganan());
        for (int i = 0; i < diseaseData.getMultimedia().size(); i++) {
            diseaseImageUrls.add(diseaseData.getMultimedia().get(i).getUrl());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        image_name = selectedImagePath.substring(selectedImagePath.lastIndexOf("/")+1);;

        imagePenyakitBaru.setImageBitmap(bm);
        Bitmap bitmap = ((BitmapDrawable)imagePenyakitBaru.getDrawable()).getBitmap();
        image_string = getEncoded64ImageStringFromBitmap(bitmap);


    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    public void setPhoto(String alamat) {
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(alamat, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(alamat, options);
        imagePenyakitBaru.setImageBitmap(bm);
    }
}
