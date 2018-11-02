package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.cloud.entity.CloudStorageFile;
import com.Intelligent.FamilyU.utils.MediaFile;
import com.Intelligent.FamilyU.utils.Utils;
import java.util.List;

public class CloudTypeListRecyclerViewAdapter extends RecyclerView.Adapter<CloudTypeListRecyclerViewAdapter.ViewHolder> {
    private List<CloudStorageFile> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemDelete(int position);
        void onItemDownload(int position);
        void onItemLongClick(int position);
    }

    public CloudTypeListRecyclerViewAdapter(Context context, List<CloudStorageFile> list, OnItemClickListener onItemClickListener) {
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
        public TextView download;


        public ViewHolder(View itemView) {
            super(itemView);

            LinearLayout ll = itemView.findViewById(R.id.ll);
            ll.setOnClickListener(this);
            ll.setOnLongClickListener(this);

            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
            download = itemView.findViewById(R.id.download);
            download.setOnClickListener(this);
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
                case R.id.download:
                    mOnItemClickListener.onItemDownload(getAdapterPosition());
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
    public CloudTypeListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_cloud_type_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.message = convertView.findViewById(R.id.message);
        viewHolder.time = convertView.findViewById(R.id.time);
        viewHolder.image = convertView.findViewById(R.id.image);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        CloudStorageFile map = mlist.get(position);
        String title = (String) map.getName();
        String message = Utils.showNowTime(map.getLastTime());
        holder.title.setText(title);
        holder.message.setText(message);

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

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void refreshData(List<CloudStorageFile> list) {
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
    public void addData(CloudStorageFile map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mlist.remove(position);
        notifyItemRemoved(position);
    }


    public List<CloudStorageFile> getAllList() {
        return mlist;
    }

}
