package com.example.liv.library;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liv.R;

/*
 * MyVideoView like the original VideoView, yet based on TextureView 
 * instead of SurfaceView
 * */
public class DealView extends LinearLayout {

	private MediaPlayer mMediaPlayer = null;
	private ImageView mVideoThumb;
	private Uri mUri;
	private Surface mSurface;
	private TextureView mTextureView;
	private TextView mTitle;
	private TextView mDesc;
	private TextView mOldPrice;

	// all possible internal states
	private static final int STATE_ERROR = -1;
	private static final int STATE_IDLE = 0;
	private static final int STATE_PREPARING = 1;
	private static final int STATE_PREPARED = 2;
	private static final int STATE_PLAYING = 3;
	private static final int STATE_PAUSED = 4;

	// mCurrentState is a VideoView object's current state.
	// mTargetState is the state that a method caller intends to reach.
	// For instance, regardless the VideoView object's current state,
	// calling pause() intends to bring the object to a target state
	// of STATE_PAUSED.
	private int mCurrentState = STATE_IDLE;
	private int mTargetState = STATE_IDLE;

	public DealView(Context context) {
		super(context);
		initDealView(context);
	}

	public DealView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initDealView(context);
	}

	public DealView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initDealView(context);
	}

	public void initDealView(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		LayoutInflater.from(context).inflate(R.layout.deal_view, this, true);
		mTextureView = (TextureView) findViewById(R.id.video_holder);
		mTextureView.setFocusable(true);
		mTextureView.setFocusableInTouchMode(true);
		mTextureView.requestFocus();

		mVideoThumb = (ImageView) findViewById(R.id.video_thumb);
		// mVideoThumb.setVisibility(View.GONE);
		mTitle = (TextView) findViewById(R.id.title);
		//mDesc = (TextView) findViewById(R.id.desc);
		mOldPrice = (TextView) findViewById(R.id.old_price);
		mOldPrice.setPaintFlags(mOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

		mCurrentState = STATE_IDLE;
		mTargetState = STATE_IDLE;
	}

	public void setVideoURI(Uri uri) {
		mUri = uri;
		openVideo();
		mTextureView.requestLayout();
		mTextureView.invalidate();
	}

	public void stopPlayBack() {
		if (mMediaPlayer != null) {
			try {
				Log.d("Debug", "mp released. " + mMediaPlayer.toString());
				mMediaPlayer.stop();
				mMediaPlayer.release();
				mMediaPlayer = null;
				mCurrentState = STATE_IDLE;
				mTargetState = STATE_IDLE;
			} catch (Exception e) {
				e.printStackTrace();
				mCurrentState = STATE_ERROR;
				mTargetState = STATE_ERROR;
			}

		}
	}

	public void setTitle(String title) {
		mTitle.setText(title);
		Log.d("Debug", "set title");
	}

	public void setDesc(String desc) {
		mDesc.setText(desc);
		Log.d("Debug", "set desc");
	}

	public void setThumbnailVisibility(Boolean value) {
		if (!value) {
			mVideoThumb.setVisibility(View.GONE);
		} else {
			mVideoThumb.setVisibility(View.VISIBLE);
		}
	}

	public void setThumbnail(int res) {
		mVideoThumb.setImageResource(res);
	}

	public void openVideo() {
		if (mUri == null) {
			// not ready for playback just yet, will try again later
			Log.d("URI", "null ");
			return;
		}
		// we shouldn't clear the target state, because somebody might have
		// called start() previously
		release(false);
		try {
			mMediaPlayer = new MediaPlayer();
			Log.d("mp", "mp created. " + mMediaPlayer.toString());
			mMediaPlayer.setOnPreparedListener(mPreparedListener);
			mMediaPlayer.setOnCompletionListener(mCompletionListener);
			mTextureView.setKeepScreenOn(true);
			mTextureView.setOnClickListener(mClickListener);
			// setOnTouchListener(mTouchListener);
			mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void release(boolean cleartargetstate) {
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
			mCurrentState = STATE_IDLE;
			if (cleartargetstate) {
				mTargetState = STATE_IDLE;
			}
		}
	}

	public void start() {
		if (isInPlaybackState()) {
			mMediaPlayer.start();
			// Log.d("mp", "state: " + mCurrentState);
			mCurrentState = STATE_PLAYING;
			Log.d("mp", "mp.start() with old mp. " + mMediaPlayer.toString());
		} else if (mMediaPlayer == null) {
			if (mUri == null) {
				// not ready for playback just yet, will try again later
				Log.d("URI", "null ");
				return;
			}
			release(false);
			try {
				mMediaPlayer = new MediaPlayer();
				Log.d("mp", "mp created. " + mMediaPlayer.toString());
				mMediaPlayer.setSurface(mSurface);
				mMediaPlayer.setDataSource(getContext(), mUri);
				mMediaPlayer.prepareAsync();
				mMediaPlayer.setOnPreparedListener(mPreparedListener);
				mMediaPlayer.setOnCompletionListener(mCompletionListener);
				// Log.d("mp", "mp preparing");
				mCurrentState = STATE_PREPARING;
				Log.d("mp", "mp is start() with new mp. " + mMediaPlayer.toString());
			} catch (Exception e) {
				e.printStackTrace();
				mCurrentState = STATE_ERROR;
				mTargetState = STATE_ERROR;
			}
		}
		mTargetState = STATE_PLAYING;
	}

	public void pause() {
		if (isInPlaybackState()) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mCurrentState = STATE_PAUSED;
				Log.d("mp", "mp.pause()");
			}
		}
		mTargetState = STATE_PAUSED;
	}

	public boolean isPlaying() {
		if (mMediaPlayer == null) {
			return false;
		}
		return mMediaPlayer.isPlaying();
	}

	public boolean isPrepared() {
		if (mMediaPlayer != null) {
			if (mCurrentState == STATE_PREPARED) {
				return true;
			}
		}
		return false;
	}

	public boolean isMPCreated() {
		if (mMediaPlayer != null) {
			return true;
		}
		return false;
	}

	// include prepared, palying, pause, playback-completed
	private boolean isInPlaybackState() {
		return (mMediaPlayer != null && mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING);
	}

	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			mp.start();
		}
	};

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mMediaPlayer == null) {
				return;
			}
			try {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.pause();
					mCurrentState = STATE_PAUSED;
					mTargetState = STATE_PAUSED;
					Log.d("mp", "Pause");
				} else {
					mMediaPlayer.start();
					mCurrentState = STATE_PLAYING;
					mTargetState = STATE_PLAYING;
					Log.d("mp", "Resume");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mCurrentState = STATE_ERROR;
				mTargetState = STATE_ERROR;
			}
		}
	};

	private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			mCurrentState = STATE_PREPARED;
			Log.d("mp", "mp prepared");
			// Due to latency of start(), delay the image gone
			mVideoThumb.postDelayed(new Runnable() {

				@Override
				public void run() {
					mVideoThumb.setVisibility(View.GONE);
					Log.d("Debug", "Thumbnail gone.");
				}
			}, 250);
			// mVideoThumb.setVisibility(View.GONE);
			mMediaPlayer.start();
		}
	};

	private SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			// Log.d("Debug", "SurfaceTextureUpdated");
		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			Log.d("Debug", "SurfaceTextureDestroyed");
			stopPlayBack();
			// Log.d("mp", "mp released");
			return true;
		}

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
			try {
				Log.d("Debug", "SurfaceTextureAvailable");
				mSurface = new Surface(surface);
				// mMediaPlayer has been released in the process of scrolling
				// this statement must be behind surface assignment
				if (mMediaPlayer == null) {
					return;
				}
				mMediaPlayer.setSurface(mSurface);
				mMediaPlayer.setDataSource(getContext(), mUri);
				mMediaPlayer.prepareAsync();
				mCurrentState = STATE_PREPARING;
				// Log.d("mp", "mp preparing");
			} catch (Exception e) {
				e.printStackTrace();
				mCurrentState = STATE_ERROR;
				mTargetState = STATE_ERROR;
			}
		}
	};

}
