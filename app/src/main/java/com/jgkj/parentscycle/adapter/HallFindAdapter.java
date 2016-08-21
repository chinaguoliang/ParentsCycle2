package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import java.util.List;

/**
 * Created by chen on 16/7/24.
 */
public class HallFindAdapter extends BaseAdapter {

    private List<String> contentData;
    private Context mContext;
    public HallFindAdapter(Context context, List<String> data){
        contentData = data;
        mContext = context;
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
            convertView = mInflater.inflate(R.layout.hall_find_fragment_layout_listview_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView)convertView.findViewById(R.id.hall_find_fragment_layout_listview_item_name_tv);
            holder.iconIv = (ImageView)convertView.findViewById(R.id.hall_find_fragment_layout_listview_item_icon_iv);
            holder.heightView = convertView.findViewById(R.id.hall_find_fragment_layout_listview_item_height_view);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        holder.contentDescTv.setText(contentData.get(position));
        if (position == 0) {
            holder.heightView.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            holder.iconIv.setImageResource(R.mipmap.grow_record);
        } else if (position == 2)  {
            holder.heightView.setVisibility(View.VISIBLE);
            holder.iconIv.setImageResource(R.mipmap.new_add_parents);
        } else if (position == 3)  {
            holder.iconIv.setImageResource(R.mipmap.new_add_teacher);
        } else if (position == 4)  {
            holder.iconIv.setImageResource(R.mipmap.add_to_school);
        } else if (position == 5)  {
            holder.heightView.setVisibility(View.VISIBLE);
            holder.iconIv.setImageResource(R.mipmap.consultation);
        }


        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;	// 消息未读条数
        ImageView iconIv;
        View heightView;
    }
}
