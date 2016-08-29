package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 16/7/11.
 */
public class TeacherInfoAdapter extends BaseAdapter {

    private List<String> contentData;
    private Context mContext;
    public HashMap<Integer,String> dataMap = new HashMap<Integer,String>();

    public TeacherInfoAdapter(Context context, List<String> data) {
        contentData = data;
        mContext = context;
        //初始化map 内容
        for(int i = 0 ; i < 15 ; i++) {
            dataMap.put(i,"");
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MineViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new MineViewHolder();
            convertView = mInflater.inflate(R.layout.manage_teachers_list_activity_lv_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_desc_tv);
            holder.btmDivider = convertView.findViewById(R.id.hall_mine_fragment_lv_item_btm_divider);
            holder.contentEt = (EditText) convertView.findViewById(R.id.hall_mine_fragment_lv_item_right_content_et);
            holder.contentTv = (TextView)convertView.findViewById(R.id.hall_mine_fragment_lv_item_right_content_tv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        String contentStr = contentData.get(position);
        String names[] =contentStr.split("_");
        if (names.length > 1) {
            holder.contentEt.setText(names[1]);
            holder.contentTv.setText(names[1]);
            dataMap.put(position,names[1]);
        }

        holder.contentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    dataMap.put(position,holder.contentEt.getText().toString());
                }
            }
        });

        holder.contentDescTv.setText(names[0]);
        if (position == (contentData.size() - 1)) {
            holder.btmDivider.setVisibility(View.VISIBLE);
        } else {
            holder.btmDivider.setVisibility(View.GONE);
        }


        if (position == 9 || position == 10 || position == 11) {
            holder.contentEt.setVisibility(View.GONE);
        } else {
            holder.contentEt.setVisibility(View.VISIBLE);
        }

        if (position == 1) {
            holder.contentEt.setVisibility(View.GONE);
            holder.contentTv.setVisibility(View.VISIBLE);
        } else {
            holder.contentTv.setVisibility(View.GONE);
        }

        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;    // 消息未读条数
        View btmDivider;
        EditText contentEt;
        TextView contentTv;
    }
}
