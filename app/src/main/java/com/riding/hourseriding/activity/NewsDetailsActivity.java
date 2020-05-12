package com.riding.hourseriding.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.riding.hourseriding.R;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.adapter.SliderAdapter;
import com.riding.hourseriding.databinding.ActivityNewsDetailsBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.SliderModel;

import java.util.ArrayList;

public class NewsDetailsActivity extends AppCompatActivity {
    ActivityNewsDetailsBinding binding;
    SliderAdapter sliderAdapter;
    private int dotsCount;
    private ImageView[] dotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_news_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);

        binding.toolbar.tvToolbar.setText("News Details");

        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getTopNews();

        // Images left navigation
        binding.ivLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = binding.sliderPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    binding.sliderPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    binding.sliderPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        binding.ivRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = binding.sliderPager.getCurrentItem();
                tab++;
                binding.sliderPager.setCurrentItem(tab);
            }
        });
    }



    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));

        News_Adapter newsAdapter = new News_Adapter(sampleModels, NewsDetailsActivity.this);
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();


        ArrayList<SliderModel> listarray = new ArrayList<>();

        listarray.add(new SliderModel("Pretty registered Welsh part-bred mare",R.drawable.news1));
        listarray.add(new SliderModel("Loving, kind & sane",R.drawable.news2));
        listarray.add(new SliderModel("Pretty 14hh cob mare",R.drawable.news3));

        sliderAdapter = new SliderAdapter(NewsDetailsActivity.this,listarray);
        binding.sliderPager.setAdapter(sliderAdapter);
        binding.sliderPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderPager.setCurrentItem(0);
        binding.sliderPager.addOnPageChangeListener(pageChangeListener);
        dotesIndicater();

    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //dotesIndicater(position);

            //for (int i = 0; i < dotes.length; i++) {
            /*for (ImageView dote : dotsCount) {
                dotes[position].setImageResource(R.drawable.circle_inactive);
            }*/

            for (int i = 0; i < dotsCount; i++) {
                dotes[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_inactive));
            }

            dotes[position].setImageResource(R.drawable.circle_active);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public void dotesIndicater() {
        dotsCount = sliderAdapter.getCount();
        dotes = new ImageView[dotsCount];
        binding.linearLayout.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dotes[i] = new ImageView(NewsDetailsActivity.this);
            dotes[i].setImageResource(R.drawable.circle_inactive);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    20
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dotes[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    binding.sliderPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            binding.linearLayout.addView(dotes[i], params);

            //linearLayout.addView(dotes[i]);
            //linearLayout.bringToFront();
        }
        dotes[0].setImageResource(R.drawable.circle_active);
        /*if (dotes.length > 0) {
            dotes[i].setImageResource(R.drawable.circle_active);
        }*/
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
