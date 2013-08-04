package com.example.liv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.liv.library.DealDesc;
import com.example.liv.library.DealView;
import com.example.liv.library.MenuDesc;
import com.example.liv.library.SlidingMenuAdapter;
import com.example.liv.library.UserFunctions;
import com.example.liv.library.VideoListAdapter;

public class HomeActivity extends Activity {

	private ActionBar ab;
	private ImageButton btnThumb;
	private ListView menuList;
	private ListView videoList;
	private View dealRowFirst;
	private DealView dvFirst;
	private View dealRowLast;
	private DealView dvLast;
	private LinearLayout host;
	private FrameLayout root;
	private View slidingMenu;
	private View dropShadow;
	
	private boolean menuShown;
	private int slideLengthHost;
	private int slideLengthMenu;
	private int shadowLength;
	private int statusHeight;
	
	private final static int HOST_SLIDE_LENGTH_IN_DP = 300;
	private final static int MENU_SLIDE_LENGTH_IN_DP = 100;
	private final static int SHADOW_LENGTH_IN_DP = 10;
	private final static int SLIDE_DURATION = 400;
//	private final static int DOWN_SCROLL_THRESHOLD = 10;
//	private final static int UP_SCROLL_THRESHOLD = 400;
//	private final static int ROW_HEIGHT = 936;
//	private final static int SCROLL_DOWN = 1;
//	private final static int NO_SCROLL = 2;
//	private final static int SCROLL_UP = 3;
	private final static float SCROLL_FRICTION_OFFSET = (float) 0.0;
//	private int MenuOffset;
//	private final static int MENU_OFFSET_PERCENT = 10;
	
	private TranslateAnimation slideRightHost;
	private TranslateAnimation slideRightMenu;
	private TranslateAnimation slideLeftHost;
	private TranslateAnimation slideLeftMenu;

	
	private ArrayList<MenuDesc> menuItems;

	
	
	ArrayList<DealDesc> dealRows = new ArrayList<DealDesc>();
	ArrayList<String> videoPaths = new ArrayList<String>();
	
	
//	private DealView dvLast_prev = null;
	// private int prevPos = ROW_HEIGHT;
	// private int currPos = ROW_HEIGHT;
//	private int prevPos = 0;
//	private int currPos = 0;
//	private int scrollDirection;

