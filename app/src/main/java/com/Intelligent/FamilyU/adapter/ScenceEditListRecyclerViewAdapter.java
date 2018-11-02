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
import com.Intelligent.FamilyU.view.SwitchView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class ScenceEditListRecyclerViewAdapter extends RecyclerView.Adapter<ScenceEditListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, Object>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private String dls;
    private String leng;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public ScenceEditListRecyclerViewAdapter(Context context, List<HashMap<String, Object>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public LinearLayout ll;
        public SwitchView switchView;

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
    public ScenceEditListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_scence_edit_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.message = convertView.findViewById(R.id.message);
        viewHolder.switchView = convertView.findViewById(R.id.switch_sclete);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, Object> map = mlist.get(position);

        String title = (String) map.get("title");
        String message = (String) map.get("message");
        String status = (String) map.get("status");
        holder.title.setText(title);
        holder.message.setText(message);
        initSwitch(holder.switchView, status, holder.message);
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

    public List<HashMap<String, Object>> getAllList() {
        return mlist;
    }

    private void initSwitch(SwitchView sv, String status, final TextView message) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("1".equals(status));
        message.setText(context.getResources().getString("1".equals(status) ? R.string.home_scence_edit_power_on : R.string.home_scence_edit_power_off));
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                message.setText(R.string.home_scence_edit_power_on);
//                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                message.setText(R.string.home_scence_edit_power_off);
//                Utils.write(nameSb.toString(), false);
            }
        });
    }

}
