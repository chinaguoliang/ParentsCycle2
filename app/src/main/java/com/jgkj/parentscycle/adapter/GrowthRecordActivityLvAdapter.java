package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.GrowthRecordItemInfo;

import java.util.List;

/**
 * Created by chen on 16/7/18.
 */
public class GrowthRecordActivityLvAdapter extends BaseAdapter {
    private List<GrowthRecordItemInfo>contentData;
    private Context mContext;
    private int showSecondPosition = -1;
    public GrowthRecordActivityLvAdapter(Context context, List<GrowthRecordItemInfo> data){
        contentData = data;
        mContext = context;
    }

    public void setCurrSelPos(int position) {
        showSecondPosition = position;
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
            convertView = mInflater.inflate(R.layout.growth_record_activity_lv_item, null);
            convertView.setTag(holder);
            holder.timeTv = (TextView)convertView.findViewById(R.id.growth_record_activity_lv_item_time_tv);
            holder.nameTv = (TextView)convertView.findViewById(R.id.growth_record_activity_name_tv);
            holder.contentTv = (TextView)convertView.findViewById(R.id.growth_record_activity_content_tv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        GrowthRecordItemInfo gri = contentData.get(position);
        holder.timeTv.setText(gri.getPublisherdate());
        holder.nameTv.setText(gri.getPublishername());
        holder.contentTv.setText(gri.getPublishertext());
        return convertView;
    }

    class MineViewHolder {
        TextView timeTv;	// 消息未读条数
        TextView nameTv;
        TextView contentTv;
    }
}
