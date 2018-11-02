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
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteOneTaskBean;
import com.Intelligent.FamilyU.utils.MediaFile;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class DownloadFileStartingListRecyclerViewAdapter extends RecyclerView.Adapter<DownloadFileStartingListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, Object>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private String dls;
    private String leng;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemDelete(int position);

        void onItemPause(int position, TextView tv, TextView show);

        void onItemTimeSetting(int position);

        void onItemLongClick(int position);
    }

    public DownloadFileStartingListRecyclerViewAdapter(Context context, List<HashMap<String, Object>> list, OnItemClickListener onItemClickListener) {
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
    public DownloadFileStartingListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                     int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_remote_download_starting_list_item, parent, false);
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
        HashMap<String, Object> map = mlist.get(position);
        RemoteListBean.TaskInfo tk = (RemoteListBean.TaskInfo) map.get("TaskInfo");
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.0");
        float dl = (float) tk.getDownload() / 1024;
        dls = String.valueOf((int) dl);
        if (dl > 0) {
            dls = df.format(((float) tk.getDownload() / 1024));
        }
        leng = df.format(((float) tk.getLength() / 1024));
        sb.append(dls).append("K/").append(leng).append("K");
        String fileName = tk.getFileName();
        holder.title.setText(fileName);
        holder.message.setText(sb.toString());
        holder.time.setText("");
        String url = tk.getUrl();
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
        String status = tk.getStatus();
        if ("Create".equals(status) || "Stop".equals(status)) {
            holder.pause.setBackgroundResource(R.mipmap.download_starting);
        } else if ("Download".equals(status)) {
            holder.pause.setBackgroundResource(R.mipmap.download_pause);
        }
        holder.pauseRl.setOnClickListener(new ItemOnClickListener(status, holder.pause, holder.message, position));
        holder.speedRl.setOnClickListener(new ItemOnClickListener(status, holder.speed, holder.message, position));
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
//                    if ("Create".equals(status) || "Stop".equals(status)) {
//                        tv.setBackgroundResource(R.mipmap.download_pause);
//                    } else if ("Download".equals(status)) {
//                        tv.setBackgroundResource(R.mipmap.download_starting);
//                    }
                    mOnItemClickListener.onItemPause(position, message, tv);
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

    public void changeStatus(TextView show, String status) {
        if ("Create".equals(status) || "Stop".equals(status)) {
            show.setBackgroundResource(R.mipmap.download_pause);
        } else if ("Download".equals(status)) {
            show.setBackgroundResource(R.mipmap.download_starting);
        }
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

    // 添加数据
    public void addData(HashMap<String, Object> map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void showSpeedTextView(RemoteOneTaskBean.TaskInfo mTaskInfo, TextView tv) {
        int download;
        if ("Complete".equals(mTaskInfo.getStatus()) && mTaskInfo.getDownload() == mTaskInfo.getLength()) {
            download = mTaskInfo.getLength();
        } else {
            download = mTaskInfo.getDownload();
        }
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.0");
        if (download > 0) {
            dls = df.format(((float) download / 1024));
        }
        sb.append(dls).append("K/").append(leng).append("K");
        tv.setText(sb.toString());
    }

    public List<HashMap<String, Object>> getAllList() {
        return mlist;
    }

}
