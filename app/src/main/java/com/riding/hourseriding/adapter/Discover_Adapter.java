package com.riding.hourseriding.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.riding.hourseriding.BR;
import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.NewsDetailsActivity;
import com.riding.hourseriding.databinding.NewsDiscoverListBinding;
import com.riding.hourseriding.databinding.NewsListBinding;
import com.riding.hourseriding.model.SampleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 11-May-20.
 */
public class Discover_Adapter extends RecyclerView.Adapter<Discover_Adapter.ViewHolder> {

    private List<SampleModel> dataModelList;
    Context context;


    public Discover_Adapter(ArrayList<SampleModel> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsDiscoverListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.news_discover_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SampleModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, NewsDetailsActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public NewsDiscoverListBinding itemRowBinding;

        public ViewHolder(NewsDiscoverListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
