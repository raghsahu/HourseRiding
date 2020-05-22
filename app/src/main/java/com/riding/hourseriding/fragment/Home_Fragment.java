package com.riding.hourseriding.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.AllNewsActivity;
import com.riding.hourseriding.activity.NewsDetailsActivity;
import com.riding.hourseriding.adapter.Discover_Adapter;
import com.riding.hourseriding.adapter.LatestNews_Adapter;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.adapter.SliderAdapter;
import com.riding.hourseriding.api_call.Api_Call;
import com.riding.hourseriding.api_call.Base_Url;
import com.riding.hourseriding.api_call.RxApiClient;
import com.riding.hourseriding.databinding.FragmentHomeBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.SliderModel;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;
import com.riding.hourseriding.utils.Connectivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by Raghvendra Sahu on 09-May-20.
 */
public class Home_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentHomeBinding binding;
    SliderAdapter sliderAdapter;
    private int dotsCount;
    private ImageView[] dotes;
    List<NewsPostModel>newsPostModels=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header(getString(R.string.news));
            ((MainActivity) getActivity()).CheckBottom(0);
        } catch (Exception e) {
        }


        setSlider();
        getTopNews();
        if (Connectivity.isConnected(getActivity())){
            GetLatestNews();
        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.tvViewAllTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Top News");
                startActivity(intent);
            }
        });

        binding.tvHighlightNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Highlight");
                startActivity(intent);
            }
        });

        binding.tvLatesstNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Latest News");
                startActivity(intent);
            }
        });


        // Images left navigation
        binding.ivLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = binding.sliderPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    binding.sliderPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    binding.sliderPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        binding.ivRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = binding.sliderPager.getCurrentItem();
                tab++;
                binding.sliderPager.setCurrentItem(tab);
            }
        });



        return root;
    }

    @SuppressLint("CheckResult")
    private void GetLatestNews() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetLatestPostNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<NewsPostModel>>() {
                    @Override
                    public void onNext(List<NewsPostModel> response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                           Log.e("result_news", "" + response.get(0).getTitle().getRendered());

                            LatestNews_Adapter newsAdapter = new LatestNews_Adapter(response, getActivity());
                            binding.setLatestnewsAdapter(newsAdapter);//set databinding adapter
                            newsAdapter.notifyDataSetChanged();

                            //set item in today news
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            for (int i=0; i<response.size(); i++){
                                try {
                                    date = inputFormat.parse(response.get(i).getDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String formattedDate = outputFormat.format(date);
                                System.out.println(formattedDate); // prints 10-04-2018

                                String current_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                if (formattedDate.equalsIgnoreCase(current_date)){
                                    newsPostModels.add(new NewsPostModel(response.get(i)));
                                }

                            }
                            Log.e("today_data", "" + newsPostModels.size());
                            if (newsPostModels!=null && newsPostModels.size()>0){
                                LatestNews_Adapter todaynewsAdapter = new LatestNews_Adapter(newsPostModels, getActivity());
                                binding.setTodaynewsAdapter(todaynewsAdapter);//set databinding adapter
                                todaynewsAdapter.notifyDataSetChanged();
                            }else {
                                binding.tvViewAllTop.setVisibility(View.GONE);
                                binding.tvTodayEmpty.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    private void setSlider() {
            // arraylist list variable for store data;
            ArrayList<SliderModel> listarray = new ArrayList<>();

            listarray.add(new SliderModel("Pretty registered Welsh part-bred mare",R.drawable.news1));
            listarray.add(new SliderModel("Loving, kind & sane",R.drawable.news2));
            listarray.add(new SliderModel("Pretty 14hh cob mare",R.drawable.news3));

            //*****************************************************
        sliderAdapter = new SliderAdapter(getActivity(),listarray);
        binding.sliderPager.setAdapter(sliderAdapter);
        binding.sliderPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderPager.setCurrentItem(0);
        binding.sliderPager.addOnPageChangeListener(pageChangeListener);
        dotesIndicater();

    }

    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));

        News_Adapter newsAdapter = new News_Adapter(sampleModels, getActivity());
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();

        Discover_Adapter discAdapter = new Discover_Adapter(sampleModels, getActivity());
        binding.setNewsDiscoverAdapter(discAdapter);//set databinding adapter
        discAdapter.notifyDataSetChanged();

    }
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < dotsCount; i++) {
                dotes[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_inactive));
            }

            dotes[position].setImageResource(R.drawable.circle_active);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public void dotesIndicater() {
        dotsCount = sliderAdapter.getCount();
        dotes = new ImageView[dotsCount];
        binding.linearLayout.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dotes[i] = new ImageView(getActivity());
            dotes[i].setImageResource(R.drawable.circle_inactive);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    20
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dotes[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    binding.sliderPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            binding.linearLayout.addView(dotes[i], params);

        }
        dotes[0].setImageResource(R.drawable.circle_active);

    }

    @Override
    public void onRefresh() {

    }
}
