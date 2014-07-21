package com.adspitcher.adapters;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.adspitcher.R;

public class FiltersAdapter extends ArrayAdapter<String> {

	Context context;
	int layoutResourceId;
	String[] filterItems = null;
	ViewHolder holder = null;
	public HashMap<String, String> checked = new HashMap<String, String>();

	public FiltersAdapter(Context context, int layoutResourceId,
			String[] objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.filterItems = objects;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater layout_inflator = ((Activity) context)
					.getLayoutInflater();
			convertView = layout_inflator.inflate(R.layout.item_filters, null);
			holder.chkbox_filterText = (CheckBox) convertView
					.findViewById(R.id.checkbox_filter_item);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		String item = filterItems[position];
		holder.chkbox_filterText.setText(item);
		
		holder.chkbox_filterText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				CheckBox chkBox = (CheckBox)view;
				if(chkBox.isChecked()){
					checked.put(String.valueOf(position), ""+chkBox.getText());
				}else{
					checked.remove(chkBox.getText());
				}
			}
		});

		return convertView;
	}

	public HashMap<String, String> getCheckedItems() {
		return checked;
	}

	/**
	 * A class defining the view holder
	 */
	static class ViewHolder {
		private CheckBox chkbox_filterText;
	}

}