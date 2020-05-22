package com.riding.hourseriding.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.databinding.FragmentNewsDetailsBinding;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;
import com.riding.hourseriding.utils.Connectivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class NewsDetailsFragment extends Fragment {
    FragmentNewsDetailsBinding binding;
    TextView title;
    WebView content;
    private String Newsid;
    ProgressDialog progressDialog;
    Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false);
        View root = binding.getRoot();
        content=binding.webviewContent;
        title=binding.title;

        try {
            ((MainActivity) getActivity()).Update_header(getString(R.string.news_details));
        } catch (Exception e) {
        }
        //onback press call
        View view = getActivity().findViewById(R.id.img_back);
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            //Do your stuff
            imageView.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).onBackPressed();
                }
            });
        }

        try {
           Newsid = getArguments().getString("id");
           // final String img_url = getIntent().getStringExtra("featured_media");
            //getIntent().getExtras().getString("id");
            String url = "https://www.equipro.org.uk/wp-json/wp/v2/posts/"+Newsid+"?fields=title,content";

            Log.e("url_detail",""+url);

            if (Connectivity.isConnected(getActivity())){

                GetNewsDetails(url);
                //  FindImage(img_url);
            }else {
                Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){

        }
//*****************************************
        WebSettings webSettings = content.getSettings();
        webSettings.setJavaScriptEnabled(true);

        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        content.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        content.getSettings().setMediaPlaybackRequiresUserGesture(false);
        content.getSettings().setSupportZoom(true);
        content.getSettings().setBuiltInZoomControls(true);


        if (Build.VERSION.SDK_INT >= 21) {
            content.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(content, true);
        }

        content.setBackgroundColor(Color.argb(1, 0, 0, 0));

        //**************webcrome client****************************
        content.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //progressBar.setProgress(progress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

        });

        //webview client
        content.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
                webview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView webview, String url) {

                webview.setVisibility(View.VISIBLE);
                super.onPageFinished(webview, url);

            }
        });
        content.setWebChromeClient(new WebChromeClient());
        content.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");


        return root;


    }

    private void GetNewsDetails(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);
                mapTitle = (Map<String, Object>) mapPost.get("title");
                mapContent = (Map<String, Object>) mapPost.get("content");

                title.setText(mapTitle.get("rendered").toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    title.setText(Html.fromHtml(mapTitle.get("rendered").toString()));
                } else {
                    title.setText(Html.fromHtml(mapTitle.get("rendered").toString()));
                }

                content.getSettings().setLoadWithOverviewMode(true);
                content.getSettings().setUseWideViewPort(true);
                content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

                WebSettings webSettings = content.getSettings();
                webSettings.setDefaultTextEncodingName("utf-8");
                //  content.loadData(mapContent.get("rendered").toString(), "text/html; charset=utf-8", null);
                try {
                    content.loadData(URLEncoder.encode(mapContent.get("rendered").toString(), "utf-8").replaceAll("\\+"," "), "text/html", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                // Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);

    }
}
