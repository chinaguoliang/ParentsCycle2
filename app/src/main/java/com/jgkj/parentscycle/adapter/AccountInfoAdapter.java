package com.jgkj.parentscycle.adapter;

import android.content.Context;
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

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoAdapter extends BaseAdapter {
    private List<String> contentData;
    private Context mContext;
    public HashMap<Integer,String> dataMap = new HashMap<Integer,String>();

    public AccountInfoAdapter(Context context, List<String> data) {
        contentData = data;
        mContext = context;
        //初始化map 内容
        for(int i = 0 ; i < 15 ; i++) {
            dataMap.put(i,"");
        }
    }

    public HashMap<Integer,String> getData() {
        return dataMap;
    }

    public List<String> getList() {
        return contentData;
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
            convertView = mInflater.inflate(R.layout.account_info_activity_lv_item, null);
            convertView.setTag(holder);
            holder.contentDescTv = (TextView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_name_tv);
            holder.rightArrowIv = (ImageView) convertView.findViewById(R.id.hall_mine_fragment_lv_item_right_arrow_iv);
            holder.contentEt = (EditText)convertView.findViewById(R.id.hall_mine_fragment_lv_item_content_et);
            holder.contentTv = (TextView)convertView.findViewById(R.id.hall_mine_fragment_lv_item_content_tv);
        } else {
            holder = (MineViewHolder) convertView.getTag();
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


        String contentStr = contentData.get(position);
        String names[] = contentStr.split("_");
        holder.contentDescTv.setText(names[0]);

        if (names.length > 1) {
            String tempNames = names[1].trim();
            if (TextUtils.isEmpty(tempNames)) {
                holder.contentEt.setText(dataMap.get(position));
                holder.contentTv.setText(dataMap.get(position));
            } else {
                holder.contentTv.setText(names[1]);
                holder.contentEt.setText(names[1]);
            }
        }



        if (position == 3 || position == 4 || position == 6 || position == 7 || position == 10) {
            holder.contentEt.setVisibility(View.GONE);
            holder.contentTv.setVisibility(View.VISIBLE);
        } else {
            holder.contentEt.setVisibility(View.VISIBLE);
            holder.contentTv.setVisibility(View.GONE);
        }

        if (position == 9) {
            //宝宝年龄
            holder.contentEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            holder.contentEt.setInputType(InputType.TYPE_NULL);
        }

        return convertView;
    }

    class MineViewHolder {
        TextView contentDescTv;    // 消息未读条数
        TextView conentNameTv;
        ImageView rightArrowIv;
        EditText contentEt;
        TextView contentTv;
    }
}
