package com.riding.hourseriding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.riding.hourseriding.R;
import com.riding.hourseriding.model.SliderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Raghvendra Sahu on 11-May-20.
 */
public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<SliderModel> listarray;

    public SliderAdapter(Context context, ArrayList<SliderModel> listarray1) {
        this.context = context;
        this.listarray = listarray1;
    }

//    public int[] sliderImage = {
//            R.drawable.mic_icon,
//            R.drawable.mymik,
//            R.drawable.mic_icon
//    };

    @Override
    public int getCount() {
        return listarray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_text, container, false);

        ImageView imageView = view.findViewById(R.id.circle_imag);
        TextView tv_test = view.findViewById(R.id.tv_test);
        TextView tv_time = view.findViewById(R.id.tv_date);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        Date date = null;
        try {
            date = inputFormat.parse(listarray.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        System.out.println(formattedDate); // prints 10-04-2018

        tv_time.setText(formattedDate);

        tv_test.setText(listarray.get(position).getName());
        //imageView.setImageResource(sliderImage[position]);
        Glide
                .with(context)
                .load(listarray.get(position).getUrl())
                .centerCrop()
                // .placeholder(R.drawable.loading_spinner)
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
