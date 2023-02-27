package com.example.android.newstodaytimes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

public class homeFragment extends Fragment {

    private SearchView searchView ;
    String api = "29deb8a6f44c42f38803869979a4f3c3" ;
    ArrayList<ModelClass>modelClassArrayList ;
    Adapter adapter ;
    String country = "in" ;
    private RecyclerView recyclerViewOfHome ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment , null);


        recyclerViewOfHome = view.findViewById(R.id.recyclerViewOfHome);
        modelClassArrayList = new ArrayList<>();
        recyclerViewOfHome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext() , modelClassArrayList);
        recyclerViewOfHome.setAdapter(adapter);

        findNews();

        return view ;
    }

    private void filterList(String newText) {
        // creating a new array list to filter our data.
        ArrayList<ModelClass> filteredlist = new ArrayList<ModelClass>();

        // running a for loop to compare elements.
        for (ModelClass item : modelClassArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void findNews() {

        ApiUtilities.getApiInterFace().getNews(country , 100 , api).enqueue(new Callback<mainNews>() {
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
