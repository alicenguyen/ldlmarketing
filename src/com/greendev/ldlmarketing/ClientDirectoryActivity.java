package com.greendev.ldlmarketing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClientDirectoryActivity extends LDLActivity implements
		OnClickListener {

	ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientdir_layout);

		// Find the ListView resource.
		listview = (ListView) findViewById(R.id.clientlistview);

		Client client_data[] = new Client[] {
				new Client(R.drawable.clientdir_triumph, NAMES[0], DESC[0]),
				new Client(R.drawable.clientdir_lbc, NAMES[1], DESC[1]),
				new Client(R.drawable.clientdir_bench, NAMES[2], DESC[2]),
				new Client(R.drawable.clientdir_holcim, NAMES[3], DESC[3]),
				new Client(R.drawable.clientdir_brieo, NAMES[4], DESC[4]),
				new Client(R.drawable.clientdir_bioessence, NAMES[5], DESC[5]),
				new Client(R.drawable.clientdir_pasto, NAMES[6], DESC[6]),
				new Client(R.drawable.clientdir_zao, NAMES[7], DESC[7]),
				new Client(R.drawable.clientdir_bountyfresh, NAMES[8], DESC[8]) };

		ClientAdapter adapter = new ClientAdapter(this,
				R.layout.clientdir_item, client_data);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String url = URL[position];
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
	}

	public class ClientAdapter extends ArrayAdapter<Client> {
		Context context;
		int layoutResourceId;
		Client data[] = null;

		public ClientAdapter(Context context, int layoutResourceId,
				Client[] data) {
			super(context, layoutResourceId, data);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ClientHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				holder = new ClientHolder();
				holder.imgIcon = (ImageView) row.findViewById(R.id.icon);
				holder.txtName = (TextView) row.findViewById(R.id.name_view);
				holder.txtDESC = (TextView) row.findViewById(R.id.desc_view);

				row.setTag(holder);
			} else {
				holder = (ClientHolder) row.getTag();
			}

			Client client = data[position];
			holder.txtName.setText(client.name);
			holder.txtName.setTypeface(fontbold);
			
			holder.txtDESC.setText(client.DESC);
			holder.txtDESC.setTypeface(fontreg);
			holder.imgIcon.setImageResource(client.icon);

			return row;
		}

	}

	public class Client {
		int icon;
		String name;
		String DESC;

		public Client(int img, String name, String DESC) {
			icon = img;
			this.name = name;
			this.DESC = DESC;
		}
	}

	static class ClientHolder {
		ImageView imgIcon;
		TextView txtName;
		TextView txtDESC;
	}

	private static String[] NAMES = new String[] { "Triumph", "LBC", "bench/",
			"Holcim", "brieo", "Bioessence", "Pasto", "zao",
			"Bounty Fresh Chicken" };

	private static String[] DESC = new String[] {
			"Triumph today enjoys a presence in over 120 countries all across the world. For its brands Triumph, sloggi, Valisere, the company develops, produces and sells underwear, lingerie, and swimwear, fusing excelling quality in workmanship, unrivaled fit and the latest in fashionable design.",
			"One of the pioneers in the field, LBC is continuously innovating ways to send and receive remittances anytime, anywhere in the Philippines.",
			"A fashion brand that offers premium quality products at affordable prices. bench/ opened up as a small store selling men's t-shirts. The company has now grown to include a ladies' line, underwear, fragrances, housewares, snacks and other lifestyle products. The company's growth can be attributed to their use of celebrity endorsers, television commercials and giant billboards along the busy streets of the metro.",
			"Holcim (US) Inc. is one of the largest manufacturers and suppliers of cement and mineral components in the United States.",
			"Brieo, a product of Brieo Digital Media Group, Inc., is what we would like to call \"community-buyin\".",
			"Bioessence Beauty Spa offers skin care and slimming services. Started in 1994 in Davao City with a three-bed clinic (Two for Facial and one for massage) . The founder aimed high and dreamed big-that BIOESSENCE become well known and be one of the leaders in the beauty, health and fitness industry.",
			"This Italian restaurant is in the heart of busy, upscale Global City and perfect for sundowners (beer and a motley of cocktails rule) with friends after work. It has an appealing red and black colour scheme and the table layout/seating arrangement make it comfortable for family dinners. The staff are quick on the mark, gracious and deftly clear up questions on menu items.",
			"Zao is a bright, sleek and casually lively place where food is the main attraction. Vietnamese restaurants aren't very common in the metro, and this level of authentic Vietnamese cooking is hard to come by. The food is good and easy on the wallet.",
			"The first large-scale broiler farm to use environmentally controlled houses in the Philippines." };

	private static String[] URL = new String[] {
			"http://www.triumph.com/ph/en/index.html",
			"http://www.lbcexpress.com/US/Home", "http://www.bench.com.ph/",
			"http://www.holcim.us/", "https://www.brieo.com/",
			"http://www.bioessence.ph/",
			"https://plus.google.com/104262187216332462787/about?gl=us&hl=en",
			"http://www.asiatatlerdining.com/philippines/zao",
			"http://www.bountyfreshchicken.com/" };

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
