package com.fengyun.hanzhifengyun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyun.hanzhifengyun.R;
import com.fengyun.hanzhifengyun.activity.LockPatternActivity;
import com.fengyun.hanzhifengyun.activity.MainActivity;
import com.fengyun.hanzhifengyun.util.LockPatternUtil;
import com.fengyun.hanzhifengyun.view.LockPatternView;

public class LockPatternFragment extends BaseFragment implements
		LockPatternView.OnPatterChangeListener, View.OnClickListener {
	public static final String LOGIN_TYPE_SETTINGS = "settings";
	public static final String LOGIN_TYPE_CHECK = "check";
	public static final String LOGIN_TYPE_UPDATE = "update";
	public static final String LOGIN_TYPE_CLOSE = "close";
	public static final String LOGIN_TYPE = "type";
	private TextView titleTextView;
	private LockPatternView lockPatternView;
	private String firstPassword;//用来保存第一次设置的密码

	private Button saveButton;
	private Button resetButton;
	private LinearLayout buttonLayout;


	public static LockPatternFragment getInstance(String type)
	{
		LockPatternFragment lockPatternFragment = new LockPatternFragment();
		Bundle bundle = new Bundle();
		bundle.putString(LOGIN_TYPE, type);
		lockPatternFragment.setArguments(bundle);

		return lockPatternFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View contentView = inflater.inflate(R.layout.fragment_lockpattern,
				container, false);
		initView(contentView);
		if (getArguments() != null)
		{
			if (LOGIN_TYPE_SETTINGS
					.equals(getArguments().getString(LOGIN_TYPE)))// 如果是设置密码
			{
				titleTextView.setText(getString(R.string.first_setting));
			}
			else
			{
				titleTextView.setText(getString(R.string.setting_password_saved));
			}
		}
		initEvent();

		return contentView;
	}

	private void initEvent()
	{
		lockPatternView.setPatterChangeListener(this);
		resetButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
	}

	private void initView(View contentView)
	{
		titleTextView = (TextView) contentView.findViewById(R.id.textView);
		lockPatternView = (LockPatternView) contentView
				.findViewById(R.id.lockPatternView);

		resetButton = (Button) contentView.findViewById(R.id.fragment_lockPattern_button_reset);
		saveButton = (Button) contentView.findViewById(R.id.fragment_lockPattern_button_save);
		buttonLayout = (LinearLayout) contentView.findViewById(R.id.fragment_lockPattern_layout_button);
		buttonLayout.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onPatterChange(String password)
	{
		if (getArguments() != null)
		{
			if (LOGIN_TYPE_CHECK.equals(getArguments().getString(LOGIN_TYPE)))// 如果是密码检查
			{
				if (password.equals(LockPatternUtil.getLockPatternPassword(getActivity())))// 密码正确
				{
					intent2Activity(MainActivity.class);
					getActivity().finish();
				}
				else
				// 密码错误
				{
					showShortToast(getString(R.string.password_error));
					lockPatternView.drawError();
				}
			}
			else if (LOGIN_TYPE_CLOSE.equals(getArguments().getString(LOGIN_TYPE))){//关闭密码功能
				if (password.equals(LockPatternUtil.getLockPatternPassword(getActivity())))// 密码正确
				{
					LockPatternUtil.closeLockPattern(getActivity());
					getActivity().setResult(getActivity().RESULT_OK);
					getActivity().finish();
				}
				else
				// 密码错误
				{
					showShortToast(getString(R.string.password_error));
					lockPatternView.drawError();
				}
			}
			else if (LOGIN_TYPE_SETTINGS.equals(getArguments().getString(LOGIN_TYPE)))// 如果是密码设置
			{
				if (TextUtils.isEmpty(firstPassword)) //检查是否是第一次设置密码
				{
					if (!TextUtils.isEmpty(password))  //第一次设置成功
					{
						lockPatternView.resetPointList();
						firstPassword = password;
						titleTextView.setText(R.string.setting_one_more_time);
						buttonLayout.setVisibility(View.VISIBLE);
						saveButton.setEnabled(false);
					}
					else
					{
						lockPatternView.drawError();
						showShortToast(getString(R.string.password_too_short));
					}
				}
				else  //第二次设置密码
				{
					if (!TextUtils.isEmpty(password))
					{
						if (password.equals(firstPassword))//两次密码相同
						{
							saveButton.setEnabled(true);
							lockPatternView.setIsSettingSuccess(true);
						}
						else //第二次密码和第一次不同,则重新设置
						{
							showShortToast(getString(R.string.setting_failture));
							resetLockPattern();
						}
					}
					else
					{
						lockPatternView.drawError();
						showShortToast(getString(R.string.password_too_short));
					}
				}

			}
			else //密码修改
			{
				if (password.equals(LockPatternUtil.getLockPatternPassword(getActivity())))// 原密码正确,跳转到设置密码页面
				{
					LockPatternActivity.actionStart(getActivity(), LOGIN_TYPE_SETTINGS);
					getActivity().finish();
				}
				else
				// 密码错误
				{
					showShortToast(getString(R.string.password_error));
					lockPatternView.drawError();
				}
			}
		}
	}

	private void resetLockPattern() {
		lockPatternView.resetPointList();
		buttonLayout.setVisibility(View.INVISIBLE);
		firstPassword = null;
		titleTextView.setText(R.string.first_setting);
	}

	private void saveLockPatternPassword() {
		LockPatternUtil.saveLockPatternPassword(getActivity(), firstPassword);
		firstPassword = "";
		showShortToast(getString(R.string.setting_success));
		getActivity().setResult(getActivity().RESULT_OK);
		getActivity().finish();
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.fragment_lockPattern_button_reset:{
				resetLockPattern();
				lockPatternView.postInvalidate();
				break;
			}
			case R.id.fragment_lockPattern_button_save:{
				saveLockPatternPassword();
				break;
			}
		}


	}
}
