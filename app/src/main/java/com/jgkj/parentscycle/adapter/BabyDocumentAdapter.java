package com.jgkj.parentscycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import java.util.List;

/**
 * Created by chen on 16/7/18.
 */
public class BabyDocumentAdapter extends BaseAdapter {
    private List<String> contentData;
    private Context mContext;
    private int showSecondPosition = -1;
    public BabyDocumentAdapter(Context context, List<String> data){
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
            convertView = mInflater.inflate(R.layout.baby_document_lv_item, null);
            convertView.setTag(holder);
            holder.babyNameTv = (TextView)convertView.findViewById(R.id.baby_document_lv_item_baby_name_tv);
            holder.secondMenu = (LinearLayout)convertView.findViewById(R.id.baby_document_lv_item_second_menu_ll);
        } else {
            holder = (MineViewHolder) convertView.getTag();
        }

        holder.babyNameTv.setText(contentData.get(position));
        if (showSecondPosition == position) {
            holder.secondMenu.setVisibility(View.VISIBLE);
        } else {
            holder.secondMenu.setVisibility(View.GONE);
        }



        return convertView;
    }

    class MineViewHolder {
        TextView babyNameTv;	// 消息未读条数
        LinearLayout secondMenu;
    }
}
