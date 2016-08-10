package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import java.util.List;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoAdapter extends BaseAdapter {
    private List<String> contentData;
    private Context mContext;

    public AccountInfoAdapter(Context context, List<String> data) {
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
            convertView = mInflater.inflate(R.layout.account_info_activity_lv_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_name_tv);
            holder.conentNameTv = (TextView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_name_content_tv);
            holder.rightArrowIv = (ImageView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_right_arrow_iv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        String contentStr = contentData.get(position);
        String names[] = contentStr.split("_");
        holder.contentDescTv.setText(names[0]);
        if (names.length == 1 || TextUtils.equals(names[1], "0")) {
            holder.rightArrowIv.setVisibility(View.VISIBLE);
            holder.conentNameTv.setVisibility(View.GONE);
        } else {
            holder.conentNameTv.setVisibility(View.VISIBLE);
            holder.conentNameTv.setText(names[1]);
            holder.rightArrowIv.setVisibility(View.GONE);
        }

        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;    // 消息未读条数
        TextView conentNameTv;
        ImageView rightArrowIv;
    }
}
