package com.riding.hourseriding.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.LatestNews_Adapter;
import com.riding.hourseriding.adapter.SliderAdapter;
import com.riding.hourseriding.api_call.Api_Call;
import com.riding.hourseriding.api_call.Base_Url;
import com.riding.hourseriding.api_call.RxApiClient;
import com.riding.hourseriding.databinding.FragmentViewAllBinding;
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

public class ViewAllNewsFragment extends Fragment {
    FragmentViewAllBinding binding;
    private String NewsHeading,NewsId;
    LatestNews_Adapter newsAdapter;
    List<NewsPostModel>newsPostModels=new ArrayList<>();
    private Integer Page_nmbr=1;
    boolean next=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_all, container, false);
        View root = binding.getRoot();

        if (getArguments()!=null){
            NewsHeading=getArguments().getString("NewsHeading");
            NewsId=getArguments().getString("NewsId");
        }

        try {
            ((MainActivity) getActivity()).Update_header(NewsHeading);
            ((MainActivity) getActivity()).CloseDrawer();
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


        if (NewsHeading.equalsIgnoreCase("Top News")){
            GetTodayNews();
        }else {
            getLatestAllNews(String.valueOf(Page_nmbr),NewsId);
        }
//************************************************************

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        next=true;
                        if (Connectivity.isConnected(getActivity())){
                            if (NewsHeading.equalsIgnoreCase("Top News")){
                                GetTodayNews();
                            }else {
                                getLatestAllNews(String.valueOf(Page_nmbr),NewsId);
                            }
                        }else {
                            Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
                        }

            }
        });

        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next=false;
                    if (Page_nmbr>1){
                        if (Connectivity.isConnected(getActivity())){
                            if (NewsHeading.equalsIgnoreCase("Top News")){
                                GetTodayNews();
                            }else {
                                getLatestAllNews(String.valueOf(Page_nmbr),NewsId);
                            }
                        }else {
                            Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getActivity(), "Previous page not found", Toast.LENGTH_SHORT).show();
                    }
            }
        });


        return root;

    }

    @SuppressLint("CheckResult")
    private void GetTodayNews() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetTodayNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<NewsPostModel>>() {
                    @Override
                    public void onNext(List<NewsPostModel> response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_news", "" + response.get(0).getTitle().getRendered());
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
                                binding.setNewsAdapter(todaynewsAdapter);//set databinding adapter
                                todaynewsAdapter.notifyDataSetChanged();
                            }else {
                            }

                            binding.nextPrevButton.setVisibility(View.VISIBLE);

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

    @SuppressLint("CheckResult")
    private void getLatestAllNews(String page_nmbr, String category) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        //category fix 37= latest news
        apiInterface.GetLatestPostNews(page_nmbr,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<NewsPostModel>>() {
                    @Override
                    public void onNext(final List<NewsPostModel> response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("latest_news", "" + response.get(0).getTitle().getRendered());

                            if (next){
                                Page_nmbr++;
                            }else {
                                Page_nmbr--;
                            }
                            newsAdapter = new LatestNews_Adapter(response, getActivity());
                            binding.setNewsAdapter(newsAdapter);//set databinding adapter
                            newsAdapter.notifyDataSetChanged();

                            binding.nextPrevButton.setVisibility(View.VISIBLE);

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
                                case 400:
                                    Toast.makeText(getActivity(), "Next page not found", Toast.LENGTH_SHORT).show();
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

}
