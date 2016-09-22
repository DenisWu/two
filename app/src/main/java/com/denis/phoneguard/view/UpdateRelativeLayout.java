package com.denis.phoneguard.view;

import com.denis.phoneguard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置页面中的自定义控件
 * 
 * @author yongda
 *
 */
public class UpdateRelativeLayout extends RelativeLayout {
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.denis.phoneguard";
	TextView tv_title, tv_detail;
	CheckBox cb_state;

	public UpdateRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public UpdateRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		String title = attrs.getAttributeValue(NAMESPACE, "title");
		String detail = attrs.getAttributeValue(NAMESPACE, "detail");
		tv_title.setText(title);
		tv_detail.setText(detail);
	}

	public UpdateRelativeLayout(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		View view = View.inflate(getContext(), R.layout.update_relativelayout, this);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_detail = (TextView) view.findViewById(R.id.tv_detail);
		cb_state = (CheckBox) view.findViewById(R.id.cb_state);
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setDetail(String detail) {
		tv_detail.setText(detail);
	}

	public boolean isChecked() {
		return cb_state.isChecked();
	}

	public void setChecked(boolean checked) {
		cb_state.setChecked(checked);
	}
}
