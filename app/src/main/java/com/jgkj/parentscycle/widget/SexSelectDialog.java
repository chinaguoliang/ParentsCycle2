package com.jgkj.parentscycle.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.jgkj.parentscycle.R;

/**
 * Created by chen on 16/8/22.
 */
public class SexSelectDialog {
    public interface SexSlectDialogFinish {
        public void finishSlecct(int index);
    }

    public static  void showSexSelectDialog(final Context context,final  SexSlectDialogFinish ssdf) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择性别");
        final String[] sex = {"女","男" };
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(context, "性别为：" + sex[which], Toast.LENGTH_SHORT).show();
                ssdf.finishSlecct(which);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
}
