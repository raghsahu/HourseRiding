package com.riding.hourseriding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.databinding.FragmentSearchBinding;

public class Search_Fragment extends Fragment {
    FragmentSearchBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header(getString(R.string.search));
        } catch (Exception e) {
        }




        return root;
    }
}
