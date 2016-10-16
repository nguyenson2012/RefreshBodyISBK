package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.utils.Constant;

/**
 * Created by Asus on 10/16/2016.
 */

public class FragmentSetting extends Fragment {
    private ListView lvGeneral,lvOther;
    ArrayAdapter<String> arrayAdapterGeneral,arrayAdapterOther;

    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_setting,container,false);
        setupView(layout);
        return layout;

    }

    private void setupView(View layout) {
        lvGeneral=(ListView)layout.findViewById(R.id.lv_general);
        lvOther=(ListView)layout.findViewById(R.id.lv_Other);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences(Constant.MY_PREFERENCE, Context.MODE_PRIVATE);
        setAdapterForListView();
        registerEvent();
    }

    private void registerEvent() {
        lvOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        sendFeedback();
                        break;
                    case 1:
                        shareAction();
                        break;
                    case 2://Rate Us
                        rateUs();
                        break;

                }
            }
        });
        lvGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        openDialogChangeWeight();
                        break;
                }
            }
        });
    }

    private void openDialogChangeWeight() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout=inflater.inflate(R.layout.dialog_change_weight,null);
        dialogBuilder.setView(layout);
        final AlertDialog alertDialog = dialogBuilder.create();

        final EditText edittextWeight= (EditText) layout.findViewById(R.id.edittext_weight);
        TextView tvCancel=(TextView)layout.findViewById(R.id.tv_cancel_weight);
        TextView tvOk=(TextView)layout.findViewById(R.id.tv_ok_weight);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change Weight
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(Constant.WEIGHT,Integer.parseInt(edittextWeight.getText()+""));
                editor.commit();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void rateUs() {
        Toast.makeText(getActivity(),"Rate Us",Toast.LENGTH_SHORT).show();
    }

    private void sendFeedback() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "sonpfievk57@gmail.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }

    private void shareAction() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "Check it out. Your message goes here";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
    }

    private void setAdapterForListView() {
        arrayAdapterGeneral=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[]{"Weight"});
        lvGeneral.setAdapter(arrayAdapterGeneral);
        arrayAdapterOther=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[]{"Feedback","Share With Friend","Rate Us"});
        lvOther.setAdapter(arrayAdapterOther);

    }
}
