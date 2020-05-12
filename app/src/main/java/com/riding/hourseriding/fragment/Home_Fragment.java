package com.riding.hourseriding.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.riding.hourseriding.MainActivity;
import com.riding.hourseriding.R;
import com.riding.hourseriding.activity.AllNewsActivity;
import com.riding.hourseriding.activity.NewsDetailsActivity;
import com.riding.hourseriding.adapter.Discover_Adapter;
import com.riding.hourseriding.adapter.News_Adapter;
import com.riding.hourseriding.adapter.SliderAdapter;
import com.riding.hourseriding.databinding.FragmentHomeBinding;
import com.riding.hourseriding.model.SampleModel;
import com.riding.hourseriding.model.SliderModel;

import java.util.ArrayList;

/**
 * Created by Raghvendra Sahu on 09-May-20.
 */
public class Home_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentHomeBinding binding;
    SliderAdapter sliderAdapter;
    private int dotsCount;
    private ImageView[] dotes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        try {
            ((MainActivity) getActivity()).Update_header(getString(R.string.news));
            ((MainActivity) getActivity()).CheckBottom(0);
        } catch (Exception e) {
        }

//        // initialize a SliderLayout
//        binding.homeImgSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        binding.homeImgSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        binding.homeImgSlider.setCustomAnimation(new DescriptionAnimation());
//        binding.homeImgSlider.setDuration(4000);

        setSlider();
        getTopNews();

        binding.tvViewAllTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Top News");
                startActivity(intent);
            }
        });

        binding.tvHighlightNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Highlight");
                startActivity(intent);
            }
        });

        binding.tvLatesstNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllNewsActivity.class);
                intent.putExtra("NewsHeading","Latest News");
                startActivity(intent);
            }
        });


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



        return root;
    }

    private void setSlider() {
            // arraylist list variable for store data;
            ArrayList<SliderModel> listarray = new ArrayList<>();

            listarray.add(new SliderModel("Pretty registered Welsh part-bred mare",R.drawable.news1));
            listarray.add(new SliderModel("Loving, kind & sane",R.drawable.news2));
            listarray.add(new SliderModel("Pretty 14hh cob mare",R.drawable.news3));

//            for (int i=0; i<listarray.size(); i++) {
//                DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
//                // initialize a SliderLayout
//                textSliderView
//                        //.description(listarray.get(i).getName())
//                        .image(listarray.get(i).getImage())
//                        .setScaleType(BaseSliderView.ScaleType.Fit);
//
//                binding.homeImgSlider.addSlider(textSliderView);
//
//            }


            //*****************************************************
        sliderAdapter = new SliderAdapter(getActivity(),listarray);
        binding.sliderPager.setAdapter(sliderAdapter);
        binding.sliderPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderPager.setCurrentItem(0);
        binding.sliderPager.addOnPageChangeListener(pageChangeListener);
        dotesIndicater();

    }

    private void getTopNews() {

        ArrayList<SampleModel> sampleModels = new ArrayList<>();
        sampleModels.add(new SampleModel("Badminton’s backstage heroes: The people who make the horse trials happen", "08 may", R.drawable.news1));
        sampleModels.add(new SampleModel("Public urged not to let off fireworks during weekly ‘clap for carers’", "100", R.drawable.news2));
        sampleModels.add(new SampleModel("National dressage championships cancelled, but sport may resume from July", "50", R.drawable.news3));

        News_Adapter newsAdapter = new News_Adapter(sampleModels, getActivity());
        binding.setNewsAdapter(newsAdapter);//set databinding adapter
        newsAdapter.notifyDataSetChanged();

        Discover_Adapter discAdapter = new Discover_Adapter(sampleModels, getActivity());
        binding.setNewsDiscoverAdapter(discAdapter);//set databinding adapter
        discAdapter.notifyDataSetChanged();

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
            dotes[i] = new ImageView(getActivity());
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
    public void onRefresh() {

    }
}
