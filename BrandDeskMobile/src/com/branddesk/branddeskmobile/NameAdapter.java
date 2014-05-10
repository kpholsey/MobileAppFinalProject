package com.branddesk.branddeskmobile;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.branddeskmobile.R;

public class NameAdapter extends ArrayAdapter<Name>{
	protected Context mContext;
	protected ArrayList<Name> mNames;
	
	public NameAdapter(Context context, ArrayList<Name> names) {
		super(context, R.layout.name_item_list, names);
		mContext = context;
		mNames = names;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Name currentName = mNames.get(position);
		
		if (convertView == null) {
		convertView = LayoutInflater.from(mContext).inflate(R.layout.name_item_list, null);
		holder = new ViewHolder();
		holder.name = (TextView) convertView.findViewById(R.id.textViewNameAdapter);
		holder.trademark = (ImageView) convertView.findViewById(R.id.imageViewTrademark);
		holder.domains = (ImageView) convertView.findViewById(R.id.imageViewDomain);
		holder.twitter = (ImageView) convertView.findViewById(R.id.imageViewTwitter);
		holder.facebook = (ImageView) convertView.findViewById(R.id.imageViewFacebook);
		
		convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(currentName.getName());
		
		if (currentName.getDotComAvailability().equals("available") && currentName.getDotNetAvailability().equals("available")) {
			holder.domains.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
		} else if (currentName.getDotComAvailability().equals("taken") && currentName.getDotNetAvailability().equals("taken")) {
			holder.domains.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xmark));
		} else {
			holder.domains.setImageDrawable(mContext.getResources().getDrawable(R.drawable.questionmark));
		}
		
		if (currentName.isTwitterUsernameAvailable()) {
			holder.twitter.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
		} else {
			holder.twitter.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xmark));
		}
		
		if (currentName.isFacebookUrlAvailable()) {
			holder.facebook.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
		} else {
			holder.facebook.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xmark));
		}
		
		if (currentName.getTrademarkCount() > 0) {
			holder.trademark.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xmark));
		} else {
			holder.trademark.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
		}
		
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView trademark;
		ImageView domains;
		ImageView twitter;
		ImageView facebook;
		TextView name;
	}
	
}

