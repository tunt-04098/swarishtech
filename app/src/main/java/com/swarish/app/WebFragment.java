package com.swarish.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    private Toolbar toolbar;

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

        toolbar = view.findViewById(R.id.toolbar);
        webView = view.findViewById(R.id.webView);
        tvTitle = view.findViewById(R.id.tvTitle);

        tvTitle.setText(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // REMOTE RESOURCE
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (view.canGoBack()) {
                    toolbar.setNavigationIcon(R.drawable.ic_back);
                } else {
                    toolbar.setNavigationIcon(null);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (view.canGoBack()) {
                    toolbar.setNavigationIcon(R.drawable.ic_back);
                } else {
                    toolbar.setNavigationIcon(null);
                }
            }
        });

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