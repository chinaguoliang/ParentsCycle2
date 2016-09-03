package com.jgkj.parentscycle.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.PerfectInformationAdapter;
import com.jgkj.parentscycle.bean.GetSevenCowTokenInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetSevenCowTokenPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ActivityManager;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.QiNiuUploadImgManager;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public abstract class BaseActivity extends FragmentActivity {
    private String TAG = "BaseActivity";

    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 20;
    public static final int REQUEST_CODE_PICK_IMAGE = 21;
    public static final int PHOTORESOULT = 22;


    public RequestQueue mQueue;
    private PopupWindow mMsgPopWindow;
    public ProgressDialog mProgressDialog;

    public Dialog mChangePhotoDialog;
    String avatarTempPath;

    PerfectInformationAdapter mPerfectInformationAdapter;
    UploadManager uploadManager;
    public volatile boolean isCancelled = false;

    public abstract void uploadImgFinished(Bitmap bitmap,String uploadedKey);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
        ActivityManager.getInstance().pushActivity(this);
        TAG = this.getPackageName();
        avatarTempPath = Environment.getExternalStorageDirectory().getPath() + "/avatarTemp.jpg";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityManager.getInstance().popActivity(this);
        //CusDialogManager.getInstance().closeCusDailog();
    }

    public void exitApp() {
        ActivityManager.getInstance().popAllActivityExceptOne(getClass());
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //showTopbarPopWindow();
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }





    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyUp(keyCode, event);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mMsgPopWindow != null) {
            mMsgPopWindow.dismiss();
        }
    }



    public void delayFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 300);
    }


    //防止按钮重复点击
    public boolean showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return false;
        }

        mProgressDialog = ProgressDialog.show(this, "", "请稍后", true, false);
        return true;
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }



    //--------上传图片
    public View.OnClickListener changePhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn.getId() == R.id.change_photo_camera_btn) {
                QiNiuUploadImgManager.verifyStoragePermissions(BaseActivity.this);
                getImageFromCamera();
            } else if (btn.getId() == R.id.change_photo_album_btn) {
                QiNiuUploadImgManager.verifyStoragePermissions(BaseActivity.this);
                getImageFromAlbum();
            } else if (btn.getId() == R.id.change_photo_cancel_btn) {
                mChangePhotoDialog.dismiss();
            }
        }
    };

    private void getImageFromCamera() {
        File f = new File(avatarTempPath);
        if (f.exists()) {
            f.delete();
        }
        Intent intent_camera = new Intent();
        intent_camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(avatarTempPath)));
        startActivityForResult(intent_camera, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型

        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public void showChangePhotoDialog() {
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
            if (extras != null) {
                hideSelectDialog();
                requestGetSevenCowToken();
            }
        }
    }

    private void hideSelectDialog() {
        if (mChangePhotoDialog != null) {
            mChangePhotoDialog.dismiss();
        }
    }



    private void uploadImg(String picturePath,String token) {
        showProgressDialog();


        HashMap<String, String> map = new HashMap<String, String>();
        if (UserInfo.isLogined) {
            map.put("phone", UserInfo.loginInfo.getRole().getPhone());
        }

        isCancelled = false;
        uploadManager.put(picturePath, null, token,
                new UpCompletionHandler() {
                    public void complete(String key,
                                         ResponseInfo info, JSONObject res) {
                        LogUtil.i("qiniu", key + ",\r\n " + info
                                + ",\r\n " + res);
                        hideProgressDialog();
                        if(info.isOK()==true){
                            //textview.setText(res.toString());

                            String keyStr = "";
                            try {
                                keyStr = res.getString("key");
                                LogUtil.d(TAG, "upload success  key:" + keyStr);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            uploadImgFinished(BitmapFactory.decodeFile(avatarTempPath),keyStr);

                        } else {
                        }
                        hideProgressDialog();
                    }
                }, new UploadOptions(map, null, false,
                        new UpProgressHandler() {
                            public void progress(String key, double percent) {
                                int progress = (int)(percent * 1000);
                                if(progress==1000){
//                                    progressbar.setVisibility(View.GONE);
                                }
                                LogUtil.d(TAG,"the progress:" + progress);
                            }

                        }, new UpCancellationSignal(){
                    @Override
                    public boolean isCancelled() {

                        return isCancelled;
                    }
                }));
    }


    private void requestGetSevenCowToken() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("bucketname", "testjg");
        GetSevenCowTokenPaser lp = new GetSevenCowTokenPaser();
        NetRequest.getInstance().request(mQueue, new RequestImgTokenResponse(), BgGlobal.GET_SEVEN_COW_TOKEN, requestData, lp);
    }


    public BaseActivity() {
        //断点上传
        String dirPath = "/storage/emulated/0/Download";
        Recorder recorder = null;
        try{
            File f = File.createTempFile("qiniu_xxxx", ".tmp");
            Log.d("qiniu", f.getAbsolutePath().toString());
            dirPath = f.getParent();
            recorder = new FileRecorder(dirPath);
        } catch(Exception e) {
            e.printStackTrace();
        }

        final String dirPath1 = dirPath;
        //默认使用 key 的url_safe_base64编码字符串作为断点记录文件的文件名。
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator(){
            public String gen(String key, File file){
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                String path = key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                Log.d("qiniu", path);
                File f = new File(dirPath1, UrlSafeBase64.encodeToString(path));
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(f));
                    String tempString = null;
                    int line = 1;
                    try {
                        while ((tempString = reader.readLine()) != null) {
//							System.out.println("line " + line + ": " + tempString);
                            Log.d("qiniu", "line " + line + ": " + tempString);
                            line++;
                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        try{
                            reader.close();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }




                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return path;
            }
        };

        Configuration config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .recorder(recorder, keyGen)
                .zone(Zone.zone1)
                .build();

        // 实例化一个上传的实例
        uploadManager = new UploadManager(config);
    }

    class RequestImgTokenResponse implements NetListener{
        @Override
        public void requestResponse(Object obj) {
            NetBeanSuper nbs = (NetBeanSuper)obj;
            hideProgressDialog();
            if (nbs.obj instanceof GetSevenCowTokenInfo) {
                GetSevenCowTokenInfo gct = (GetSevenCowTokenInfo)nbs.obj;
                if (nbs.isSuccess()) {
                    LogUtil.d(TAG,"success get token:" + gct.getToken());
                    uploadImg(avatarTempPath, gct.getToken());
                } else {
                    ToastUtil.showToast(BaseActivity.this, nbs.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        }
    }
}
