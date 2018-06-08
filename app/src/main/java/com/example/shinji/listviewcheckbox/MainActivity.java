package com.example.shinji.listviewcheckbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView mListView = (ListView) findViewById(R.id.ListView);

		ArrayList<SpotList> spotLists = new ArrayList<>();

		int[] icons = {
				R.mipmap.ic_launcher_round,
				R.mipmap.ic_launcher_round,
				R.mipmap.ic_launcher_round,
				R.mipmap.ic_launcher_round,
				R.mipmap.ic_launcher_round,
		};

		String[] ssid = {
				"aiueo12",
				"kakikukeko",
				"sashisuseso",
				"tachitsuteto",
				"naninuneno",
		};

		Integer[] priority = {
				1,
				2,
				3,
				4,
				5,
		};

		Boolean[] enable = {
				true,
				false,
				true,
				true,
				false
		};

		for (int i = 0; i < icons.length; i++) {
			SpotList spotList = new SpotList(BitmapFactory.decodeResource(getResources(),icons[i]),ssid[i],priority[i],enable[i]);
			spotLists.add(spotList);
		}

		SpotListAdapter adapter = new SpotListAdapter(this, 0, spotLists);
		mListView.setAdapter(adapter);


		// リストビューのクリックイベントを取得
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("DEBUG_DATA", "onItemClick");

				String msg = position + "番目のアイテムがクリックされました";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
	}

	public class SpotListAdapter extends ArrayAdapter<SpotList> {
		protected LayoutInflater inflater;
		private List<SpotList> spotLists; // ☆追加
		private int rowLayoutResourceId; // ☆追加

		private LayoutInflater layoutInflater;
		public SpotListAdapter(Context c, int id, ArrayList<SpotList> spotLists) {
			super(c, id, spotLists);
			this.spotLists = spotLists; // ☆追加
			this.layoutInflater = (LayoutInflater) c.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE
			);
			this.rowLayoutResourceId = id; // ☆追加
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.wifi_list_item,
						parent,
						false
				);

				// ビューホルダーを作成して、データを取得
				ViewHolder vh = new ViewHolder();
				vh.icon = convertView.findViewById(R.id.icon);
				vh.ssid = convertView.findViewById(R.id.ssid);
				vh.enable = convertView.findViewById(R.id.enable);

				convertView.setTag(vh);
			}

			final int p = position;


			SpotList spotList = getItem(position);
			ViewHolder vh = (ViewHolder) convertView.getTag();

			vh.icon.setImageBitmap(spotList.getIcon());
			vh.ssid.setText(spotList.getSsid());
			if(spotList.getEnable()) vh.enable.isChecked();

			// 必ずsetChecked前にリスナを登録(convertView != null の場合は既に別行用のリスナが登録されている！)
			vh.enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					Log.d("DEBUG_DATA", "p=" + String.valueOf(p) + ", isChecked=" + String.valueOf(isChecked));
				}
			});


			return convertView;
		}
	}


	private static class ViewHolder {
		private ImageView icon;
		private TextView ssid;
		private CheckBox enable;
	}
}
