package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.AnnouncementCommentListItemInfo;

import java.util.List;

/**
 * Created by chen on 16/7/23.
 */
public class AnnouncementCommentAdapter extends BaseAdapter {
    private List<AnnouncementCommentListItemInfo> contentData;
    private Context mContext;
    public AnnouncementCommentAdapter(Context context, List<AnnouncementCommentListItemInfo> data){
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
        final HallMainChannelViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new HallMainChannelViewHolder();
            convertView = mInflater.inflate(R.layout.announcement_detail_comment_list_item, null);
            convertView.setTag(holder);
            holder.classNameTv = (TextView)convertView.findViewById(R.id.announcement_comment_lv_item_name_tv);
            holder.timeTv = (TextView)convertView.findViewById(R.id.announcement_comment_lv_item_time_tv);
            holder.contentTv = (TextView)convertView.findViewById(R.id.announcement_comment_lv_item_content_tv);
        } else {
            holder = (HallMainChannelViewHolder) convertView.getTag();
        }


        AnnouncementCommentListItemInfo hallMainChannelLvInfo = contentData.get(position);
        holder.classNameTv.setText(hallMainChannelLvInfo.getAnnounid());
        holder.timeTv.setText(hallMainChannelLvInfo.getCreatetime());
        holder.contentTv.setText(hallMainChannelLvInfo.getCritics());
        return convertView;
    }

    class HallMainChannelViewHolder {
        TextView classNameTv;
        TextView timeTv;
        TextView contentTv;
    }
}
