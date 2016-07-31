package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.widget.CircularImage;

import java.util.List;

/**
 * Created by chen on 16/7/24.
 */
public class PerfectInformationAdapter extends BaseAdapter {
    private List<String> contentData;
    private Context mContext;
    private Bitmap userIcon;
    public PerfectInformationAdapter(Context context, List<String> data){
        contentData = data;
        mContext = context;
    }

    public void setUserIcon(Bitmap icon) {
        userIcon = icon;
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
            convertView = mInflater.inflate(R.layout.perfect_information_activity_lv_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView)convertView.findViewById(R.id.perfect_information_activity_lv_item_name_tv);
            holder.conentNameTv = (TextView)convertView.findViewById(R.id.perfect_information_activity_lv_item_desc_tv);
            holder.rightArrowIv = (ImageView)convertView.findViewById(R.id.perfect_information_activity_lv_item_right_arrow_iv);
            holder.grayLine = convertView.findViewById(R.id.perfect_information_activity_lv_item_gray_line);
            holder.userIconIv = (CircularImage)convertView.findViewById(R.id.perfect_information_activity_lv_item_user_icon_iv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        String contentStr = contentData.get(position);
        String names[] =contentStr.split("_");
        holder.contentDescTv.setText(names[0]);
        holder.conentNameTv.setText(names[1]);
        if (position == 0 || position == 5) {
            holder.grayLine.setVisibility(View.VISIBLE);
        } else {
            holder.grayLine.setVisibility(View.GONE);
        }

        if (position == 0) {
            if (userIcon != null) {
                holder.userIconIv.setVisibility(View.VISIBLE);
                holder.userIconIv.setImageBitmap(userIcon);
            }
        } else {
            holder.userIconIv.setVisibility(View.GONE);
        }

        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;	// 消息未读条数
        TextView conentNameTv;
        ImageView rightArrowIv;
        CircularImage userIconIv;
        View grayLine;
    }
}
