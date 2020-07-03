package com.riding.hourseriding.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.LatestNews_Adapter;
import com.riding.hourseriding.api_call.Api_Call;
import com.riding.hourseriding.api_call.Base_Url;
import com.riding.hourseriding.api_call.RxApiClient;
import com.riding.hourseriding.databinding.FragmentSearchBinding;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Search_Fragment extends Fragment {
    FragmentSearchBinding binding;
    String search_text;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header(getString(R.string.search));
        } catch (Exception e) {
        }

        binding.searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                search_text= newText;
                return false;
            }
        });


        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_text.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter search field", Toast.LENGTH_SHORT).show();
                }else {

                    getSearchNews(search_text);
                }

            }
        });



        return root;
    }

    @SuppressLint("CheckResult")
    private void getSearchNews(String search_text) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetSearchNews(search_text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<NewsPostModel>>() {
                    @Override
                    public void onNext(final List<NewsPostModel> response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                          //  Log.e("latest_news", "" + response.get(0).getTitle().getRendered());

                            if (response!=null && !response.isEmpty()){
                                LatestNews_Adapter newsAdapter = new LatestNews_Adapter(response, getActivity(), "");
                                binding.setNewsAdapter(newsAdapter);//set databinding adapter
                                newsAdapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
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
