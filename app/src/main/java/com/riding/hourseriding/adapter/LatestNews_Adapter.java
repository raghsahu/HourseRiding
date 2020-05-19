package com.riding.hourseriding.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.riding.hourseriding.BR;
import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.LatestNewsDetailsActivity;
import com.riding.hourseriding.activity.NewsDetailsActivity;
import com.riding.hourseriding.databinding.LatestNewsListBinding;
import com.riding.hourseriding.databinding.NewsListBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LatestNews_Adapter extends RecyclerView.Adapter<LatestNews_Adapter.ViewHolder> {

    private List<NewsPostModel> dataModelList;
    Context context;


    public LatestNews_Adapter(List<NewsPostModel> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LatestNewsListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.latest_news_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsPostModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dataModel.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        System.out.println(formattedDate); // prints 10-04-2018

        holder.itemRowBinding.tvTime.setText(formattedDate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.itemRowBinding.tvTitle.setText(Html.fromHtml(dataModel.getExcerpt().getRendered(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.itemRowBinding.tvTitle.setText(Html.fromHtml(dataModel.getExcerpt().getRendered()));
        }

        try {
           // Log.e("img_size",""+dataModel.getBetterFeaturedImage());
            if (dataModel.getBetterFeaturedImage()!=null) {
                Glide.with(context)
                        .load(dataModel.getBetterFeaturedImage().getSourceUrl())
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .error(R.drawable.news_logo)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(holder.itemRowBinding.newsImg);


            }

        }catch (Exception e){

        }

        holder.itemRowBinding.cardNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postID=dataModel.getId().toString();
                Intent intent=new Intent(context, LatestNewsDetailsActivity.class);
                intent.putExtra("id", ""+postID);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LatestNewsListBinding itemRowBinding;

        public ViewHolder(LatestNewsListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
