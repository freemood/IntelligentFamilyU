package com.Intelligent.FamilyU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.view.SwitchView;

import java.util.HashMap;
import java.util.List;

public class ScenceDefutEditListRecyclerViewAdapter extends RecyclerView.Adapter<ScenceDefutEditListRecyclerViewAdapter.ViewHolder> {
    private List<SceneOperation> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private String dls;
    private String leng;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemDelete(int position);

        void onItemLongClick(int position);
    }

    public ScenceDefutEditListRecyclerViewAdapter(Context context, List<SceneOperation> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView title;
        public TextView delete;
        public LinearLayout ll;
        public SwitchView switchView;

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
    public ScenceDefutEditListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.activity_scence_defut_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.switchView = convertView.findViewById(R.id.switch_sclete_item);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SceneOperation map = mlist.get(position);

        String title = (String) map.getParamIndex();
        String status = (String) map.getParamValue();
        holder.title.setText(title);
        initSwitch(holder.switchView, status);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    private void initSwitch(SwitchView sv, String status) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("1".equals(status));
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
//                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
//                Utils.write(nameSb.toString(), false);
            }
        });
    }

    public void refreshData(List<SceneOperation> list) {
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
    public void addData(SceneOperation map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public List<SceneOperation> getAllList() {
        return mlist;
    }

}
