package com.efsalokyay.sozlukuygulamasiretrofit;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Kelimeler> kelimelerArrayList;
    private KelimeAdapter adapter;
    private KelimelerDaoInterface kelimelerDIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.main_recylerview);

        toolbar.setTitle("Sözlük Uygulaması-Retrofit2");
        setSupportActionBar(toolbar);

        kelimelerDIF = ApiUtils.getKelimelerDaoInterface();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tumKisiler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_ara);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.e("onQueryTextSubmit", s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("onQueryTextChange", s);
        kelimeAra(s);
        return false;
    }

    public void tumKisiler() {

        kelimelerDIF.tumKelimeler().enqueue(new Callback<KelimelerCevap>() {
            @Override
            public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {

                List<Kelimeler> listeTemp = response.body().getKelimeler();
                adapter = new KelimeAdapter(MainActivity.this, listeTemp);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<KelimelerCevap> call, Throwable t) {

            }
        });
    }

    public void kelimeAra(String aranan_kelime) {

        kelimelerDIF.kelimeAra(aranan_kelime).enqueue(new Callback<KelimelerCevap>() {
            @Override
            public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {

                List<Kelimeler> listeTemp = response.body().getKelimeler();
                adapter = new KelimeAdapter(MainActivity.this, listeTemp);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<KelimelerCevap> call, Throwable t) {

            }
        });
    }
}
