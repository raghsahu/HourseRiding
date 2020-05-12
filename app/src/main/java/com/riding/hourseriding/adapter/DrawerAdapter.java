package com.riding.hourseriding.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.AllNewsActivity;
import com.riding.hourseriding.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 09-May-20.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerItemViewHolder>{

    Context mContext;
    ArrayList<DrawerItem> menuList;

    public class DrawerItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemTitle;
        RelativeLayout rel_item;

        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            itemTitle=itemView.findViewById(R.id.menu_item_title);
            rel_item=itemView.findViewById(R.id.relative_item);
        }
    }//ViewHolder

    public DrawerAdapter(Context context, ArrayList<DrawerItem> itemList){

        mContext=context;
        menuList=itemList;
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item,parent,false);
        DrawerItemViewHolder dvh=new DrawerItemViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, int position) {

        final DrawerItem currentItem=menuList.get(position);

        holder.itemTitle.setText(currentItem.getTitle());
       // holder.itemTitle.setTextColor(Color.WHITE);
       // holder.itemTitle.setBackgroundColor(Color.BLACK);

        holder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, AllNewsActivity.class);
                intent.putExtra("NewsHeading",currentItem.getTitle());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
