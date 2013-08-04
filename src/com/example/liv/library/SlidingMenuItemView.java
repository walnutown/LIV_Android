package com.example.liv.library;

import com.example.liv.R;
import com.example.liv.R.id;
import com.example.liv.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlidingMenuItemView extends LinearLayout{
	
	private ImageView mMenuIcon;
	private TextView mMenuLabel;
	
	
	public SlidingMenuItemView(Context context) {
		super(context);
		initView(context);
	}

	public SlidingMenuItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initView(context);
	}

	public SlidingMenuItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public void initView(Context context) {
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		LayoutInflater.from(context).inflate(R.layout.menu_item_view, this, true);

		mMenuIcon = (ImageView) findViewById(R.id.menu_icon);
		mMenuLabel = (TextView) findViewById(R.id.menu_label);
	}
	
	public void setIcon(int icon){
		mMenuIcon.setImageResource(icon);
	}
	
	public void setLabel(String label){
		mMenuLabel.setText(label);
	}

}
