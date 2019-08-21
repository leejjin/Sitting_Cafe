package com.example.hyunm.sittingcafe;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CustomViewHolder> {

    private ArrayList<CafeData> mList = null;
    private Activity context = null;


    public CafeAdapter(Activity context, ArrayList<CafeData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView per;
        protected TextView people;
        protected TextView table;
        protected TextView chair;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.per = (TextView) view.findViewById(R.id.textView_list_per);
            this.people = (TextView) view.findViewById(R.id.textView_list_people);
            this.table = (TextView) view.findViewById(R.id.textView_list_table);
            this.chair = (TextView) view.findViewById(R.id.textView_list_chair);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cafe_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.name.setText(mList.get(position).getCafe_name());
        viewholder.per.setText(mList.get(position).getCafe_per());
        viewholder.people.setText(mList.get(position).getCafe_people());
        viewholder.table.setText(mList.get(position).getCafe_table());
        viewholder.chair.setText(mList.get(position).getCafe_chair());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}