package com.denis.phoneguard.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 手机防盗初始化页面1
 * 
 * @author yongda
 *
 */
public class Setup3Activity extends Activity {
	private Context context;
	private String SelectedNumber;
	private EditText et_contact;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_setup3);
		et_contact=(EditText) findViewById(R.id.et_contact);
		et_contact.setText(AppConf.getBindPhone(context));
	}

	/**
	 * 下一步
	 * 
	 * @param view
	 */
	public void next(View view) {
		SelectedNumber=et_contact.getText().toString();
		if(TextUtils.isEmpty(SelectedNumber)){
			Toast.makeText(context, "请选择联系人", Toast.LENGTH_SHORT).show();
			return;
		}
		AppConf.setBindPhone(context, SelectedNumber);
		Intent intent = new Intent(Setup3Activity.this, Setup4Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_next_enter, R.anim.acitvity_next_out);
	}

	/**
	 * 上一步
	 */
	public void previous(View view) {
		Intent intent = new Intent(Setup3Activity.this, Setup2Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_previous_enter, R.anim.acitvity_previous_out);
	}

	/**
	 * 选择联系人
	 */
	public void chooseContact(View view) {
		final ArrayList<HashMap<String, String>> contactList = readContact();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View mView = View.inflate(context, R.layout.dialog_show_contact, null);
		ListView lv_contact = (ListView) mView.findViewById(R.id.lv_contact);
		lv_contact.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				et_contact.setText(contactList.get(position).get("phone"));
				dialog.dismiss();
			}
		});
		lv_contact.setAdapter(new SimpleAdapter(context, contactList, R.layout.contact_item_list,
				new String[] { "name", "phone" }, new int[] { R.id.tv_name, R.id.tv_number }));
		builder.setView(mView);
		dialog = builder.create();
		dialog.show();
	}

	private ArrayList<HashMap<String, String>> readContact() {
		// 首先,从raw_contacts中读取联系人的id("contact_id")
		// 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
		// 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
		Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		// 从raw_contacts中读取联系人的id("contact_id")
		Cursor rawContactsCursor = getContentResolver().query(rawContactsUri, new String[] { "contact_id" }, null, null,
				null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				String contactId = rawContactsCursor.getString(0);
				// 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
				Cursor dataCursor = getContentResolver().query(dataUri, new String[] { "data1", "mimetype" },
						"contact_id=?", new String[] { contactId }, null);
				if (dataCursor != null) {
					HashMap<String, String> map = new HashMap<String, String>();
					while (dataCursor.moveToNext()) {
						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							map.put("phone", data1);
						} else if ("vnd.android.cursor.item/name".equals(mimetype)) {
							map.put("name", data1);
						}
					}
					list.add(map);
					dataCursor.close();
				}
			}
			rawContactsCursor.close();
		}
		return list;
	}
}
