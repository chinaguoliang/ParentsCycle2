package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Environment;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/20.
 */
public class BabyShowActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.baby_show_activity_take_photo_iv)
    ImageView takePhotoIcon;

    @Bind(R.id.baby_show_activity_take_photo_ll)
    LinearLayout takePhotoLl;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    private Dialog mChangePhotoDialog;
    String avatarTempPath;

    Bitmap mPhoto;
    Bitmap mModifyedIcon;
    Bitmap circleBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_show_activity);
        ButterKnife.bind(this);
        avatarTempPath = Environment.getExternalStorageDirectory().getPath() + "/avatarTemp.jpg";
    }

    @OnClick({R.id.baby_show_activity_take_photo_iv,R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == takePhotoIcon) {
            showChangePhotoDialog();
        } else if (v == backIv) {
            finish();
        }
    }

//    private View.OnClickListener changePhotoListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Button btn = (Button) v;
//            if (btn.getId() == R.id.change_photo_camera_btn) {
//                getImageFromCamera();
//            } else if (btn.getId() == R.id.change_photo_album_btn) {
//                getImageFromAlbum();
//            } else if (btn.getId() == R.id.change_photo_cancel_btn) {
//                mChangePhotoDialog.dismiss();
//            }
////			else if(btn.getId() == R.id.dialog_sure_btn){
////				Dialog mTipDialog = app.getChangeDialog();
////				mTipDialog.dismiss();
////			}
//        }
//    };

//    private void showChangePhotoDialog() {
//        mChangePhotoDialog = new Dialog(this, R.style.DialogTheme);
//        mChangePhotoDialog.getWindow().setWindowAnimations(
//                R.style.dialogWindowAnim);
//
//        View contentView = LayoutInflater.from(this).inflate(
//                R.layout.dialog_change_photo, null);
//        Button cameraBtn = (Button) contentView
//                .findViewById(R.id.change_photo_camera_btn);
//        Button albumBtn = (Button) contentView
//                .findViewById(R.id.change_photo_album_btn);
//        Button cancelBtn = (Button) contentView
//                .findViewById(R.id.change_photo_cancel_btn);
//
//        cameraBtn.setOnClickListener(changePhotoListener);
//        albumBtn.setOnClickListener(changePhotoListener);
//        cancelBtn.setOnClickListener(changePhotoListener);
//
//        mChangePhotoDialog.setContentView(contentView);
//        mChangePhotoDialog.setCanceledOnTouchOutside(true);
//        mChangePhotoDialog.show();
//
//        WindowManager.LayoutParams params = mChangePhotoDialog.getWindow()
//                .getAttributes();
//        params.gravity = Gravity.BOTTOM;
//        params.width = UtilTools.SCREEN_WIDTH;
//        mChangePhotoDialog.getWindow().setAttributes(params);
//
//    }

//    protected void getImageFromCamera() {
//        File f = new File(avatarTempPath);
//        if (f.exists()) {
//            f.delete();
//        }
//        Intent intent_camera = new Intent();
//        intent_camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(avatarTempPath)));
//        startActivityForResult(intent_camera, REQUEST_CODE_CAPTURE_CAMEIA);
//    }
//
//    protected void getImageFromAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");// 相片类型
//
//        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
//    }
//
//    File tempFile;
//    public void startPhotoZoom(Uri uri) {
//        // 此处就是调用了android系统给定的切图功能
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 100);
//        intent.putExtra("outputY", 100);
//        intent.putExtra("return-data", true);
//        tempFile=new File(avatarTempPath);
//        if (!tempFile.exists()) {
//            try {
//                tempFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        intent.putExtra("output", Uri.fromFile(tempFile));
//
//        startActivityForResult(intent, PHOTORESOULT);
//    }
//
//    private void hideSelectDialog() {
//        if (mChangePhotoDialog != null) {
//            mChangePhotoDialog.dismiss();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
//            if (data == null) {
//                hideSelectDialog();
//                return;
//            }
//            Uri uri = data.getData();
//            if (uri != null) {
//                startPhotoZoom(uri);
//                return;
//            } else {
//                ToastUtil.showToast(getApplicationContext(), "获取图片错误", Toast.LENGTH_LONG);
//
//            }
//        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
//            File f = new File(avatarTempPath);
//            if (!f.exists()) {
//                hideSelectDialog();
//                return;
//            }
//            startPhotoZoom(Uri.fromFile(f));
//            return;
//
////			ToastUtil.showToast(getApplicationContext(), "获取图片错误", Toast.LENGTH_LONG)
////					;
//        } else if (requestCode == PHOTORESOULT) {
//            if (data == null) {
//                hideSelectDialog();
//                return;
//            }
//            Bundle extras = data.getExtras();
////			data.get
//            if (extras != null) {
//                Bitmap bitmap = extras.getParcelable("data");
//                File newfile = new File(avatarTempPath);
//                String path = newfile.getPath();
//                int degree = UtilTools.readPictureDegree(path);
//                mModifyedIcon = UtilTools.rotaingImageView(degree, bitmap);
//
////				if (photo != null) {
////					headIv.setImageBitmap(photo);
////				}
//
//                File file = null;
//                try {
//                    file = new File(avatarTempPath);// 创建文件
//                    if (!file.exists()) {
//                        file.createNewFile();
//                    }
//
//                    BufferedOutputStream bos = new BufferedOutputStream(
//                            new FileOutputStream(file));
//                    mModifyedIcon.compress(Bitmap.CompressFormat.JPEG, 60, bos);
//
//                    bos.flush();
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (file != null) {
////                    uploadAvatar(file);
//
//                }
//                hideSelectDialog();
//                addAImgToActivity(mModifyedIcon);
//            }
//        }



//		else if(resultCode == UApplication.CHANGEPWD_RESULT_CODE){
//			this.finish();
//		}else if(resultCode == UApplication.BIND_MOBILE_CODE){
//			phone = data.getStringExtra("phone");
//			bindPhoneBtn.setText(R.string.user_bind_mobile_already);
//		}else if(resultCode == UApplication.UNBIND_MOBILE_CODE){
//			phone = "";
//			bindPhoneBtn.setText(R.string.user_bind_mobile);
//		}else if(resultCode == UApplication.RECHARGE_PERSON_CODE){
//			LogUtil.d(TAG, "instance loading dialog");
//			loadingDialog = ProgressDialog.show(this, "", "login...", true,false);
//			getUserInfo();
//		}
//    }

    private void addAImgToActivity(Bitmap img) {
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(img);
        iv.setPadding(10,10,10,10);
        takePhotoLl.addView(iv,0);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap) {

    }
}
