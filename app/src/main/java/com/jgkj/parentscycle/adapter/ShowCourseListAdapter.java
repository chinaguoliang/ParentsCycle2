package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ShowCourseListInfoItem;

import java.util.List;

/**
 * Created by chen on 16/9/18.
 */
public class ShowCourseListAdapter extends BaseAdapter {

    private List<ShowCourseListInfoItem> contentData;
    private Context mContext;
    public ShowCourseListAdapter(Context context, List<ShowCourseListInfoItem> data){
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
            convertView = mInflater.inflate(R.layout.corse_activity_course_item, null);
            convertView.setTag(holder);
            holder.corseName = (TextView)convertView.findViewById(R.id.corse_activity_corse_item_corse_num_tv);
            holder.courseDel = (ImageView)convertView.findViewById(R.id.corse_activity_corse_item_del_iv);
            holder.courseContent = (EditText)convertView.findViewById(R.id.corse_activity_corse_item_corse_name_et);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        ShowCourseListInfoItem scli = contentData.get(position);
        holder.courseContent.setText(scli.getCourse());
        holder.corseName.setText("第"+ scli.getFestivals() + "节课");
        holder.courseContent.setEnabled(false);
        holder.courseDel.setVisibility(View.INVISIBLE);
        return convertView;
    }

    class MineViewHolder {
       TextView corseName;
        EditText courseContent;
        ImageView courseDel;
    }
}
