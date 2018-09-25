package com.swarish.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by TuNT on 9/25/2018.
 * tunt.program.04098@gmail.com
 */
public class WebFragment extends Fragment implements BackView {

    private static final String KEY_URL = "KEY_URL";

    private static final String KEY_TITLE = "KEY_TITLE";

    public static WebFragment getInstance(String url, String title) {
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        args.putString(KEY_TITLE, title);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String url;

    private String title;

    private TextView tvTitle;

    private WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(KEY_URL);
            title = getArguments().getString(KEY_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        webView = view.findViewById(R.id.webView);
        tvTitle = view.findViewById(R.id.tvTitle);

        tvTitle.setText(title);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // REMOTE RESOURCE
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());

        return view;
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return false;
        }
        return true;
    }
}