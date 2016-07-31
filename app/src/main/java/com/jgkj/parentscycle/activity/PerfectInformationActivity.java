package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.PerfectInformationAdapter;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class PerfectInformationActivity extends BaseActivity implements View.OnClickListener,NetListener{
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 20;
    public static final int REQUEST_CODE_PICK_IMAGE = 21;
    public static final int PHOTORESOULT = 22;
    public static final int UPLOAD_AVATAR_ERROR = 1;
    public static final int UPLOAD_AVATAR_SUCCESS = 0;
    public static final int DOWNLOAD_AVATAR_SUCCESS = 3;

    @Bind(R.id.perfect_information_activity_lv)
    ListView mListView;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.perfect_information_activity_save_btn)
    Button saveBtn;

    private Dialog mChangePhotoDialog;
    String avatarTempPath;

    Bitmap mPhoto;
    Bitmap mModifyedIcon;
    Bitmap circleBitmap;
    PerfectInformationAdapter mPerfectInformationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfect_information_activity);
        ButterKnife.bind(this);
        mPerfectInformationAdapter = new PerfectInformationAdapter(this, getContentData());
        avatarTempPath = Environment.getExternalStorageDirectory().getPath() + "/avatarTemp.jpg";
        mListView.setAdapter(mPerfectInformationAdapter);
        titleTv.setText("完善资料");
        rightTitleTv.setVisibility(View.GONE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //点击了用户头像
                    showChangePhotoDialog();
                }
            }
        });
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("头像_ ");
        data.add("昵称_ ");
        data.add("帐号_ ");
        data.add("地区_ ");
        data.add("性别_ ");
        data.add("家庭角色_ ");
        data.add("宝宝名字_ ");
        data.add("宝宝性别_ ");
        data.add("宝宝年龄_ ");
        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.perfect_information_activity_save_btn})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == saveBtn) {
            showProgressDialog();
            requestSave();
        }
    }

    private View.OnClickListener changePhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn.getId() == R.id.change_photo_camera_btn) {
                getImageFromCamera();
            } else if (btn.getId() == R.id.change_photo_album_btn) {
                getImageFromAlbum();
            } else if (btn.getId() == R.id.change_photo_cancel_btn) {
                mChangePhotoDialog.dismiss();
            }
        }
    };

    protected void getImageFromCamera() {
        File f = new File(avatarTempPath);
        if (f.exists()) {
            f.delete();
        }
        Intent intent_camera = new Intent();
        intent_camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(avatarTempPath)));
        startActivityForResult(intent_camera, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    public void requestSave() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        HashMap<Integer,String> data = mPerfectInformationAdapter.getData();
        requestData.put("tmpinfoid","1");
        requestData.put("babyname",data.get(0));
        requestData.put("familyrole",data.get(1));
        requestData.put("babyage",data.get(2));
        requestData.put("nickname",data.get(3));
        requestData.put("babysex",data.get(4));
        requestData.put("sex",data.get(5));
        requestData.put("familyrole","2");
        requestData.put("babyname","2");
        requestData.put("babysex","2");
        requestData.put("region","2");
        requestData.put("fmbg","http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg");
        requestData.put("account","chen");
        requestData.put("sex","1");
        requestData.put("headportrait","http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg");
        requestData.put("teachername","2");
        requestData.put("teachersex","4");
        requestData.put("nationality","3");
        requestData.put("birthdate","3");
        requestData.put("phone","15810697038");
        requestData.put("kbwx","3");
        requestData.put("kbqq","5");
        requestData.put("classid","5");
        requestData.put("onthejob","6");
        GetVerifyPhoneNumPaser lp = new GetVerifyPhoneNumPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_SAVE, requestData, lp);
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型

        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void showChangePhotoDialog() {
        mChangePhotoDialog = new Dialog(this, R.style.DialogTheme);
        mChangePhotoDialog.getWindow().setWindowAnimations(
                R.style.dialogWindowAnim);

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_change_photo, null);
        Button cameraBtn = (Button) contentView
                .findViewById(R.id.change_photo_camera_btn);
        Button albumBtn = (Button) contentView
                .findViewById(R.id.change_photo_album_btn);
        Button cancelBtn = (Button) contentView
                .findViewById(R.id.change_photo_cancel_btn);

        cameraBtn.setOnClickListener(changePhotoListener);
        albumBtn.setOnClickListener(changePhotoListener);
        cancelBtn.setOnClickListener(changePhotoListener);

        mChangePhotoDialog.setContentView(contentView);
        mChangePhotoDialog.setCanceledOnTouchOutside(true);
        mChangePhotoDialog.show();

        WindowManager.LayoutParams params = mChangePhotoDialog.getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mChangePhotoDialog.getWindow().setAttributes(params);
    }

    File tempFile;
    public void startPhotoZoom(Uri uri) {
        // 此处就是调用了android系统给定的切图功能
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        tempFile=new File(avatarTempPath);
        if (!tempFile.exists()) {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra("output", Uri.fromFile(tempFile));

        startActivityForResult(intent, PHOTORESOULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data == null) {
                hideSelectDialog();
                return;
            }
            Uri uri = data.getData();
            if (uri != null) {
                startPhotoZoom(uri);
                return;
            } else {
                ToastUtil.showToast(getApplicationContext(), "获取图片错误", Toast.LENGTH_LONG);

            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            File f = new File(avatarTempPath);
            if (!f.exists()) {
                hideSelectDialog();
                return;
            }
            startPhotoZoom(Uri.fromFile(f));
            return;

//			ToastUtil.showToast(getApplicationContext(), "获取图片错误", Toast.LENGTH_LONG)
//					;
        } else if (requestCode == PHOTORESOULT) {
            if (data == null) {
                hideSelectDialog();
                return;
            }
            Bundle extras = data.getExtras();
//			data.get
            if (extras != null) {
                Bitmap bitmap = extras.getParcelable("data");
                File newfile = new File(avatarTempPath);
                String path = newfile.getPath();
                int degree = UtilTools.readPictureDegree(path);
                mModifyedIcon = UtilTools.rotaingImageView(degree, bitmap);

//				if (photo != null) {
//					headIv.setImageBitmap(photo);
//				}

                File file = null;
                try {
                    file = new File(avatarTempPath);// 创建文件
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    mModifyedIcon.compress(Bitmap.CompressFormat.JPEG, 60, bos);

                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (file != null) {
//                    uploadAvatar(file);
                }
                hideSelectDialog();
//                addAImgToActivity(mModifyedIcon);
                mPerfectInformationAdapter.setUserIcon(mModifyedIcon);
                mPerfectInformationAdapter.notifyDataSetChanged();
            }
        }

    }

    private void hideSelectDialog() {
        if (mChangePhotoDialog != null) {
            mChangePhotoDialog.dismiss();
        }
    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        hideProgressDialog();
//        if (nbs.isSuccess()) {
//
//        }

    }
}
