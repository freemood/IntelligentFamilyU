package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwitchView;

import java.util.HashMap;
import java.util.List;

public class MainPluginListRecyclerViewAdapter extends RecyclerView.Adapter<MainPluginListRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, String>> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public MainPluginListRecyclerViewAdapter(Context context, List<HashMap<String, String>> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public SwitchView switchView;
        public LinearLayout ll;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainPluginListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_page_plugin_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.message = convertView.findViewById(R.id.is_starting_tv);
        viewHolder.switchView = convertView.findViewById(R.id.switch_view);
        viewHolder.ll = convertView.findViewById(R.id.ll);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashMap<String, String> map = mlist.get(position);
        final String title = map.get("title");
        holder.title.setText(title);
        //holder.message.setText(map.get("message"));
        initSwitch(holder.switchView, holder.message, map.get("title"));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.contains("远程下载")) {
                    context.startActivity(new Intent(context, RemotedownloadActivity.class));
                }
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

    private void initSwitch(SwitchView sv, final TextView tv, final String key) {
        String name = Utils.readSharedPreferences(context.getResources().getString(R.string.home_login_name));
        final StringBuffer nameSb = new StringBuffer();
        nameSb.append(name).append(key);
        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened(isPushAble);
        tv.setText(isPushAble ? R.string.home_drawer_starting : R.string.home_drawer_not_starting);
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                Utils.write(nameSb.toString(), true);
                tv.setText(R.string.home_drawer_starting);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                Utils.write(nameSb.toString(), false);
                tv.setText(R.string.home_drawer_not_starting);
            }
        });
    }
}
