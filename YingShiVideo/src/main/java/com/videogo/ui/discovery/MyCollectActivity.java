package com.videogo.ui.discovery;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.videogo.exception.BaseException;
import com.videogo.openapi.EZFavoriteSquareVideo;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZSquareVideo;
import com.videogo.widget.TitleBar;

import java.util.List;

public class MyCollectActivity extends FragmentActivity implements SquareVideoListFragment.OnDataProcess {
    private TitleBar titleBar;
    private EZOpenSDK mEZSdk = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.videogo.open.R.layout.activity_my_collect);
        titleBar = (TitleBar) findViewById(com.videogo.open.R.id.titleBar);
        titleBar.setTitle(com.videogo.open.R.string.my_collect);
		if (savedInstanceState != null) {
			return;
		}
		SquareVideoListFragment fragment = SquareVideoListFragment.newInstance(null, true);
		fragment.setOnDataProcess(this);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(com.videogo.open.R.id.container, fragment);
		transaction.commit();
		mEZSdk = EZOpenSDK.getInstance();
	}

	@Override
	public List<EZSquareVideo> loadMore(int pageStart, int pageSize,
										Object... objects) throws BaseException {
//		List<SquareVideoInfo> result = EzvizAPI.getInstance().getSquareVideoFavorite(pageStart, pageSize);
//		if (result != null) {
//			for (SquareVideoInfo videoInfo : result) {
//				videoInfo.setCollected(true);
//			}
//		}
//		return result;
		return null;
	}

	@Override
	public List<EZFavoriteSquareVideo> loadMoreFavorite(int pageStart,
														int pageSize, Object... objects) throws BaseException {
		List<EZFavoriteSquareVideo> result = mEZSdk.getFavoriteSquareVideoList(pageStart, pageSize);
		if (result != null) {
			for (EZFavoriteSquareVideo videoInfo : result) {
//				videoInfo.setCollected(true);
			}
		}
		return result;

	}
}
