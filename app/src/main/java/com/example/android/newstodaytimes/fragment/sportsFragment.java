package com.example.android.newstodaytimes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.newstodaytimes.adapter.Adapter;
import com.example.android.newstodaytimes.utilityandinterface.ApiUtilities;
import com.example.android.newstodaytimes.modalclass.ModelClass;
import com.example.android.newstodaytimes.R;
import com.example.android.newstodaytimes.mainNews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sportsFragment extends Fragment {


    String api = "29deb8a6f44c42f38803869979a4f3c3" ;
    ArrayList<ModelClass>modelClassArrayList ;
    Adapter adapter ;
    String country = "in" ;
    private RecyclerView recyclerViewOfSports ;
    private String category = "sports";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sportsfragment , null);

        recyclerViewOfSports = view.findViewById(R.id.recyclerViewOfSports);
        modelClassArrayList = new ArrayList<>();
        recyclerViewOfSports.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext() , modelClassArrayList);
        recyclerViewOfSports.setAdapter(adapter);

        findNews();

        return view ;
    }

    private void findNews() {
     ApiUtilities.getApiInterFace().getCategoryNews(country , category, 100 , api).enqueue(new Callback<mainNews>() {
         @Override
         public void onResponse(Call<mainNews> call, Response<mainNews> response) {
             if (response.isSuccessful()){
                 modelClassArrayList.addAll(response.body().getArticles());
                 adapter.notifyDataSetChanged();
             }
         }

         @Override
         public void onFailure(Call<mainNews> call, Throwable t) {

         }
     });
    }
}
