package com.example.liv;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.liv.library.MenuDesc;
import com.example.liv.library.SlidingMenuAdapter;
import com.example.liv.library.UserFunctions;

public class HomeActivity extends Activity {

	private ActionBar ab;
	private ImageButton btnThumb;

	private LinearLayout host;
	private FrameLayout root;
	private View slidingMenu;
	private View dropShadow;
	private boolean menuShown = false;
	private int slideLengthHost;
	private int slideLengthMenu;
	private int shadowLength;
	private final static int HOST_SLIDE_LENGTH_IN_DP = 300;
	private final static int MENU_SLIDE_LENGTH_IN_DP = 100;
	private final static int SHADOW_LENGTH_IN_DP = 10;
	private final static int SLIDE_DURATION = 400;
	private int MenuOffset;
	private final static int MENU_OFFSET_PERCENT = 10;
	private TranslateAnimation slideRightHost;
	private TranslateAnimation slideRightMenu;
	private TranslateAnimation slideLeftHost;
	private TranslateAnimation slideLeftMenu;

	private ListView menuList;
	private ArrayList<MenuDesc> menuItems;

	private int statusHeight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);	
		init();
	}

	public void toggleSlide() {
		if (!menuShown) {
			slideRight();
			Log.d("Debug", "swipe left");
		} else {
			slideLeft();
			Log.d("Debug", "swipe right");
		}
	}

	public void drawSlidingMenu() {
		// Get the height of the status bar
		if (statusHeight == 0) {
			Rect rectgle = new Rect();
			Window window = this.getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
			statusHeight = rectgle.top;
		}

		// draw slidingMenu according to layout params
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		slidingMenu = inflater.inflate(R.layout.menu, null);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT, 4);
		params.setMargins(-slideLengthMenu, statusHeight, 0, 0);
		slidingMenu.setLayoutParams(params);

		// draw menu list
		menuList = (ListView) slidingMenu.findViewById(R.id.menu_list_view);
		menuList.setAdapter(new SlidingMenuAdapter(this, menuItems));

	}

	public void drawDropShadow() {
		if (statusHeight == 0) {
			Rect rectgle = new Rect();
			Window window = this.getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
			statusHeight = rectgle.top;
		}
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dropShadow = inflater.inflate(R.layout.drop_shadow, null);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT, 4);
		params.setMargins(-shadowLength, statusHeight, 0, 0);
		dropShadow.setLayoutParams(params);
	}

	public void slideRight() {

		host.startAnimation(slideRightHost);
		// top.startAnimation(slideRightHost);
		drawSlidingMenu();
		drawDropShadow();
		root.addView(slidingMenu);
		root.addView(dropShadow);
		root.bringChildToFront(host);
		// disable other views except overlay area
		slidingMenu.findViewById(R.id.overlay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (menuShown) {
					slideLeft();
				}
			}
		});
		UserFunctions.enableViewGroup(host, false);

		slidingMenu.startAnimation(slideRightMenu);
		dropShadow.startAnimation(slideRightHost);

		menuShown = true;

	}

	public void slideLeft() {
		root.bringChildToFront(host);
		slidingMenu.startAnimation(slideLeftMenu);
		dropShadow.startAnimation(slideLeftHost);

		host.startAnimation(slideLeftHost);
		slidingMenu.setVisibility(View.GONE);
		dropShadow.setVisibility(View.GONE);
		UserFunctions.enableViewGroup(root, true);

		menuShown = false;
	}

	public void init() {

		btnThumb = (ImageButton) findViewById(R.id.thumb_icon);
		btnThumb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toggleSlide();
			}
		});
			
		host = (LinearLayout) findViewById(android.R.id.content).getParent();
		root = (FrameLayout) host.getParent();

		slideLengthHost = UserFunctions.dpToPx(HOST_SLIDE_LENGTH_IN_DP, this);
		slideLengthMenu = UserFunctions.dpToPx(MENU_SLIDE_LENGTH_IN_DP, this);
		shadowLength = UserFunctions.dpToPx(SHADOW_LENGTH_IN_DP, this);

		slideRightHost = new TranslateAnimation(0, slideLengthHost, 0, 0);
		slideRightHost.setDuration(SLIDE_DURATION);
		slideRightHost.setFillAfter(true);

		slideRightMenu = new TranslateAnimation(0, slideLengthMenu, 0, 0);
		slideRightMenu.setDuration(SLIDE_DURATION);
		slideRightMenu.setFillAfter(true);

		slideLeftHost = new TranslateAnimation(slideLengthHost, 0, 0, 0);
		slideLeftHost.setDuration(SLIDE_DURATION);
		slideLeftHost.setFillAfter(true);

		slideLeftMenu = new TranslateAnimation(slideLengthMenu, 0, 0, 0);
		slideLeftMenu.setDuration(SLIDE_DURATION);
		slideLeftMenu.setFillAfter(true);

		menuItems = new ArrayList<MenuDesc>();
		for (int i = 0; i < 5; i++) {
			MenuDesc md = new MenuDesc();
			md.label = "menu" + i;
			md.icon = R.drawable.ic_launcher;
			menuItems.add(md);
		}

	}

}
