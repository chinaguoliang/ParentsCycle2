package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.widget.CircularImage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 16/7/24.
 */
public class PerfectInformationAdapter extends BaseAdapter {
    private static final String TAG = "PerfectInformationAdapter";
    private List<String> contentData;
    private Context mContext;
    private Bitmap userIcon;
    public HashMap<Integer,String> dataMap = new HashMap<Integer,String>();
    public PerfectInformationAdapter(Context context, List<String> data){
        contentData = data;
        mContext = context;

        //初始化map 内容
        for(int i = 0 ; i < 15 ; i++) {
            dataMap.put(i,"");
        }
    }

    public void setPositionData(int position,String data) {
        contentData.set(position,data);
        this.notifyDataSetChanged();
    }

    public HashMap<Integer,String> getData() {
        return dataMap;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MineViewHolder holder;
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
            holder.mContentEt = (EditText)convertView.findViewById(R.id.perfect_information_activity_lv_item_et);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        holder.mContentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    dataMap.put(position,holder.mContentEt.getText().toString());
                    LogUtil.d(TAG,"get data:" + dataMap.get(position));
                }

            }
        });

        String contentStr = contentData.get(position);
        String names[] =contentStr.split("_");
        if (names.length > 1) {
            holder.contentDescTv.setText(names[0]);
            holder.conentNameTv.setText(names[1]);
            //dataMap.put(position,names[1]);
        }


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
            holder.mContentEt.setVisibility(View.GONE);
        } else {

            holder.userIconIv.setVisibility(View.GONE);
            if (position == 4 || position == 7) {

            } else {
                holder.mContentEt.setVisibility(View.VISIBLE);
            }

        }

        if (position == 8) {
            holder.mContentEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }


        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;	// 消息未读条数
        TextView conentNameTv;
        ImageView rightArrowIv;
        CircularImage userIconIv;
        EditText mContentEt;
        View grayLine;
    }
}
