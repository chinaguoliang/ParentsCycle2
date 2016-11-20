package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.json.TeacherListPaser;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.videogo.RequestAccessToken;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/15.
 */
public class TestNetActivity extends BaseActivity implements View.OnClickListener,NetListener {
    @Bind(R.id.test_net_activity_btn)
    Button testNetBtn;

    @Bind(R.id.test_net_activity_iv)
    ImageView testIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_net_activity);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.test_net_activity_btn})
    @Override
    public void onClick(View v) {
        if (v == testNetBtn) {
            //requestBabyAskForLeave();
//            startActivity(new Intent(this, PublishFoodListActivity.class));
            //requestTeachersList();
//            requestClassListBySchoolId();
//            requestCheckAttendance();
//            requestCourseList();
            //requestPublishFoodList();
            //requestCourseList();
//            requestFoodList();
            //requestCourseList();
            //requestFoodList();
//            requestPublishFoodList();
//            requestCourseList();
//            requestAnnouncementDetailList();
            //requestAnnouncementList();
//            requestAnnouncementDetailList();

//             RequestAccessToken.getAccessToken();
            //testGetAccessToken();
            //requestQuerySchoolList();
            //requestBabyGrowthList();
            //requestSearchSchoolList();
            requestPerfectBabyInfo();
        }
    }

    private void testGetAccessToken() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://open.ys7.com/api/lapp/token/get",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result","request success:" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("result","request fail");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String, String> map = new HashMap <String, String>();
                map.put("appKey","8ff0d3e7aab5485195fd7ddcb0a33934");
                map.put("appSecret","6741c50a996dd8a185a2ceaf06f28be2");
                return map;
            }
        };
        mQueue.add(request);
    }


    //所有文章公共转发
    public void requestPublicArticleDistribuction() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("wbid", "1");
        requestData.put("osid","1");
        requestData.put("role","1");
        requestData.put("attype","1");
        requestData.put("chennl","1");
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLIC_ARTICLE_DISTRIBUCTION, requestData, lp);
    }


    //所有点赞公共接口
    public void requestSetGood() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("wbid", "1");
        requestData.put("osid","1");
        requestData.put("role","1");
        requestData.put("attype","1");
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.SET_GOOD, requestData, lp);
    }

    //课程表发布   (不管有多少节数，必须有多少课数)
    public void requestPublishCourse() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("weeknum", "1");
        requestData.put("festivals","1");
        requestData.put("course","语文");
        requestData.put("clssids","1");
        requestData.put("osperion","小明");
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLISH_COURSE, requestData, lp);
    }

    //公告列表
    private void requestAnnouncementList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("id", UserInfo.loginInfo.getRole().getId());
        requestData.put("page", "1");
        requestData.put("rows", "10");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_LIST, requestData, lp);
    }

    //食谱发布
    public void requestPublishFoodList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("weeknum", "1");
        requestData.put("meal","晚餐");
        requestData.put("foodimgs","http://www.1.png");
        requestData.put("fooddescription","黄色");
        requestData.put("classid","1");
        requestData.put("shoolid","1");
        requestData.put("osperion","王宝强");

        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLISH_FOOD_LIST, requestData, lp);
    }

    // 父母圈发帖
    public void requestParentsCycleSendArticle() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1");
        requestData.put("address","北京");
        requestData.put("topic","马荣");
        requestData.put("topictext","婊子本色");
        requestData.put("topicimg","1.png");
        requestData.put("topictype","1");

        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PARENTS_CYCLE_SEND_ARTICLE, requestData, lp);
    }

    // 家长完善个人信息
    public void requestPerfectParentsInfo() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("headportrait", "1.png");
        requestData.put("nickname","北京");
        requestData.put("account","3625");
        requestData.put("region","21323");
        requestData.put("sex","1");
        requestData.put("familyrole","1");

        requestData.put("babyname","1");
        requestData.put("babysex","1");
        requestData.put("babyage","236");
        requestData.put("fmbg","1.png");
        requestData.put("tmpinfoid", "4");
        requestData.put("isdaren","0");

        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PERFECT_PARENTS_INFO, requestData, lp);
    }

    // 宝宝信息完善
    public void requestPerfectBabyInfo() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("age", "5");
        requestData.put("bgurl","1");
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("birthdate",date);
        SimpleDateFormat sdf1 =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(System.currentTimeMillis());
        requestData.put("bobyenrollmentdate",date1);
        requestData.put("iocurl","1");
        requestData.put("isshool","1");

        requestData.put("nickname","1");
        requestData.put("ostmpid","66");
        requestData.put("sex","1");
        requestData.put("username","liangliang");

        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PERFECT_BYBY_INFO, requestData, lp);
    }


    // 宝宝成长记录发布
    public void requestPublishBabyGrowRecord() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("babyid", "80");
        requestData.put("publisher","123");
        requestData.put("publishername","1");
        requestData.put("publishertext","1");
        requestData.put("bobypublisherdate",date);
        requestData.put("publisherimge","321");



        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLISH_BABY_GROW_RECORD, requestData, lp);
    }


    // 宝宝列表
    public void requestBabyList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows", "0");
        requestData.put("page","2");
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.BABY_LIST, requestData, lp);
    }


    // 家长版-宝宝请假添加     (此接口教师也可以使用，看具体需求，如果是签到直接改变类型，内容可不传)
    public void requestBabyAskForLeave() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("babyid", "1");
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("techerstarttime",date);
        requestData.put("asktype","1");
        requestData.put("askday","10");
        requestData.put("asktext","345435");
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.BABY_ASK_LEAVE_ADD, requestData, lp);
    }


    // 教师版请假修改
    public void requestAskForLeaveForTeacherVersion() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("askday", "1");
        requestData.put("asktext","20160601");
        requestData.put("asktype","22");
        requestData.put("babyid","12312");
        requestData.put("schoolid","555");
        requestData.put("classid","999");
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("dateday",date);
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.ASK_FOR_LEAVE_MODIFY_FOR_TEACHER_VERSION, requestData, lp);
    }

    //考勤日历 颜色列表  根据类型显示 各种颜色
    public void requestCheckAttendance() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("page", "1");
        requestData.put("rows","2");
        requestData.put("createdatetimeStart","2015");
        requestData.put("createdatetimeEnd","2016");
        requestData.put("classid","1");
        requestData.put("schoolid","1");
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(System.currentTimeMillis());
        //requestData.put("starttime",date);
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.CHECK_ATTENDANCE, requestData, lp);
    }


    //请求教师列表
    public void requestTeachersList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        TeacherListPaser lp = new TeacherListPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHERS_LIST, requestData, lp);
    }

    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    //查询食谱列表
    private void requestFoodList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        requestData.put("classid", "62");
        requestData.put("weeknum", "1");
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.QUERY_FOOD_LIST, requestData, lp);
    }


    //查询课程列表
    private void requestCourseList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        requestData.put("weeknum","1");
        requestData.put("clssids", "62");
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.QUERY_COURSE_LIST, requestData, lp);
    }


    //查询课程列表
    private void requestAnnouncementDetailList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        requestData.put("announid","9");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_DETAIL, requestData, lp);
    }


    //获取学校列表
    private void requestQuerySchoolList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.QUERY_SCHOOLS_LIST, requestData, lp);
    }

    //宝宝成长记录查询列表
    private void requestBabyGrowthList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.GET_BABY_GROWTH_RECORD_LIST, requestData, lp);
    }

    //学校查询列表
    private void requestSearchSchoolList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        requestData.put("schoolname","山西");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.QUERY_SCHOOL_LIST_BY_SCHOOL_NAME, requestData, lp);
    }



    @Override
    public void requestResponse(Object obj) {

    }


    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }
}
