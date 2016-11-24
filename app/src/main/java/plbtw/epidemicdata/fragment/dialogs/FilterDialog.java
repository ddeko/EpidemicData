package plbtw.epidemicdata.fragment.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import plbtw.epidemicdata.R;
import plbtw.epidemicdata.fragment.ExploreFragment;
import plbtw.epidemicdata.fragment.pickerview.DesaPicker;
import plbtw.epidemicdata.fragment.pickerview.KabupatenPicker;
import plbtw.epidemicdata.fragment.pickerview.KecamatanPicker;
import plbtw.epidemicdata.fragment.pickerview.ProvincePicker;


/**
 * Created by DedeEko on 4/17/2016.
 */
@SuppressLint("ValidFragment")
public class FilterDialog extends DialogFragment implements View.OnClickListener{

    public AVLoadingIndicatorView loading;

    protected Dialog dialog;

    private RelativeLayout filterdialogcategory;

    private RelativeLayout containerProvinsi;
    private RelativeLayout containerKabupaten;
    private RelativeLayout containerKecamatan;
    private RelativeLayout containerDesa;
    private TextView provinsiTxt;
    private TextView kabupatenTxt;
    private TextView kecamatanTxt;
    private TextView desaTxt;
    private TextView doneBtn;

    private ExploreFragment exploreFragment;

    private ProvincePicker provincePicker;
    private KabupatenPicker kabupatenPicker;
    private KecamatanPicker kecamatanPicker;
    private DesaPicker desaPicker;

    private String selectedState;
    private String selectedKab;
    private String selectedKec;
    private String selectedDesa;

    private String idProvinsi;
    private String idKabupaten;
    private String idKecamatan;
    private String idDesa;

    @SuppressLint("ValidFragment")
    public FilterDialog(ExploreFragment Fragment)
    {
        this.exploreFragment = Fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(),R.style.MaterialDialogSheet);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_filter_window);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();

        selectedState = "";
        selectedKab= "";
        selectedKec="";
        selectedDesa="";

        dialog.show();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exploreFragment.tampilListPenyakitByLokasi(idDesa, selectedDesa);


                dialog.dismiss();
            }
        });

        provincePicker = new ProvincePicker(getContext()) {
            @Override
            protected void onPickDone(String province, String id  ) {
                selectedState = province;
                idProvinsi = id;

                provinsiTxt.setText(selectedState);
                toggleEnable(containerKabupaten, true);
                validate();
            }

            @Override
            protected String lastSelectedProvince() {
                return selectedState;
            }
        };


        validate();

        return dialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;
                    else {
                        dialog.dismiss();
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }


    private void initView() {
        provinsiTxt = (TextView)dialog.findViewById(R.id.form_item_provinsi);
        kabupatenTxt = (TextView)dialog.findViewById(R.id.form_item_kabupaten);
        kecamatanTxt = (TextView)dialog.findViewById(R.id.form_item_kecamatan);
        desaTxt = (TextView)dialog.findViewById(R.id.form_item_desa);
        doneBtn = (TextView)dialog.findViewById(R.id.view_filter_done);
        loading = (AVLoadingIndicatorView) dialog.findViewById(R.id.avloadingIndicatorView);
        containerProvinsi = (RelativeLayout)dialog.findViewById(R.id.container_provinsi);
        containerKabupaten = (RelativeLayout)dialog.findViewById(R.id.container_kabupaten);
        containerKecamatan = (RelativeLayout)dialog.findViewById(R.id.container_kecamatan);
        containerDesa = (RelativeLayout)dialog.findViewById(R.id.container_desa);

        containerProvinsi.setOnClickListener(this);
        containerKabupaten.setOnClickListener(this);
        containerKecamatan.setOnClickListener(this);
        containerDesa.setOnClickListener(this);

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    void startAnim(){
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(v == containerProvinsi)
        {
            provincePicker.show(provinsiTxt);
        }
        else if(v ==containerKabupaten)
        {
            kabupatenPicker = new KabupatenPicker(getContext(), idProvinsi) {
                @Override
                protected void onPickDone(String kabupaten, String id) {
                    selectedKab = kabupaten;
                    idKabupaten = id;

                    kabupatenTxt.setText(selectedKab);
                    toggleEnable(containerKecamatan, true);
                    validate();
                }

                @Override
                protected String lastSelectedProvince() {
                    return selectedKab;
                }
            };
            kabupatenPicker.show(kabupatenTxt);

        }
        else if(v ==containerKecamatan)
        {
            kecamatanPicker = new KecamatanPicker(getContext(), idKabupaten) {
                @Override
                protected void onPickDone(String kecamatan, String id) {
                    selectedKec = kecamatan;
                    idKecamatan = id;

                    kecamatanTxt.setText(selectedKec);
                    toggleEnable(containerDesa, true);
                    validate();
                }

                @Override
                protected String lastSelectedProvince() {
                    return selectedKec;
                }
            };
            kecamatanPicker.show(kecamatanTxt);
        }
        else if(v ==containerDesa)
        {
            desaPicker = new DesaPicker(getContext(), idKecamatan) {
                @Override
                protected void onPickDone(String desa, String id) {
                    selectedDesa = desa;
                    idDesa = id;

                    desaTxt.setText(selectedDesa);
                    validate();
                }

                @Override
                protected String lastSelectedProvince() {
                    return null;
                }
            };
            desaPicker.show(desaTxt);
        }
    }

    public void validate(){
        if(provinsiTxt.getText().toString().isEmpty()||provinsiTxt.getText().toString().equalsIgnoreCase(""))
        {
            toggleEnable(containerKabupaten, false);
            toggleEnable(containerKecamatan, false);
            toggleEnable(containerDesa, false);
        }else if(kabupatenTxt.getText().toString().isEmpty())
        {
            toggleEnable(containerKecamatan, false);
            toggleEnable(containerDesa, false);
        }else if(kecamatanTxt.getText().toString().isEmpty())
        {
            toggleEnable(containerDesa, false);
        }

    }

    private void toggleEnable(View view, boolean enable) {
        view.setEnabled(enable);
        view.setBackgroundResource(enable ? R.drawable.shape_form_control : R.drawable.shape_form_control_has_error);
    }

}
