package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.utils.MediaFile;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class CloudStartingListRecyclerViewAdapter extends RecyclerView.Adapter<CloudStartingListRecyclerViewAdapter.ViewHolder> {
    private List<CloudFileBean> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private String dls;
    private String leng;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemDelete(int position);

        void onItemPause(int position, TextView tv);

        void onItemTimeSetting(int position);

        void onItemLongClick(int position);
    }

    public CloudStartingListRecyclerViewAdapter(Context context, List<CloudFileBean> list, OnItemClickListener onItemClickListener) {
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
        public TextView image;
        public LinearLayout ll;
        public TextView pause;
        public TextView speed;
        public RelativeLayout pauseRl;
        public RelativeLayout speedRl;

        public ViewHolder(View itemView) {
            super(itemView);

            LinearLayout ll = itemView.findViewById(R.id.ll);
            ll.setOnClickListener(this);
            ll.setOnLongClickListener(this);

            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll:
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                    break;
                case R.id.delete:
                    mOnItemClickListener.onItemDelete(getAdapterPosition());
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
    public CloudStartingListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                              int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_cloud_download_starting_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.message = convertView.findViewById(R.id.message);
        viewHolder.time = convertView.findViewById(R.id.time);
        viewHolder.image = convertView.findViewById(R.id.image);
        viewHolder.pause = convertView.findViewById(R.id.pause);
        viewHolder.speed = convertView.findViewById(R.id.speed);
        viewHolder.speedRl = convertView.findViewById(R.id.speed_rl);
        viewHolder.pauseRl = convertView.findViewById(R.id.pause_rl);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        CloudFileBean map = mlist.get(position);
        float length = Float.parseFloat(map.getFileLength());
        float download = Float.parseFloat(map.getFileDowdloadLength());
        ;
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.0");
        float dl = (float) download / 1024;
        dls = String.valueOf((int) dl);
        if (dl > 0) {
            dls = df.format(((float) download / 1024));
        }
        leng = df.format(((float) length / 1024));
        sb.append(dls).append("K/").append(leng).append("K");
        String title = map.getFileName();
        holder.title.setText(title);
        holder.message.setText(sb.toString());
        holder.time.setText(map.getCreateTime());
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
        String status = map.getStatus();
        if ("build".equals(status) || "stop".equals(status)) {
            holder.pause.setBackgroundResource(R.mipmap.download_starting);
        } else if ("download".equals(status)) {
            holder.pause.setBackgroundResource(R.mipmap.download_pause);
        }
        holder.pauseRl.setOnClickListener(new ItemOnClickListener(status, holder.pause, holder.message, holder.getAdapterPosition()));
        holder.speedRl.setOnClickListener(new ItemOnClickListener(status, holder.speed, holder.message, holder.getAdapterPosition()));
    }

    class ItemOnClickListener implements View.OnClickListener {
        String status;
        TextView tv;
        int position;
        TextView message;

        public ItemOnClickListener(String status, TextView tv, TextView message, int position) {
            this.status = status;
            this.tv = tv;
            this.message = message;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pause_rl:
                    if ("build".equals(status) || "stop".equals(status)) {
                        tv.setBackgroundResource(R.mipmap.download_pause);
                    } else if ("download".equals(status)) {
                        tv.setBackgroundResource(R.mipmap.download_starting);
                    }
                    mOnItemClickListener.onItemPause(position, message);
                    break;
                case R.id.speed_rl:
//                    Drawable dr = context.getResources().getDrawable(R.mipmap.setting_time);
//                    if (dr == tv.getb) {
//                        tv.setBackgroundResource(R.mipmap.setting_time_ok);
//                    }else{
//                        tv.setBackgroundResource(R.mipmap.setting_time);
//                    }
                    mOnItemClickListener.onItemTimeSetting(position);
                    break;
            }
        }
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

    public void showSpeedTextView(TextView tv, long leng, long downLeng) {
        long download;
        if (leng == downLeng) {
            download = leng;
        } else {
            download = downLeng;
        }
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.0");
        if (download > 0) {
            dls = df.format(((Long) download / 1024));
        }
        String lengs = df.format(((Long) leng / 1024));
        sb.append(dls).append("K/").append(lengs).append("K");
        tv.setText(sb.toString());
    }

    public List<CloudFileBean> getAllList() {
        return mlist;
    }

}
