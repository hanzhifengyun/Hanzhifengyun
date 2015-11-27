package com.fengyun.hanzhifengyun.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fengyun.hanzhifengyun.fragment.LockPatternFragment;

public class LockPatternActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(
						android.R.id.content,
						LockPatternFragment
								.getInstance(getIntent().getStringExtra(LockPatternFragment.LOGIN_TYPE)))
								.commit();
	}

	public static void actionStart(Context context, String type) {
		Intent intent = new Intent(context, LockPatternActivity.class);
		intent.putExtra(LockPatternFragment.LOGIN_TYPE, type);
		context.startActivity(intent);
	}


	public static void actionStartForResult(Activity context, String type, int requestCode) {
		Intent intent = new Intent(context, LockPatternActivity.class);
		intent.putExtra(LockPatternFragment.LOGIN_TYPE, type);
		context.startActivityForResult(intent, requestCode);
	}

}
