package com.riding.hourseriding.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.LatestNews_Adapter;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.api_call.Api_Call;
import com.riding.hourseriding.api_call.Base_Url;
import com.riding.hourseriding.api_call.RxApiClient;
import com.riding.hourseriding.databinding.ActivityAllNewsBinding;
import com.riding.hourseriding.databinding.ActivityNewsDetailsBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class AllNewsActivity extends AppCompatActivity {
    ActivityAllNewsBinding binding;
    private String NewsHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all_news);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_news);

        if (getIntent()!=null){
            NewsHeading=getIntent().getStringExtra("NewsHeading");
        }
        binding.toolbar.tvToolbar.setText(NewsHeading);

        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (NewsHeading.equalsIgnoreCase("Latest News")){
            //getLatestAllNews();
        }else {
            getTopNews();
        }


    }


    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));

        News_Adapter newsAdapter = new News_Adapter(sampleModels, AllNewsActivity.this);
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();

    }
}
