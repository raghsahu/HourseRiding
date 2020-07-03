package com.riding.hourseriding.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.riding.hourseriding.BR;
import com.riding.hourseriding.R;
import com.riding.hourseriding.databinding.LatestNewsListBinding;
import com.riding.hourseriding.databinding.TodayNewsListBinding;
import com.riding.hourseriding.fragment.NewsDetailsFragment;
import com.riding.hourseriding.model.TodayNewsModel;
import com.riding.hourseriding.model.news_post_model.NewsPostModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayNews_Adapter extends RecyclerView.Adapter<TodayNews_Adapter.ViewHolder> {

    private List<NewsPostModel> dataModelList;
    Context context;

    public TodayNews_Adapter(List<NewsPostModel> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TodayNewsListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.today_news_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsPostModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
       // holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        SimpleDateFormat todayMatchingDate = new SimpleDateFormat("dd MMM, yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dataModel.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        String today_formatDate = todayMatchingDate.format(date);
        System.out.println(formattedDate);

        String current_date = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());
        if (today_formatDate.equalsIgnoreCase(current_date)){
            holder.itemRowBinding.tvTime.setText(formattedDate);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.itemRowBinding.tvTitle.setText(Html.fromHtml(dataModel.getExcerpt().getRendered(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.itemRowBinding.tvTitle.setText(Html.fromHtml(dataModel.getExcerpt().getRendered()));
            }

            try {
                // Log.e("img_size",""+dataModel.getBetterFeaturedImage());
                if (dataModel.getBetterFeaturedImage().getSourceUrl()!=null) {
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
        }else {
            holder.itemRowBinding.cardNews.setVisibility(View.GONE);
        }

        //***************************



        holder.itemRowBinding.cardNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postID=dataModel.getId().toString();
                NewsDetailsFragment fragment2 = new NewsDetailsFragment();
                Bundle bundle = new Bundle();
                // bundle.putSerializable("MyAddressEdit", dataModel);
                bundle.putString("id",postID);
                FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fragment2.setArguments(bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TodayNewsListBinding itemRowBinding;

        public ViewHolder(TodayNewsListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
