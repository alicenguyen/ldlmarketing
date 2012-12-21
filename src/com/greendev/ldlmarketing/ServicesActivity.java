package com.greendev.ldlmarketing;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ServicesActivity extends LDLActivity {

	private ExpandableListView mExpandableList;
	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.services_layout);

		mExpandableList = (ExpandableListView) findViewById(R.id.expandable_list);
		
		mExpandableList.setChildDivider(getResources().getDrawable(R.color.transparent));  
		//mExpandableList.setGroupIndicator(null);

		ArrayList<Parent> arrayParents = new ArrayList<Parent>();
		ArrayList<String> arrayChildren;

		String[] parentNames = { "Marketing / Advertising",
				"Digital Media Marketing", "Event Management", "PR Management",
				"Media Planning", "CSR Program Development" };

		String[] marketingChildNames1 = { "Brand Development",
				"Logo Identity Design & Development",
				"Web Design & Development", "Above The Line (ATL) Advertising",
				"Below The Line (BTL) Advertising" };
		String[] digitalChildNames2 = {
				"Social Networking Sites Content Management",
				"Email Marketing", "Campaign Development" };
		String[] eventChildNames3 = { "Corporate Functions", "Private Events" };

		int count = 0;
		while (count < 6) {
			Parent parent;
			arrayChildren =  new ArrayList<String>();
			if (count == 0) {
				parent = new Parent();
				parent.setTitle(parentNames[0]);
				for (int i = 0; i < marketingChildNames1.length; i++) {
					arrayChildren.add(marketingChildNames1[i]);
					parent.setArrayChildren((arrayChildren));
				}
			} else if (count == 1) { 
				parent = new Parent();
				parent.setTitle(parentNames[1]);
				for (int i = 0; i < digitalChildNames2.length; i++) {
					arrayChildren.add(digitalChildNames2[i]);
					parent.setArrayChildren((arrayChildren));
				}
			} else if (count == 2) {
				parent = new Parent();
				parent.setTitle(parentNames[2]);
				for (int i = 0; i < eventChildNames3.length; i++) {
					arrayChildren.add(eventChildNames3[i]);
					parent.setArrayChildren((arrayChildren));
				}
			} else {
				parent = new Parent();
				parent.setTitle(parentNames[count]);
				parent.setArrayChildren((arrayChildren));
				 //mExpandableList.setGroupIndicator(state_empty);
			}
			arrayParents.add(parent);
			count++;
		}

		// sets the adapter that provides data to the list.
		mExpandableList.setAdapter(new MyCustomAdapter(ServicesActivity.this,
				arrayParents));
		mExpandableList.setGroupIndicator(getResources().getDrawable(R.drawable.services_group_selector));

	}

	private class Parent {
		private String mTitle;
		private ArrayList<String> mArrayChildren;

		public String getTitle() {
			return mTitle;
		}

		public void setTitle(String mTitle) {
			this.mTitle = mTitle;
		}

		public ArrayList<String> getArrayChildren() {
			return mArrayChildren;
		}

		public void setArrayChildren(ArrayList<String> mArrayChildren) {
			this.mArrayChildren = mArrayChildren;
		}
	}

	private class MyCustomAdapter extends BaseExpandableListAdapter {

		private LayoutInflater inflater;
		private ArrayList<Parent> mParent;

		public MyCustomAdapter(Context context, ArrayList<Parent> parent) {
			mParent = parent;
			inflater = LayoutInflater.from(context);
		}

		@Override
		// counts the number of group/parent items so the list knows how many
		// times calls getGroupView() method
		public int getGroupCount() {
			return mParent.size();
		}

		@Override
		// counts the number of children items so the list knows how many times
		// calls getChildView() method
		public int getChildrenCount(int i) {
			return mParent.get(i).getArrayChildren().size();
		}

		@Override
		// gets the title of each parent/group
		public Object getGroup(int i) {
			return mParent.get(i).getTitle();
		}

		@Override
		// gets the name of each item
		public Object getChild(int i, int i1) {
			return mParent.get(i).getArrayChildren().get(i1);
		}

		@Override
		public long getGroupId(int i) {
			return i;
		}

		@Override
		public long getChildId(int i, int i1) {
			return i1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		// in this method you must set the text to see the parent/group on the
		// list
		public View getGroupView(int i, boolean b, View view,
				ViewGroup viewGroup) {

			if (view == null) {
				view = inflater.inflate(R.layout.services_item_parent,
						viewGroup, false);
			}
//			if(getChildrenCount(i) > 0){
//				ImageView indic = (ImageView) view.findViewById(R.layout.indic);
//				indic.setImageDrawable(R.drawable.ic_expand);
//			}


			TextView textView = (TextView) view
					.findViewById(R.id.list_item_text_view);
			// "i" is the position of the parent/group in the list
			textView.setText(getGroup(i).toString());
			textView.setTypeface(fontbold);
			

			// return the entire view
			return view;
		}

		@Override
		// in this method you must set the text to see the children on the list
		public View getChildView(int i, int i1, boolean b, View view,
				ViewGroup viewGroup) {

			if (view == null) {
				view = inflater.inflate(R.layout.services_item_child,
						viewGroup, false);
			}

			TextView textView = (TextView) view
					.findViewById(R.id.list_item_text_child);
			// "i" is the position of the parent/group in the list and
			// "i1" is the position of the child
			textView.setText(mParent.get(i).getArrayChildren().get(i1));
			textView.setTypeface(fontreg);
			
			if (getChildrenCount(i) == 0) {
				view.setVisibility(View.INVISIBLE);
			}
			// return the entire view
			return view;
		}

		@Override
		public boolean isChildSelectable(int i, int i1) {
			return false;
		}
	}

}
