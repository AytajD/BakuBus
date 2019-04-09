package com.dadashova.aytaj.bakubustest.Containers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dadashova.aytaj.bakubustest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BusAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private SpinnerCallBack callBack;
    private Map<String, String> mHashMap;
    private ArrayList<String> mValues;
    private ArrayList<String> mKeys;

    public BusAdapter( Context context, Map<String, String> mHashMap) {

        this.mHashMap = mHashMap;
        this.context = context;
        this.callBack = (SpinnerCallBack) context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate( R.layout.item_row_route,viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


      HashMap<String, String> hashMap = sortByValue((HashMap<String, String>) mHashMap);

      mValues = (ArrayList<String>) getValues(hashMap);
      mKeys = (ArrayList<String>) getKeys(hashMap);
      viewHolder.mRouteNumber.setText(mValues.get(i));
      viewHolder.mRoute.setText(mKeys.get(i));



        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBack.onItemClicked(viewHolder.mRouteNumber
                                .getText()
                                .toString(),

                        viewHolder.mRoute
                                .getText()
                                .toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHashMap.size();
    }


    public interface SpinnerCallBack{

        void onItemClicked(String busNum, String busRoute);

    }


    public static HashMap<String, String> sortByValue(HashMap<String, String> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String> > list = new LinkedList (hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, String> temp = new LinkedHashMap();
        for (Map.Entry<String, String> aa : list) {
            temp
            .put(aa.getKey(),
                    aa.getValue());
        }
        return temp;
    }


    public static List<String> getValues(Map map) {
        return new ArrayList(map.values());
    }

    public static List<String> getKeys(Map map) {
        return new ArrayList(map.keySet());
    }
}