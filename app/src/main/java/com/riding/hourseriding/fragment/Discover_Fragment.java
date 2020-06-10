package com.riding.hourseriding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.Discover_Adapter;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.adapter.News_Adapter1;
import com.riding.hourseriding.databinding.FragmentDiscoverBinding;
import com.riding.hourseriding.databinding.FragmentHomeBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 11-May-20.
 */
public class Discover_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentDiscoverBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header("New Rider");
            ((MainActivity) getActivity()).CheckBottom(2);
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

        getTopNews();

        return root;


    }

    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "2020-05-12T11:24:52", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "2020-05-12T11:24:52", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "2020-05-12T11:24:52",R.drawable.news3));

        News_Adapter1 newsAdapter = new News_Adapter1(sampleModels, getActivity());
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();

    }




    @Override
    public void onRefresh() {

    }
}