	private HashMap<Integer, DealView> dvMap = new HashMap<Integer, DealView>();

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);	
		init();
		
		videoList = (ListView) findViewById(R.id.videoList);
		videoPaths
				.add(0, "https://v.cdn.vine.co/r/videos/37B926EBC4965748550245969920_17915f6d3d3.3_cHR4.8r6RDQXA_MwZRSvakvKuMdIV1XiHeI4Lq6_spR0SMqDo5zS5vTCuYI7BM7T.mp4?versionId=GofccS921hBgrP.f36TQpZpBJY_JLhgj");
		videoPaths
				.add(1, "https://v.cdn.vine.co/r/videos/630824DB37966941739384557568_1736066dbf6.3_pyjAJU.RqG7gEUty5Ado_sk6NROgDltxqgYRLgrPD7xKsEk.PUBPKHObWXSPtrQf.mp4?versionId=_lPIR99ivITsJZynfT8EABJAV8aXz551");
		videoPaths
				.add(2, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		videoPaths
				.add(3, "https://v.cdn.vine.co/r/videos/6D43C68100964692366743240704_1acd90e551a.3_K2q5qaEtoRvg0K9.KX4BPK_rpIUweynZCIEAQPThYXWwW6w1tI5EgwYdqNk_0mDu.mp4?versionId=H6QOz2P2y_61q2axSQSkskdBXhS.GHiE");
		videoPaths
				.add(4, "https://v.cdn.vine.co/r/videos/FA01205D-0ABE-4AC9-8812-CFC1D75E544B-6231-0000031EBD2EE420_197450298e5.1.3.mp4?versionId=z7.LcYvqtxmLeLXsUNT60Iqv8ebtNU3X");
		videoPaths
				.add(5, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		videoPaths
				.add(6, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		videoPaths
				.add(7, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		videoPaths
				.add(8, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		videoPaths
				.add(9, "https://v.cdn.vine.co/v/videos/1D0360AA-2502-4E82-9738-D0F700D1ED1D-31315-00000B49D14A1A98_1.1.2.mp4?versionId=tquc9S.CYdHtwoQ_lXmqerCR6n3s_0te");
		
		for (int i = 0; i < videoPaths.size(); i++) {
			DealDesc dvi = new DealDesc();
			dvi.videoPath = videoPaths.get(i);
			if (i == 0){dvi.thumbPath = R.drawable.v0;}
			if (i == 1){dvi.thumbPath = R.drawable.v1;}
			if (i == 2){dvi.thumbPath = R.drawable.v2;}
			if (i == 3){dvi.thumbPath = R.drawable.v3;}
			if (i == 4){dvi.thumbPath = R.drawable.v4;}
			if (i == 5){dvi.thumbPath = R.drawable.v5;}
			if (i == 6){dvi.thumbPath = R.drawable.v6;}
			if (i == 7){dvi.thumbPath = R.drawable.v7;}
			if (i == 8){dvi.thumbPath = R.drawable.v8;}
			if (i == 9){dvi.thumbPath = R.drawable.v9;}
			dvi.title = "Video " + i;
			dvi.desc = "fairly generic and I know there might not be an 100% answer to it. I'm building an ASP .NET web solution that will include a lot of pictures and hopefully a fair amount of traffic."
					+ " I do really want to achieve performance. OMMENT: Thanks for many good answers. I will go for a file based solution even if I like the idea of having a 100% database driven solution. It "
					+ "seems that there are today good solutions to do what I want with databases etc but I have a few reasons for not doing it."
					+ "When I was setting up my first SQL database test table with phpMyAdmin on my website, I was confronted with making choices I "
					+ "don't remember encountering in the tutorials. One choice in particular about collation stumped me.The default collation in phpMyAdmin"
					+ " for tables is automatically latin1_swedish_ci, and I accepted it since I didn't have a clue about what might be a better choice (and I "
					+ "looked collation up at dev.mysql.com, but it didn't help me understand what I should choose). I had hoped I'd see something like English "
					+ "as an option, but no such luck (or I missed it if it was there).";
			dealRows.add(dvi);
		}

		// Log.d("Debug",
		// "Set the adapter of the ListView object to be a new instance of VideoGalleryAdapter");
		videoList.setAdapter(new VideoListAdapter(this, dealRows));
		videoList.setFriction((float) (ViewConfiguration.getScrollFriction() + SCROLL_FRICTION_OFFSET));
		videoList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// is being scrolled
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// has been scrolled
				new PositionCheck().execute();
			}
		});
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
		statusHeight = 0;
		menuShown = false;

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
	
//	public int checkScrollDirection(int prevPos, int currPos) {
//		if (prevPos > currPos) {
//			return SCROLL_UP;
//		} else if (prevPos == currPos) {
//			return NO_SCROLL;
//		} else {
//			return SCROLL_DOWN;
//		}
//	}

	public void clearMP() {
		Log.d("MainActivity", "ClearMP");
		for (Map.Entry<Integer, DealView> entry : dvMap.entrySet()) {
			Integer pos = entry.getKey();
			DealView dv = entry.getValue();
			if (dv.isMPCreated()) {
				dv.stopPlayBack();
				Log.d("clearMP", "clear " + pos);
			}
		}
	}

	protected class PositionCheck extends AsyncTask<Context, Integer, String> {

		@Override
		protected String doInBackground(Context... arg0) {
			int firstPos = videoList.getFirstVisiblePosition();
			int lastPos = videoList.getLastVisiblePosition();
			if (lastPos == -1) {
				Log.d("Debug", "invalid lastPos = -1");
				return null;
			}
			Log.d("FirstPosition; LastPosition", "-------" + firstPos + "; " + lastPos + "-------");
			try {
				dealRowFirst = videoList.getChildAt(0);
				if (dealRowFirst == null) {
					Log.d("Debug", "dealRowFirst is null");
					return null;
				} else {
					dvFirst = (DealView) dealRowFirst.findViewById(R.id.myDealView);
				}
				// if there's just one video on the screen
				if (firstPos == lastPos) {
					if (!dvFirst.isMPCreated()) {
						clearMP();
						dvFirst.start();
						dvMap.put(firstPos, dvFirst);
					}
				} else {

					dealRowLast = videoList.getChildAt(1);
					if (dealRowLast == null) {
						Log.d("Debug", "dealRowLast is null");
						return null;
					} else {
						dvLast = (DealView) dealRowLast.findViewById(R.id.myDealView);
					}
					if (!dvLast.isMPCreated()) {
						Log.d("Debug", "1 plan");
						dvLast.start();
					}
					// make sure that dvLast is playing
					dvMap.put(lastPos, dvLast);
					if (dvFirst.isMPCreated()) {
						Log.d("Debug", "2 plan");
						dvFirst.stopPlayBack();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
