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
import com.Intelligent.FamilyU.model.message.entity.MessageBean;
import com.Intelligent.FamilyU.model.message.entity.MessageItmeBean;
import com.Intelligent.FamilyU.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class SystemMessageListRecyclerViewAdapter extends RecyclerView.Adapter<SystemMessageListRecyclerViewAdapter.ViewHolder> {
    private List<MessageBean> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public SystemMessageListRecyclerViewAdapter(Context context, List<MessageBean> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public TextView time;
        public LinearLayout ll;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SystemMessageListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                              int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_add_device_message_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
        viewHolder.imageView = convertView.findViewById(R.id.iv);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        MessageBean map = mlist.get(position);
        holder.title.setText(map.getComment());
        MessageItmeBean pe = new Gson().fromJson(map.getContent(), MessageItmeBean.class);
        holder.message.setText(pe.getOrderType());
        holder.time.setText(Utils.showNowTime(Long.parseLong(map.getCreateTime())));
        if (map.getIsRead() == 2) {
            holder.imageView.setVisibility(View.GONE);
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
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

    public void refreshData(List<MessageBean> list) {
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

    public List<MessageBean> getAllList() {
        return mlist;
    }

    // 添加数据
    public void addData(MessageBean map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    public void refreshOneItem(MessageBean bean, int position) {
        mlist.set(position, bean);
        notifyItemChanged(position);
    }

    // 删除数据
    public void removeData(int position) {
        mlist.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
