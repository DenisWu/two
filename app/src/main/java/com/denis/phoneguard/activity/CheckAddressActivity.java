package com.denis.phoneguard.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.denis.phoneguard.R;

/**
 * ��ѯ������
 *
 * @author yongda
 */
public class CheckAddressActivity extends Activity implements OnClickListener {
    private EditText et_check;
    private TextView tv_belongs;
    private Button bn_check;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_check_address);
        et_check = (EditText) findViewById(R.id.et_check);
        tv_belongs = (TextView) findViewById(R.id.tv_belongs);
        bn_check = (Button) findViewById(R.id.bn_check);
        bn_check.setOnClickListener(this);
        et_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputNumber = et_check.getText().toString().trim();
                if (inputNumber.length() > 6) {
                    inputNumber = inputNumber.substring(0, 7);
                }
                tv_belongs.setText(checkAddress(inputNumber));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_check:
                String inputNumber = et_check.getText().toString().trim();
                if (inputNumber.length() > 6) {
                    inputNumber = inputNumber.substring(0, 7);
                }
                tv_belongs.setText(checkAddress(inputNumber));
                break;
            default:
                break;
        }
    }

    /**
     * ���ݺ����ѯ������
     *
     * @param number
     * @return
     */
    private String checkAddress(String number) {
        String address = "未知地址";
        switch (number.length()) {
            case 3:
                address = "报警电话";
                break;
            case 4:
                address = "模拟器";
                break;
            case 5:
                address = "客服电话";
                break;
            case 6:
            case 7:
            case 8:
                address = "本地电话";
                String path = getFilesDir() + "/address.db";
                SQLiteDatabase db = openOrCreateDatabase(path, MODE_PRIVATE, null);
                Cursor outkeyCursor = db.query("data1", new String[]{"outkey"}, "id=?", new String[]{number}, null,
                        null, null);
                Cursor addrCursor = null;
                try {
                    if (outkeyCursor != null) {
                        outkeyCursor.moveToLast();
                        String outKey = outkeyCursor.getString(0);
                        Log.v("Shortcut", "outKey:" + outKey);
                        addrCursor = db.query("data2", new String[]{"location"}, "id=?", new String[]{outKey}, null,
                                null, null);
                        if (addrCursor != null) {
                            addrCursor.moveToNext();
                            address = addrCursor.getString(0);
                            Log.v("Shortcut", "address:" + addrCursor.getColumnName(0));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (outkeyCursor != null) {
                        outkeyCursor.close();
                    }
                    if (addrCursor != null) {
                        addrCursor.close();
                    }
                }
                break;
            default:
                break;
        }
        return address;
    }
}
