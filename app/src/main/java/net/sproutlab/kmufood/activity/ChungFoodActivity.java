package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.datamod.chungFooddata;

import java.util.Calendar;

public class ChungFoodActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    chungFooddata mDatamod;
    public static String[][] MealMenu = new String[6][8];
    public static String[][] MealPrice = new String[6][8];

    CoordinatorLayout mCLayout;
    int curindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chungfood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_meal4).setChecked(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.tabpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mCLayout = (CoordinatorLayout) findViewById(R.id.appbar_view);

        Calendar c = Calendar.getInstance();
        curindex = c.get(Calendar.DAY_OF_WEEK);
        if(curindex == 1) curindex = 0;
        else curindex -= 2;

        mDatamod = new chungFooddata(this);
        MealMenu = mDatamod.loadMenu();
        MealPrice = mDatamod.loadPrice();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(curindex);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_meal1) startActivity((new Intent(this, LawFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        else if(id == R.id.nav_meal2) startActivity((new Intent(this, StuFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        else if(id == R.id.nav_meal3) startActivity((new Intent(this, StaffFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        else if(id == R.id.nav_meal5) startActivity((new Intent(this, DormFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chungfood, container, false);
            int sect = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

            int emptycnt = 0;

            if(MealMenu[sect][0].isEmpty() && MealMenu[sect][1].isEmpty() && MealMenu[sect][2].isEmpty() && MealMenu[sect][3].isEmpty() && MealMenu[sect][4].isEmpty()){
                rootView.findViewById(R.id.card_section1).setVisibility(View.GONE);
                emptycnt++;
            } else{
                ((TextView) rootView.findViewById(R.id.content_section1_1)).setText(MealMenu[sect][0]);
                if(MealPrice[sect][0].isEmpty()) rootView.findViewById(R.id.content_section1_1p).setVisibility(View.GONE);
                else ((TextView) rootView.findViewById(R.id.content_section1_1p)).setText("￦"+MealPrice[sect][0]);

                if(MealMenu[sect][1].isEmpty()){
                    rootView.findViewById(R.id.view_section1_sub2).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section1_2)).setText(MealMenu[sect][1]);
                    if(MealPrice[sect][1].isEmpty()) rootView.findViewById(R.id.content_section1_2p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section1_2p)).setText("￦"+MealPrice[sect][1]);
                }

                if(MealMenu[sect][2].isEmpty()){
                    rootView.findViewById(R.id.view_section1_sub3).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section1_3)).setText(MealMenu[sect][2]);
                    if(MealPrice[sect][2].isEmpty()) rootView.findViewById(R.id.content_section1_3p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section1_3p)).setText("￦"+MealPrice[sect][2]);
                }

                if(MealMenu[sect][3].isEmpty()){
                    rootView.findViewById(R.id.view_section1_sub4).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section1_4)).setText(MealMenu[sect][3]);
                    if(MealPrice[sect][3].isEmpty()) rootView.findViewById(R.id.content_section1_4p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section1_4p)).setText("￦"+MealPrice[sect][3]);
                }

                if(MealMenu[sect][4].isEmpty()){
                    rootView.findViewById(R.id.view_section1_sub5).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section1_5)).setText(MealMenu[sect][4]);
                    if(MealPrice[sect][4].isEmpty()) rootView.findViewById(R.id.content_section1_5p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section1_5p)).setText("￦"+MealPrice[sect][4]);
                }
            }

            if(MealMenu[sect][5].isEmpty() && MealMenu[sect][6].isEmpty() && MealMenu[sect][7].isEmpty()){
                rootView.findViewById(R.id.card_section2).setVisibility(View.GONE);
                emptycnt++;
            } else{
                ((TextView) rootView.findViewById(R.id.content_section2_1)).setText(MealMenu[sect][5]);
                if(MealPrice[sect][5].isEmpty()) rootView.findViewById(R.id.content_section2_1p).setVisibility(View.GONE);
                else ((TextView) rootView.findViewById(R.id.content_section2_1p)).setText("￦"+MealPrice[sect][5]);

                if(MealMenu[sect][6].isEmpty()){
                    rootView.findViewById(R.id.view_section2_sub2).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section2_2)).setText(MealMenu[sect][6]);
                    if(MealPrice[sect][6].isEmpty()) rootView.findViewById(R.id.content_section2_2p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section2_2p)).setText("￦"+MealPrice[sect][6]);
                }

                if(MealMenu[sect][7].isEmpty()){
                    rootView.findViewById(R.id.view_section2_sub3).setVisibility(View.GONE);
                } else{
                    ((TextView) rootView.findViewById(R.id.content_section2_3)).setText(MealMenu[sect][7]);
                    if(MealPrice[sect][7].isEmpty()) rootView.findViewById(R.id.content_section2_3p).setVisibility(View.GONE);
                    else ((TextView) rootView.findViewById(R.id.content_section2_3p)).setText("￦"+MealPrice[sect][7]);
                }
            }

            rootView.findViewById(R.id.card_scrollv).setFadingEdgeLength(150);

            if(emptycnt>=2){
                rootView.findViewById(R.id.ndmsg).setVisibility(View.VISIBLE);
            }

            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getString(R.string.wd0);
                case 1:
                    return getString(R.string.wd1);
                case 2:
                    return getString(R.string.wd2);
                case 3:
                    return getString(R.string.wd3);
                case 4:
                    return getString(R.string.wd4);
                case 5:
                    return getString(R.string.wd5);
            }
            return null;
        }
    }
}
