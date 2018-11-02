package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;

import java.util.HashMap;
import java.util.List;

public class AssociatedEquipmentListRecyclerViewAdapter extends RecyclerView.Adapter<AssociatedEquipmentListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, Object>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public AssociatedEquipmentListRecyclerViewAdapter(Context context, List<HashMap<String, Object>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
//        public TextView message;
//        public TextView time;
        public RelativeLayout ll;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AssociatedEquipmentListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_gateway_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
//        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
//        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.ll = (RelativeLayout) convertView.findViewById(R.id.ll);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, Object> map = mlist.get(position);
        holder.title.setText((String)map.get("title"));
//        holder.message.setText(map.get("message"));
//        holder.time.setText(map.get("time"));
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

    public void refreshData(List<HashMap<String, Object>> list) {
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

    public void refresh(){
        notifyDataSetChanged();
    }

    public List<HashMap<String, Object>> getAllList() {
        return mlist;
    }

    // 添加数据
    public void addData(HashMap<String, Object> map, int position) {
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

}
