package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.asus.refreshbody.R;

public class FragmentNewspaper extends Fragment {

    private WebView webView;
    public FragmentNewspaper() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentview = inflater.inflate(R.layout.fragment_fragment_newspaper, container, false);
        // Inflate the layout for this fragment
        webView =(WebView) fragmentview.findViewById(R.id.idNewspaper);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.goclaptrinh.com");
        return fragmentview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
