package com.d4it_b.hajidanumroh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.d4it_b.hajidanumroh.db.DbQueries;
import com.d4it_b.hajidanumroh.fragment.FragmentSubMenu;
import com.d4it_b.hajidanumroh.model.DetailContent;
import com.d4it_b.hajidanumroh.model.IsiDetailContent;
import com.d4it_b.hajidanumroh.model.SubMenuContent;
import com.d4it_b.hajidanumroh.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainAct extends AppCompatActivity{
    private int SETTINGS_ACTION = 1;

    DbQueries dbQueries;
    ArrayList<SubMenuContent> subMenuContents;
    ArrayList<DetailContent>detailContents;
    ArrayList<IsiDetailContent>isiDetailContents;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.act_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);

        img = (ImageView) findViewById(R.id.img);

        int[] res = {R.drawable.gmb1, R.drawable.gmb2, R.drawable.gmb3, R.drawable.gmb4, R.drawable.gmb5, R.drawable.gmb6, R.drawable.gmb7, R.drawable.gmb8, R.drawable.gmb9, R.drawable.gmb10};
        Random rand = new Random();
        int rndInt = rand.nextInt(res .length);
        img.setImageDrawable(getResources().getDrawable(res[rndInt]));

        subMenuContents = new ArrayList<>();
        detailContents = new ArrayList<>();
        isiDetailContents = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        dbQueries = new DbQueries(this);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        int selected_tab = getSelectedTab();
        if (selected_tab != 0) {
            Log.i("SELECTED_TAB", "selected tab : "+selected_tab);
            tabLayout.setScrollPosition(selected_tab,0f,true);
            viewPager.setCurrentItem(selected_tab);
        }


//        TextView text = (TextView) findViewById(R.id.activityLabel);
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Honej.ttf");
//        text.setTypeface(tf);
    }

    public int getSelectedTab(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int selected_tab = sharedPref.getInt("SELECTED_TAB", 0);
        switch (selected_tab){
            case 3:
                return 3;
            case 2:
                return 1;
            case 1:
                return 0;
            case 4:
                return 4;
            case 5:
                return 2;
        }
        return selected_tab;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        FragmentSubMenu fragmentUmroh = new FragmentSubMenu();
        fragmentUmroh.setRetainInstance(true);
        fragmentUmroh.setIndexMain(1);
        adapter.addFragment(fragmentUmroh, "Umrah");

        FragmentSubMenu fragmentHaji = new FragmentSubMenu();
        fragmentHaji.setRetainInstance(true);
        fragmentHaji.setIndexMain(2);
        adapter.addFragment(fragmentHaji, "HAJI");

        FragmentSubMenu fragmentDAM = new FragmentSubMenu();
        fragmentDAM.setRetainInstance(true);
        fragmentDAM.setIndexMain(5);
        adapter.addFragment(fragmentDAM, "DAM");

        FragmentSubMenu fragmentSholat = new FragmentSubMenu();
        fragmentSholat.setRetainInstance(true);
        fragmentSholat.setIndexMain(3);
        adapter.addFragment(fragmentSholat, "Shalat");

        FragmentSubMenu fragmentDoa = new FragmentSubMenu();
        fragmentDoa.setRetainInstance(true);
        fragmentDoa.setIndexMain(4);
        adapter.addFragment(fragmentDoa, "DOA");


        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.themeSetting:
                finish();
                startActivity(new Intent(this,SettingsActivity.class));
//                Runtime.getRuntime().exit(0);
                return true;
            case R.id.profile:
                Intent intent = new Intent(MainAct.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isExternalStrorageWriteable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Tutup Aplikasi")
                .setMessage("Anda yakin ingin keluar dari aplikasi?")
                .setPositiveButton("Iya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
