package com.example.liv.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.liv.R;

public class DealListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DealDesc> deals;
	LayoutInflater inflater;
	private HashMap<Integer, DealView> dvMap = new HashMap<Integer, DealView>();
	private int currPos;

	public DealListAdapter(Context _context, ArrayList<DealDesc> _items) {
		context = _context;
		deals = _items;
		inflater = LayoutInflater.from(_context);
		currPos = 0;
	}

	public int getCount() {
		return deals.size();
	}

	public Object getItem(int position) {
		return deals.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void clearMP() {
		Log.d("VideoListAdapter", "ClearMP");
		for (Map.Entry<Integer, DealView> entry : dvMap.entrySet()) {
			Integer pos = entry.getKey();
			if (pos != (currPos + 1)) {
				DealView dv = entry.getValue();
				if (dv.isMPCreated()) {
					dv.stopPlayBack();
					Log.d("clearMP", "clear " + pos);
				}
			}
		}
	}

	// Return the view for each row represented in the ListView. It is passed in
	// the
	// position that is meant to be returned (along with a View object
	// representing the
	// current View and an object that represents the parent ViewGroup)
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("Debug", "getView(): " + position);
		currPos = position;
		clearMP();
		View dealRow = inflater.inflate(R.layout.deal_list_item, null);
		final DealView dv = (DealView) dealRow.findViewById(R.id.myDealView);
		String url = deals.get(position).videoPath;

		dv.setThumbnailVisibility(true);
		//dv.setThumbnailVisibility(false);
		dv.setThumbnail(deals.get(position).thumbPath);
		dv.setTitle(deals.get(position).title);
		//dv.setDesc(deals.get(position).desc);
		dv.setVideoURI(Uri.parse(url));
		
		dvMap.put(position, dv);
		return dealRow;
	}
}
