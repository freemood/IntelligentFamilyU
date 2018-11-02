package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;

import java.util.HashMap;
import java.util.List;

public class PluginListRecyclerViewAdapter extends RecyclerView.Adapter<PluginListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, String>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public PluginListRecyclerViewAdapter(Context context, List<HashMap<String, String>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public PluginListRecyclerViewAdapter(Context context, List<HashMap<String, String>> list) {
        super();
        mlist = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public TextView unstall;
        public TextView install;
        public LinearLayout ll;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PluginListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_plugin_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
        viewHolder.unstall = (TextView) convertView.findViewById(R.id.uninstall_tv);
        viewHolder.install = (TextView) convertView.findViewById(R.id.install_tv);
        viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, String> map = mlist.get(position);
        holder.title.setText(map.get("title"));
        holder.message.setText(map.get("message"));
        holder.install.setText(map.get("install"));
        holder.unstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
