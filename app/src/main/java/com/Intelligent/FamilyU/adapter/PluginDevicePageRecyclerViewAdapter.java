package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;

import java.util.HashMap;
import java.util.List;

public class PluginDevicePageRecyclerViewAdapter extends RecyclerView.Adapter<PluginDevicePageRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, String>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public PluginDevicePageRecyclerViewAdapter(Context context, List<HashMap<String, String>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView imv;
        public ImageView startingTv;
        public LinearLayout ll;
        public TextView line;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PluginDevicePageRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_plugin_device_paget_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
        viewHolder.imv = (TextView) convertView.findViewById(R.id.iv);
        viewHolder.startingTv = (ImageView) convertView.findViewById(R.id.is_starting_tv);
        viewHolder.line = (TextView) convertView.findViewById(R.id.line);
        viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, String> map = mlist.get(position);
        final String name = map.get("name");


        String status = map.get("status");
        holder.name.setText(name);
        if (mlist.size() == (position + 1)) {
            holder.line.setVisibility(View.GONE);
        }
        if ("ONLINE".equals(status)) {
            holder.startingTv.setBackgroundResource(R.mipmap.greed_on);
        } else {
            holder.startingTv.setBackgroundResource(R.mipmap.red_off);
        }
        if ("add".equals(name)) {
            holder.name.setVisibility(View.GONE);
            holder.startingTv.setVisibility(View.GONE);
            holder.imv.setBackgroundResource(R.mipmap.dlna_icon_member_add);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.startingTv.setVisibility(View.VISIBLE);
            if (name.contains("远程下载")) {
                holder.imv.setBackgroundResource(R.mipmap.remote_download_page);
            } else if (name.contains("云存储")) {
                holder.imv.setBackgroundResource(R.mipmap.cloud_storage_page);
            }
        }
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

    public void clear() {
        if (null != mlist) {
            mlist.clear();
        }
        notifyDataSetChanged();
    }

    public List<HashMap<String, String>> getAllList() {
        return mlist;
    }

    // 添加数据
    public void addData(HashMap<String, String> map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    // 删除数据
    public void removeData(int position) {
        mlist.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addBtn() {
        if (null == mlist || mlist.size() == 0) {
            return;
        }
//        if ("add".equals(mlist.get(mlist.size() - 1).get("name"))) {
//            mlist.remove(mlist.size() - 1);
//        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "add");
        mlist.add(map);
        notifyItemInserted(mlist.size() - 1);
//        List<HashMap<String, String>> list = new ArrayList<>();
//        list.addAll(mlist);
//        refreshData(list);
    }
}
