package com.d4it_b.hajidanumroh;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.d4it_b.hajidanumroh.adapter.AdapterDetail;
import com.d4it_b.hajidanumroh.db.DBHandler;
import com.d4it_b.hajidanumroh.db.DbQueries;
import com.d4it_b.hajidanumroh.model.ContentDetailAct;

import java.util.ArrayList;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {

//    private RecyclerView mRecyclerView;
    private ListView listViewContent;
    private TextView title;
    private AdapterDetail adapterDetail;
    private ArrayList<ContentDetailAct> listData;

    ImageView img;

    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;
    DbQueries dbQueries;
    CardView cardView;
    CollapsingToolbarLayout collapsingToolbar;

    int[] res = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    Random rand;
    int rndInt;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_haji);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewContent= (ListView) findViewById(R.id.listView);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        cardView = (CardView)findViewById(R.id.card_view);
        title = (TextView)findViewById(R.id.title_detail);
        img = (ImageView) findViewById(R.id.img);
        dbHandler = new DBHandler(this);
        dbQueries = new DbQueries(getApplicationContext());

        listData= new ArrayList<>();
        dbQueries.open();

        title.setText(getIntent().getExtras().getString("title"));

        if (getIntent().getIntExtra("isSetIsi", 0) != 0){
            collapsingToolbar.setTitle(dbQueries.getTitleAct(getIntent().getIntExtra("idTitle", 0), "tb_sub_menu"));
            listData = dbQueries.getContentDetail(getIntent().getIntExtra("idData", 0),"tb_isi_detail_content");
            Log.i("IdData", "onGroupClick: "+"HALOOO");
        }else{
            collapsingToolbar.setTitle(dbQueries.getTitleAct(getIntent().getIntExtra("idTitle", 0), "tb_utama"));
            listData = dbQueries.getContentDetail(getIntent().getIntExtra("idData", 0),"tb_detail_content");
            Log.i("IdData", "onGroupClick: "+"HAIII");
        }
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#44ffffff"));
//        collapsingToolbar.setBackgroundColor();
        adapterDetail = new AdapterDetail(this, listData);
        Log.i("jumlahData", "onCreate: "+adapterDetail.getCount());
        listViewContent.setAdapter(adapterDetail);


        rand = new Random();
        rndInt = rand.nextInt(res .length);
        img.setImageDrawable(getResources().getDrawable(res[rndInt]));
    }

}
