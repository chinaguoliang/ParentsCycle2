package com.jgkj.parentscycle.utils;


import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class ButtonTimeCount extends CountDownTimer {
	private TextView sendButton;
	public ButtonTimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
	}
	
	public void setSendButton(TextView sendBtn) {
		sendButton = sendBtn;
	}

	@Override
	public void onFinish() {// 计时完毕时触发
//		sendButton.setPadding(0, 0, 0, 0);
//		sendButton.setBackgroundResource(R.drawable.register_get_verify_num);
		sendButton.setText("获取验证码");
		sendButton.setEnabled(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
//		sendButton.setPadding(0, 0, 0, 0);
//		sendButton.setBackgroundResource(R.drawable.register_get_verify_num_gray);
		sendButton.setEnabled(false);
		long count = millisUntilFinished / 1000;
		if (count < 10) {
			sendButton.setText("　" + count + "秒后重发");
		} else {
			sendButton.setText(count + "秒后重发");
		}
	}
	
	
}