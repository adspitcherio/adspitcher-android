package com.adspitcher.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.dataobjects.OffersItem;

public class LatestOffersFragmentAdapter extends ArrayAdapter<OffersItem> {

	Context context;
	int layoutResourceId;
	OffersItem[] offersItem = null;

	public LatestOffersFragmentAdapter(Context context, int layoutResourceId,
			OffersItem[] objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.offersItem = objects;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layout_inflator = ((Activity) context)
					.getLayoutInflater();
			convertView = layout_inflator.inflate(R.layout.item_offer, null);
			holder.dataCell_text = (TextView) convertView
					.findViewById(R.id.textview_offertext);
			holder.dataCell_brandorstore = (TextView) convertView.findViewById(R.id.textview_brandstore);
			holder.dataCell_views = (TextView) convertView
					.findViewById(R.id.textview_views);
			holder.dataCell_reviews = (TextView) convertView
					.findViewById(R.id.textview_reviews);
			holder.dataCell_ups = (TextView) convertView
					.findViewById(R.id.textview_ups);
			holder.dataCell_downs = (TextView) convertView
					.findViewById(R.id.textview_downs);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.dataCell_views.setText("");
			holder.dataCell_views.setText(R.string.text_views);
			holder.dataCell_reviews.setText("");
			holder.dataCell_reviews.setText(R.string.text_reviews);
			holder.dataCell_ups.setText("");
			holder.dataCell_ups.setText(R.string.text_up);
			holder.dataCell_downs.setText("");
			holder.dataCell_downs.setText(R.string.text_down);
			
		}

		OffersItem item = offersItem[position];
		holder.dataCell_text.setText(item.getOffer_text());
		holder.dataCell_brandorstore.setText(item.getOffer_brandorstore());
		holder.dataCell_views.setText(holder.dataCell_views.getText() + "" + item.getOffer_views());
		holder.dataCell_reviews.setText(holder.dataCell_reviews.getText() + "" + item.getOffer_reviews());
		holder.dataCell_ups.setText(holder.dataCell_ups.getText() + "" + item.getVotesup());
		holder.dataCell_downs.setText(holder.dataCell_downs.getText() + "" + item.getVotesdown());
		// BitmapDrawable icon = new BitmapDrawable(convertView.getResources(),
		// item.getItem_icon());
		// holder.dataCell_text.setCompoundDrawables(icon, null, null, null);

		return convertView;
	}

	/**
	 * A class defining the view holder
	 */
	static class ViewHolder {
		private TextView dataCell_text;
		private TextView dataCell_brandorstore;
		private TextView dataCell_views;
		private TextView dataCell_reviews;
		private TextView dataCell_ups;
		private TextView dataCell_downs;
	}

}
