package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ParentsCycleInfo;
import com.jgkj.parentscycle.bean.ParentsCyclePostsListItem;
import com.jgkj.parentscycle.utils.AsyncImageUtil;

import java.util.List;

/**
 * Created by chen on 16/7/24.
 */
public class ParentsCycleAdapter extends BaseAdapter{
    private List<ParentsCyclePostsListItem> contentData;
    private Context mContext;
    public ParentsCycleAdapter(Context context, List<ParentsCyclePostsListItem> data){
        contentData = data;
        mContext = context;
    }

    public void setDataList(List<ParentsCyclePostsListItem> data){
        contentData = data;
    }


    @Override
    public int getCount() {
        return contentData.size();
    }

    @Override
    public Object getItem(int position) {
        return contentData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new MineViewHolder();
            convertView = mInflater.inflate(R.layout.parents_cycle_activity_listview_item, null);
            convertView.setTag(holder);
            holder.nameTv = (TextView)convertView.findViewById(R.id.parents_cycle_activity_listview_item_name_tv);
            holder.timeTv = (TextView)convertView.findViewById(R.id.parents_cycle_activity_listview_item_time_tv);
            holder.conentTv = (TextView)convertView.findViewById(R.id.parents_cycle_activity_listview_item_content_tv);
            holder.schoolIv = (ImageView)convertView.findViewById(R.id.parents_cycle_activity_listview_item_school_icon_iv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        ParentsCyclePostsListItem pci = contentData.get(position);
        holder.nameTv.setText(pci.getTopic());
        holder.timeTv.setText(pci.getCreatetime());
        holder.conentTv.setText(pci.getTopictext());
        holder.schoolIv.setImageResource(R.mipmap.school2);
        AsyncImageUtil.asyncLoadImage( holder.schoolIv,
                pci.getTopicimg(),
                0, false, false);
        return convertView;
    }

    class MineViewHolder {
        TextView nameTv;
        TextView timeTv;
        TextView conentTv;
        ImageView schoolIv;
    }
}
