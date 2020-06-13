package com.riding.hourseriding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.databinding.FragmentGalleryBinding;
import com.riding.hourseriding.databinding.FragmentHomeBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.utils.Connectivity;

import java.util.ArrayList;

/**
 * Created by Raghvendra Sahu on 11-May-20.
 */
public class Gallery_Fragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentGalleryBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header("Tips");
            ((MainActivity) getActivity()).CheckBottom(1);
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


        if (Connectivity.isConnected(getActivity())){
            getTopNews();
        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        return root;


    }

    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));

        News_Adapter newsAdapter = new News_Adapter(sampleModels, getActivity());
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();

    }


    @Override
    public void onRefresh() {

    }
}
