package com.example.liv.library;

import java.util.ArrayList;

import com.example.liv.R;
import com.example.liv.R.id;
import com.example.liv.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class SlidingMenuAdapter extends BaseAdapter{
	
	Context context;
	private ArrayList<MenuDesc> menuItems;
	LayoutInflater inflater;
	
//	public class MenuDesc{
//		public int icon;
//		public String label;
//	}
	
	public SlidingMenuAdapter(Context _context, ArrayList<MenuDesc> _items){
		context = _context;
		menuItems = _items;
		inflater = LayoutInflater.from(_context);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View menuItemRow = inflater.inflate(R.layout.menu_list_item, null);
		final SlidingMenuItemView sv = (SlidingMenuItemView) menuItemRow.findViewById(R.id.myMenuItemView);
		MenuDesc mi = menuItems.get(position);
		sv.setIcon(mi.icon);
		sv.setLabel(mi.label);
		
		return menuItemRow;
	}
	@Override
	public int getCount() {
		return menuItems.size();
	}
	@Override
	public Object getItem(int position) {
		return menuItems.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
}