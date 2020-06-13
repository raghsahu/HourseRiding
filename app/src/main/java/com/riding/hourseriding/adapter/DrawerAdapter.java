package com.riding.hourseriding.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.AllNewsActivity;
import com.riding.hourseriding.fragment.ViewAllNewsFragment;
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

                ViewAllNewsFragment fragment2 = new ViewAllNewsFragment();
                Bundle bundle = new Bundle();
                // bundle.putSerializable("MyAddressEdit", dataModel);
                bundle.putString("NewsHeading",currentItem.getTitle());
                bundle.putString("NewsId",currentItem.getNewsId());
                FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
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
        return menuList.size();
    }
}
