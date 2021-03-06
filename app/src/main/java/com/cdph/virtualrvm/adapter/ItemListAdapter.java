package com.cdph.virtualrvm.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.*;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.cdph.virtualrvm.model.ItemModel;
import com.cdph.virtualrvm.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends Adapter<ItemListAdapter.ItemListViewHolder> implements Filterable
{
	private List<ItemModel> itemList;
	private List<ItemModel> itemListFull;
	
	public ItemListAdapter(@NonNull List<ItemModel> items)
	{
		this.itemList = items;
		this.itemListFull = new ArrayList<>(items);
	}
	
	@NonNull
	@Override
	public ItemListAdapter.ItemListViewHolder onCreateViewHolder(ViewGroup parent, int type)
	{
		return (new ItemListAdapter.ItemListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false)));
	}
	
	@Override
	public void onBindViewHolder(@NonNull final ItemListAdapter.ItemListViewHolder holder, int position)
	{
		final ItemModel model = itemList.get(position);
		
		holder.tv_itemLabel.setText("Item Name");
		holder.tv_itemName.setText(model.itemName);
		
		holder.btn_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		holder.btn_edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}
	
	@Override
	public int getItemCount()
	{
		return itemList.size();
	}
	
	@Override
	public Filter getFilter()
	{
		return filter;
	}
    
	public class ItemListViewHolder extends ViewHolder
	{
		public Context context;
		public ImageButton btn_edit, btn_delete;
		public LinearLayout parent;
		public TextView tv_itemName, tv_itemLabel;
		
		public ItemListViewHolder(View view)
		{
			super(view);
			
			context = view.getContext();
			parent = (LinearLayout) view;
			btn_edit = view.findViewById(R.id.content_list_edit);
			btn_delete = view.findViewById(R.id.content_list_delete);
			tv_itemLabel = view.findViewById(R.id.content_list_label);
			tv_itemName = view.findViewById(R.id.content_list_name);
		}
	}
	
	private Filter filter = new Filter()
	{
		@Override
		protected Filter.FilterResults performFiltering(CharSequence constraint)
		{
			List<ItemModel> filteredList = new ArrayList<>();
			
			if(constraint != null && constraint.length() > 0)
			{
				String filterPattern = constraint.toString().toLowerCase().trim();
				for(ItemModel model : itemListFull)
					if(model.itemName.contains(filterPattern))
						filteredList.add(model);
			}
			else
				filteredList.addAll(itemListFull);
			
			Filter.FilterResults result = new Filter.FilterResults();
			result.values = filteredList;
			return result;
		}
		
		@Override
		protected void publishResults(CharSequence constraint, Filter.FilterResults result)
		{
			itemList.clear();
			itemList.addAll((List) result.values);
			notifyDataSetChanged();
		}
	};
}
