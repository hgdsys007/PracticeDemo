package com.lzz.studydemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.lzz.studydemo.R;

public class Pull2finishActivity extends AppCompatActivity {

	private GestureDetector gestureDetector;

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, Pull2finishActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
		// 在 super.onCreate(savedInstanceState) 之前调用该方法
		super.onCreate(savedInstanceState);
		if (getLayoutId() != 0) {
			setContentView(getLayoutId());
		}
		initView();
	}

	protected int getLayoutId() {
		return R.layout.activity_pull_finish;
	}

	protected void initView() {

		// if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
		// // System.out.println("水平方向移动距离过大");
		// return true;
		// }
		// System.out.println("手指移动的太慢了");
		// 手势向下 down
		//在此处控制关闭
		// 手势向上 up
		gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				System.out.println("手指移动的太慢了");
				// if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
				// // System.out.println("水平方向移动距离过大");
				// return true;
				// }
				if (Math.abs(velocityY) < 100) {
					// System.out.println("手指移动的太慢了");
					return true;
				}

				// 手势向下 down
				if ((e2.getRawY() - e1.getRawY()) > 200) {
					finish();//在此处控制关闭
					return true;
				}
				// 手势向上 up
				if ((e1.getRawY() - e2.getRawY()) > 200) {
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
