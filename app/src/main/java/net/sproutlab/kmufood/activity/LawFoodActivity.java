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
import net.sproutlab.kmufood.datamod.lawFooddata;

import java.util.Calendar;

public class LawFoodActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    lawFooddata mDatamod;
    public static String[][] MealMenu = new String[5][8];
    public static String[][] MealPrice = new String[5][2];

    CoordinatorLayout mCLayout;
    int curindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawfood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_meal1).setChecked(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.tabpager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);

        mCLayout = (CoordinatorLayout) findViewById(R.id.appbar_view);

        Calendar c = Calendar.getInstance();
        curindex = c.get(Calendar.DAY_OF_WEEK);
        if(curindex == 1 || curindex == 7) curindex = 6;
        else curindex -= 2;

        mDatamod = new lawFooddata(this);
        MealMenu = mDatamod.getMenu();
        MealPrice = mDatamod.getPrice();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_meal2){
            startActivity((new Intent(this, StuFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        } else if(id == R.id.nav_meal3){
            startActivity((new Intent(this, StaffFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        } else if(id == R.id.nav_meal4){
            startActivity((new Intent(this, ChungFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        } else if(id == R.id.nav_meal5){
            startActivity((new Intent(this, DormFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            View rootView = inflater.inflate(R.layout.fragment_lawfood, container, false);
            ((TextView) rootView.findViewById(R.id.content_section1_1)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][0]);
            ((TextView) rootView.findViewById(R.id.content_section1_2)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][1]);
            ((TextView) rootView.findViewById(R.id.content_section1_1p)).setText(MealPrice[getArguments().getInt(ARG_SECTION_NUMBER) - 1][0]);
            ((TextView) rootView.findViewById(R.id.content_section1_2p)).setText(MealPrice[getArguments().getInt(ARG_SECTION_NUMBER) - 1][1]);


            ((TextView) rootView.findViewById(R.id.content_section2)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][2]);
            ((TextView) rootView.findViewById(R.id.content_section3)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][3]);
            ((TextView) rootView.findViewById(R.id.content_section4)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][4]);
            ((TextView) rootView.findViewById(R.id.content_section5)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][5]);
            ((TextView) rootView.findViewById(R.id.content_section6)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][6]);
            ((TextView) rootView.findViewById(R.id.content_section7)).setText(MealMenu[getArguments().getInt(ARG_SECTION_NUMBER) - 1][7]);
            rootView.findViewById(R.id.card_scrollv).setFadingEdgeLength(150);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
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
            }
            return null;
        }
    }
}
