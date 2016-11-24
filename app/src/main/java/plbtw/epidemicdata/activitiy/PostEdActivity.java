package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.fragment.dialogs.FilterDialog;
import plbtw.epidemicdata.fragment.dialogs.LocationDialog;
import plbtw.epidemicdata.fragment.pickerview.ProvincePicker;
import plbtw.epidemicdata.model.DiseaseBody;
import plbtw.epidemicdata.model.DiseasePostResponse;
import plbtw.epidemicdata.model.MultimediaBody;
import plbtw.epidemicdata.model.MultimediaPostResponse;
import plbtw.epidemicdata.model.TipePenyakitModel;
import plbtw.epidemicdata.model.TipePenyakitResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by DedeEko on 6/1/2016.
 */
public class PostEdActivity extends BaseActivity {

    public static int REQUEST_CODE_POST_PENYAKIT = 1;
    private static int RESULT_CODE_PILIH_LOKASI = 1;
    AwesomeValidation mAwesomeValidation;

    private int SELECT_FILE = 1;

    private Toolbar toolbar;

    public MainActivity mainActivity;

    public String idPenyakit;
    public String idUser;

    private EditText namaPenyakit;
    private EditText jumlahPenderita;
    private EditText deskripsi;
    private EditText gejala;
    private EditText saran;

    private TextView tipePenyakit;
    private TextView Lokasi;

    private String image_name="";
    private String image_string = "";

    @Bind(R.id.spinnerTipePenyakit)
    Spinner spinnerTipePenyakit;

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

    @Bind(R.id.imagePenyakitBaru)
    ImageView imagePenyakitBaru;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private List<TipePenyakitModel> tipePenyakitModelList = new ArrayList<>();


    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;

    public String id_user;
    public String id_desa = "";
    public String nama_desa;

    private String id_tipe_penyakit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user", "");


        clearValidation();
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        addValidation(this);


