package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;

import java.util.HashMap;
import java.util.List;

public class DeviceListRecyclerViewAdapter extends RecyclerView.Adapter<DeviceListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, String>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public DeviceListRecyclerViewAdapter(Context context, List<HashMap<String, String>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ImageView imv1;
        public TextView model;
        public ImageView imv2;
        public LinearLayout ll;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DeviceListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_add_device_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.imv1 = (ImageView) convertView.findViewById(R.id.iv1);
        viewHolder.model = (TextView) convertView.findViewById(R.id.model);
        viewHolder.imv2 = (ImageView) convertView.findViewById(R.id.iv2);
        viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, String> map = mlist.get(position);
        holder.name.setText(map.get("name"));
        holder.model.setText(map.get("model"));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(position);
                return true;
            }
        });

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void refreshData(List<HashMap<String, String>> list) {
        if (null != mlist) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public List<HashMap<String, String>> getAllList() {
        return mlist;
    }

}
