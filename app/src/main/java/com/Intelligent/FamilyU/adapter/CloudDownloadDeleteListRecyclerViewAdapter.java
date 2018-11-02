package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.utils.MediaFile;
import com.Intelligent.FamilyU.utils.Utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class CloudDownloadDeleteListRecyclerViewAdapter extends RecyclerView.Adapter<CloudDownloadDeleteListRecyclerViewAdapter.ViewHolder> {
    private List<CloudFileBean> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);

        void onItemecoveryClick(int position);
    }

    public CloudDownloadDeleteListRecyclerViewAdapter(Context context, List<CloudFileBean> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public TextView delete;
        public TextView time;
        public LinearLayout ll;
        public TextView recovery;
        public TextView image;

        public ViewHolder(View itemView) {
            super(itemView);

            LinearLayout ll = itemView.findViewById(R.id.ll);
            ll.setOnClickListener(this);
            ll.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll:
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                    break;

            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.ll:
                    mOnItemClickListener.onItemLongClick(getAdapterPosition());
                    break;
            }
            return false;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CloudDownloadDeleteListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_cloud_download_delete_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
        viewHolder.time = (TextView) convertView.findViewById(R.id.time);
        viewHolder.recovery = (TextView) convertView.findViewById(R.id.recovery);
        viewHolder.image = (TextView) convertView.findViewById(R.id.image);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        CloudFileBean mCloudFileBean = mlist.get(position);
        float length = Float.parseFloat(mCloudFileBean.getFileLength());
        String title = mCloudFileBean.getFileName();
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.0");
        sb.append(df.format(((float) length / 1024))).append("KB");
        holder.title.setText(title);
        holder.message.setText(sb.toString());
        holder.time.setText(mCloudFileBean.getCreateTime());
        String url = title;
        if (MediaFile.isAudioFileType(url)) {
            holder.image.setBackgroundResource(R.mipmap.music);
        } else if (MediaFile.isVideoFileType(url)) {
            holder.image.setBackgroundResource(R.mipmap.video);
        } else if (MediaFile.isImageFileType(url)) {
            holder.image.setBackgroundResource(R.mipmap.photo);
        } else if (MediaFile.isZipFileType(url)) {
            holder.image.setBackgroundResource(R.mipmap.zips);
        } else {
            holder.image.setBackgroundResource(R.mipmap.doctext);
        }
        holder.recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemecoveryClick(holder.getAdapterPosition());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void refreshData(List<CloudFileBean> list) {
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

    public List<CloudFileBean> getAllList() {
        return mlist;
    }

    // 添加数据
    public void addData(CloudFileBean map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mlist.remove(position);
        notifyItemRemoved(position);
    }
}