        setRightIcon(0);
        setActionBarTitle("Post ed!");
        actionRight.setBackgroundColor(getResources().getColor(R.color.actionbar_dark_color));


    }

    private void clearValidation() {
        if (mAwesomeValidation != null) {
            mAwesomeValidation.clear();
        }
    }

    private void addValidation(final Activity activity){
//        mAwesomeValidation.addValidation(activity, R.id.signup_email,"[^$|^.*@.*\\..*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.form_item_name, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.form_item_number, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.form_item_desc, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.form_item_gejala, "[^\\s*$]", R.string.field_ksong);
        mAwesomeValidation.addValidation(activity, R.id.form_item_saran, "[^\\s*$]", R.string.field_ksong);

//        mAwesomeValidation.addValidation(activity, R.id.form_item_location, "[^\\s*$]", R.string.field_ksong);
  //      mAwesomeValidation.addValidation(activity, R.id.spinnerTipePenyakit, "[^\\s*$]", R.string.field_ksong);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        namaPenyakit = (EditText) findViewById(R.id.form_item_name);
        jumlahPenderita= (EditText) findViewById(R.id.form_item_number);
        deskripsi= (EditText) findViewById(R.id.form_item_desc);
        gejala= (EditText) findViewById(R.id.form_item_gejala);
        saran= (EditText) findViewById(R.id.form_item_saran);

//        tipePenyakit= (TextView) findViewById(R.id.form_item_type);
        Lokasi= (TextView) findViewById(R.id.form_item_location);
        Lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationDialog locationDialog = new LocationDialog(PostEdActivity.this, LocationDialog.REQUEST_POST);
                locationDialog.show(getSupportFragmentManager(), null);
            }
        });
        getTipePenyakitList();
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
                if (namaPenyakit.getText().toString().isEmpty() || jumlahPenderita.getText().toString().isEmpty() ||
                        deskripsi.getText().toString().isEmpty() || gejala.getText().toString().isEmpty() ||
                        saran.getText().toString().isEmpty() ) {
                    mAwesomeValidation.clear();
                    mAwesomeValidation.validate();
                } else if (tipePenyakitModelList.size() == 0) {
                    Toast.makeText(PostEdActivity.this, "Tipe penyakit belum diperoleh", Toast.LENGTH_SHORT).show();
                } else if (tipePenyakitModelList.get(spinnerTipePenyakit.getSelectedItemPosition()).getId_tipe_penyakit().equalsIgnoreCase("")) {
                    Toast.makeText(PostEdActivity.this, "Pilih tipe penyakit terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else if (id_desa.equalsIgnoreCase("")) {
                    Toast.makeText(PostEdActivity.this, "Pilih lokasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else if (image_name.equalsIgnoreCase("")) {
                    Toast.makeText(PostEdActivity.this, "Pilih gambar penyakit terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                else {
                    postPenyakit();
                }
                //  postMultimedia();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_post_ed;
    }

    @Override
    public void updateUI() {
        setLokasi(nama_desa);
    }

    public void postPenyakit()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<DiseasePostResponse> call = null;
        DiseaseBody diseaseBody = new DiseaseBody();
        diseaseBody.setId_user_app(id_user);
        diseaseBody.setNama_penyakit(namaPenyakit.getText().toString());
        diseaseBody.setId_tipe_penyakit(tipePenyakitModelList.get(spinnerTipePenyakit.getSelectedItemPosition()).getId_tipe_penyakit());
        diseaseBody.setId_desa(id_desa);
        diseaseBody.setJumlah_penderita(jumlahPenderita.getText().toString());
        diseaseBody.setDeskripsi(deskripsi.getText().toString());
        diseaseBody.setGejala(gejala.getText().toString());
        diseaseBody.setSaran_penanganan(saran.getText().toString());

        diseaseBody.setPhoto_name(image_name);
        diseaseBody.setPhoto_string(image_string);

        idPenyakit = diseaseBody.getId_penyakit();


        call = api.postDisease(diseaseBody);
        call.enqueue(new Callback<DiseasePostResponse>() {
            @Override
            public void onResponse(Response<DiseasePostResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (201 == response.code()) {

                    finish();

                } else {
                    Toast.makeText(PostEdActivity.this, "Error post penyakit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
                Toast.makeText(PostEdActivity.this, "Failed post penyakit", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void postMultimedia(){
    //    Toast.makeText(PostEdActivity.this, image_name, Toast.LENGTH_SHORT).show();
        if(image_name.equalsIgnoreCase(""))
        {
            Toast.makeText(PostEdActivity.this, "Pilih image terlebih dahulu",
                    Toast.LENGTH_LONG).show();
        }
        else {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait...");
            dialog.show();
            Call<MultimediaPostResponse> call = null;
            MultimediaBody multimediaBody = new MultimediaBody();
            multimediaBody.setId_penyakit(idPenyakit);
            multimediaBody.setId_user(id_user);
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
                        Toast.makeText(PostEdActivity.this, "Error to post image",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(PostEdActivity.this, "Error to post image",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_CODE_PILIH_LOKASI == resultCode && REQUEST_CODE_POST_PENYAKIT == requestCode) {


            Bundle bundle = data.getExtras();
            if (bundle != null) {
                id_desa = bundle.getString("id_desa");
                nama_desa = bundle.getString("nama_desa");
                Lokasi.setText(nama_desa);
                // Toast.makeText(EditPenyakitActivity.this, id_desa, Toast.LENGTH_SHORT).show();
            }

        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    private void getTipePenyakitList() {
        progressBar.setVisibility(View.VISIBLE);
        Call<TipePenyakitResponse> call = null;
        call = api.getTipePenyakitList("1", "1000");
        call.enqueue(new Callback<TipePenyakitResponse>() {
            @Override
            public void onResponse(Response<TipePenyakitResponse> response,
                                   Retrofit retrofit) {
                progressBar.setVisibility(View.GONE);
                if (2 == response.code() / 100) {
                    showTipePenyakitList(response);


                } else {
                    showErrorMessage();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "failed get tipe penyakit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
        //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

    private void showTipePenyakitList
            (Response<TipePenyakitResponse> response) {

        final TipePenyakitResponse tipePenyakitResponse = response.body();
        tipePenyakitModelList = tipePenyakitResponse.getListTipePenyakit();

        String[] array = new String[tipePenyakitModelList.size()];
        for (int i = 0; i < tipePenyakitModelList.size(); i++)
            array[i] = tipePenyakitModelList.get(i).getNama_tipe_penyakit();
        ArrayAdapter<String> adapterTipePenyakit = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, R.id.text1, array);
        adapterTipePenyakit.setDropDownViewResource(R.layout.spinner_item);
        spinnerTipePenyakit.setAdapter(adapterTipePenyakit);
    }

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

    public void setLokasi(String desa){
        Lokasi.setText(desa);
    }


}
