package com.riding.hourseriding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.riding.hourseriding.adapter.CustomExpandableListAdapter;
import com.riding.hourseriding.adapter.DrawerAdapter;
import com.riding.hourseriding.databinding.ActivityMainBinding;
import com.riding.hourseriding.fragment.Discover_Fragment;
import com.riding.hourseriding.fragment.Gallery_Fragment;
import com.riding.hourseriding.fragment.Home_Fragment;
import com.riding.hourseriding.fragment.Search_Fragment;
import com.riding.hourseriding.model.CategoryChild;
import com.riding.hourseriding.model.DrawerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView tv_main_header;
    ImageView iv_drawer;
    public BottomNavigationView navView;

    String[] items;

   // private ExpandableListView mExpandableListView;
//    private ExpandableListAdapter mExpandableListAdapter;
//    List<String> mExpandableListTitle;
//    private NavigationManager mNavigationManager;
//    // Map<String, List<String>> mExpandableListData;
//    Map<String, List<CategoryChild>> mExpandableListData;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment_home = new Home_Fragment();
                    FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.frame, fragment_home, "HOME_FRAGMENT");
                    ft_home.addToBackStack(null);
                    ft_home.commit();

                    return true;
                case R.id.navigation_video:
                    Fragment fragment_faq = new Gallery_Fragment();
                    FragmentTransaction ft_faq = getSupportFragmentManager().beginTransaction();
                    ft_faq.replace(R.id.frame, fragment_faq);
                    ft_faq.addToBackStack(null);
                    ft_faq.commit();

                    return true;
                case R.id.navigation_discover:
                    Fragment fragment_create = new Discover_Fragment();
                    FragmentTransaction ft_create = getSupportFragmentManager().beginTransaction();
                    ft_create.replace(R.id.frame, fragment_create);
                    ft_create.addToBackStack(null);
                    ft_create.commit();

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        tv_main_header = findViewById(R.id.tv_main_header);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        iv_drawer = findViewById(R.id.iv_drawer);
        iv_drawer.setOnClickListener(this);
        navView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        setHomeFragment();

//        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
//        mNavigationManager = FragmentNavigationManager.obtain(this);

      //  getNewsCategory();

        addDrawerItems();
        setupDrawer();

        binding.appBar.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment_create = new Search_Fragment();
                FragmentTransaction ft_create = getSupportFragmentManager().beginTransaction();
                ft_create.replace(R.id.frame, fragment_create);
                ft_create.addToBackStack(null);
                ft_create.commit();
            }
        });


    }

//    private void getNewsCategory() {
//        mExpandableListTitle = new ArrayList<String>();
//        mExpandableListData = new HashMap<String, List<CategoryChild>>();
//
//        mExpandableListTitle.add("All News");
//      //  mExpandableListTitle.add("Industry News");
//        mExpandableListTitle.add("Popular News");
//       // mExpandableListTitle.add("Sports News");
//       // mExpandableListTitle.add("Events News");
//        mExpandableListTitle.add("Equestrian News");
//
//        List<CategoryChild> colors = new ArrayList<CategoryChild>();
//        colors.add(new CategoryChild("Gallery"));
//        colors.add(new CategoryChild("Events"));
//        colors.add(new CategoryChild("Discover"));
//        colors.add(new CategoryChild("Market"));
//
//        mExpandableListData.put(mExpandableListTitle.get(0), colors);
//        mExpandableListData.put(mExpandableListTitle.get(1), colors);
//        mExpandableListData.put(mExpandableListTitle.get(2), colors);
//      //  mExpandableListData.put(mExpandableListTitle.get(3), colors);
//       // mExpandableListData.put(mExpandableListTitle.get(4), colors);
//      //  mExpandableListData.put(mExpandableListTitle.get(5), colors);
//
//        mExpandableListAdapter = new CustomExpandableListAdapter(MainActivity.this, mExpandableListTitle, mExpandableListData);
//        mExpandableListView.setAdapter(mExpandableListAdapter);
//    }

    private void addDrawerItems() {

        ArrayList<DrawerItem>drawerItemList=new ArrayList<>();
        drawerItemList.add(new DrawerItem("Horse Care", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Vet Library", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Feeding Horses", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Training Tips", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("New Riders & Owners", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Horse Breeding", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Buying & Selling Advice", R.drawable.news_logo));

        drawerItemList.add(new DrawerItem("Expert Opinion", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Have Your Say", R.drawable.news_logo));
        drawerItemList.add(new DrawerItem("Industry News", R.drawable.news_logo));

        DrawerAdapter drawerAdapter=new DrawerAdapter(this,drawerItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvMenu.setLayoutManager(layoutManager);
        binding.rvMenu.setAdapter(drawerAdapter);
      // mDrawerLayout.closeDrawer(GravityCompat.START);
//
//        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
////                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
//            }
//        });
//
//        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                //getSupportActionBar().setTitle(R.string.film_genres);
//            }
//        });
//
//        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
//                        .get(childPosition).toString();
//
////                String selectedID =  mExpandableListData.get(mExpandableListTitle.get(groupPosition))
////                        .get(childPosition).getCategory_id();
//
//                String selectedID =  mExpandableListData.get(mExpandableListTitle.get(groupPosition))
//                        .get(childPosition).getName();
//
//                mNavigationManager.showFragmentAction(selectedID);
////                if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
////                     mNavigationManager.showFragmentAction(selectedItem);
////                } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
////                    //  mNavigationManager.showFragmentComedy(selectedItem);
////                } else if (items[2].equals(mExpandableListTitle.get(groupPosition))) {
////                    // mNavigationManager.showFragmentDrama(selectedItem);
////                } else if (items[3].equals(mExpandableListTitle.get(groupPosition))) {
////                    //  mNavigationManager.showFragmentMusical(selectedItem);
////                } else if (items[4].equals(mExpandableListTitle.get(groupPosition))) {
////                    //mNavigationManager.showFragmentThriller(selectedItem);
////                } else {
////                    throw new IllegalArgumentException("Not supported fragment type");
////                }
//
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                return false;
//            }
//        });


    }

    private void setHomeFragment() {
        Home_Fragment homefragment = new Home_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame, homefragment, "HOME_FRAGMENT");
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_drawer:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {

                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
    }



    public void Update_header(String title) {
        tv_main_header.setText(title);
    }
    public void CheckBottom(int pos) {
        navView.getMenu().getItem(pos).setChecked(true);
    }

    public void CloseDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        Home_Fragment myFragment = (Home_Fragment) getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            //finish();
            Exit();
        } else {
            super.onBackPressed();
        }

    }

    private void Exit() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to exit the app!")
                .setIcon((int) R.drawable.news_logo)
                .setPositiveButton(getResources().getString(R.string.yes), new C03424())
                .setNegativeButton(getResources().getString(R.string.no), new C03435()).show();

    }

    class C03424 implements DialogInterface.OnClickListener {
        C03424() {
        }

        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }

    class C03435 implements DialogInterface.OnClickListener {
        C03435() {
        }

        public void onClick(DialogInterface dialog, int which) {
//            drawer.closeDrawer(GravityCompat.START);
        }
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
