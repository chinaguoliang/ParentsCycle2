package com.jgkj.parentscycle.adapter;

import android.widget.BaseAdapter;


        import android.app.Activity;
        import android.content.Context;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.MediaController;
        import android.widget.TextView;
        import android.widget.VideoView;

        import com.jgkj.parentscycle.R;
        import com.jgkj.parentscycle.bean.HallMainChannelLvInfo;

        import java.util.List;

/**
 * Created by chen on 16/7/23.
 */
public class VideoOpenCourseActivityAdapter extends BaseAdapter {
    private List<HallMainChannelLvInfo> contentData;
    private Context mContext;
    public VideoOpenCourseActivityAdapter(Context context, List<HallMainChannelLvInfo> data){
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
            convertView = mInflater.inflate(R.layout.hall_main_channel_fragment_layout_lv_item, null);
            convertView.setTag(holder);
            holder.classNameTv = (TextView)convertView.findViewById(R.id.hall_main_channel_fragment_layout_lv_item_class_name_tv);
            holder.onLineStatusTv = (TextView)convertView.findViewById(R.id.hall_main_channel_fragment_layout_lv_item_is_online_tv);

        } else {
            holder = (HallMainChannelViewHolder) convertView.getTag();
        }


        HallMainChannelLvInfo hallMainChannelLvInfo = contentData.get(position);
        holder.classNameTv.setText(hallMainChannelLvInfo.getClassName());
        holder.onLineStatusTv.setText(hallMainChannelLvInfo.getOnLineStatus());
        return convertView;
    }

    class HallMainChannelViewHolder {
        TextView classNameTv;
        TextView onLineStatusTv;

    }
}
