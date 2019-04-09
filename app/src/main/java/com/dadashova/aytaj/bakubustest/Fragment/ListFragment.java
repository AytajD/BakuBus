package com.dadashova.aytaj.bakubustest.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dadashova.aytaj.bakubustest.Containers.BusAdapter;
import com.dadashova.aytaj.bakubustest.HttpClient.BusService;
import com.dadashova.aytaj.bakubustest.HttpClient.RetrofitInstance;
import com.dadashova.aytaj.bakubustest.POJOS.Bus;
import com.dadashova.aytaj.bakubustest.POJOS.ResponseModel;
import com.dadashova.aytaj.bakubustest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private BusAdapter mAdapter;
    private Map mHashMap;


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);

        Log.d("ListFragmnet", "on List Fragment");

        mHashMap = new HashMap<String, String>();

        getWeatherData();
        return view;
    }

    private void getWeatherData() {

        BusService busService = RetrofitInstance.getRetrofitInstance().create(BusService.class);

        Call<ResponseModel> call = busService.getBusData();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                getDataList(response.body());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                Log.e("fail", t.getMessage());

            }
        });

    }

    private void getDataList(ResponseModel body) {

        if (body.getmBusList() != null) {

            for (Bus bus : body.getmBusList()) {


                mHashMap.put(bus.getmBus().getmRouteName(),
                        bus.getmBus().getmDisplayRouteCode());

            }



            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL);

            itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(),
                    R.drawable.drawable_item_decorator)));

            mRecyclerView.addItemDecoration(itemDecorator);

            mAdapter = new BusAdapter(getContext(), mHashMap);

            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);

        }


    }

}