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
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chen on 16/8/3.
 */
public class ModifyPermissionDialogLvAdapter extends BaseAdapter {

    private List<String> contentData;
    private Context mContext;

    public ModifyPermissionDialogLvAdapter(Context context, List<String> data) {
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
            convertView = mInflater.inflate(R.layout.modify_permission_dialog_lv_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView) convertView.findViewById(R.id.modify_permission_dialog_lv_item_text_tv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        holder.contentDescTv.setText(contentData.get(position));

        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;    // 消息未读条数
    }
}
