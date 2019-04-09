package com.dadashova.aytaj.bakubustest.Containers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dadashova.aytaj.bakubustest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {

        super(itemView);

        ButterKnife.bind(this, itemView);

    }

    @BindView(R.id.text_route_number)
    TextView mRouteNumber;
    @BindView(R.id.text_route)
    TextView mRoute;
    @BindView(R.id.linear_layout)
    LinearLayout mLinearLayout;


}
