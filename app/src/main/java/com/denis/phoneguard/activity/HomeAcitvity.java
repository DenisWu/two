package com.denis.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;
import com.denis.phoneguard.service.LocationService;
import com.denis.phoneguard.utils.Md5;

public class HomeAcitvity extends Activity {
    private GridView gv_home;
    private int[] app_name = {R.string.item0, R.string.item1, R.string.item2, R.string.item3, R.string.item4, R.string.item5, R.string.item6, R
            .string.item7, R.string.item8};
    private int[] app_icon = {R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
            R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};
    private Context mContext;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_home);
        startService(new Intent(this, LocationService.class));
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new IconListAdapter());
        gv_home.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // 手机防盗
                    case 0:
                        if (AppConf.getFindLostPwd(mContext).isEmpty()) {
                            settingPwdDial();
                        } else {
                            testPwdDial();
                        }
                        break;
                    //通讯卫士
                    case 1:
                        Intent intentBN = new Intent(HomeAcitvity.this, BlackNameActivity.class);
                        startActivity(intentBN);
                        break;
                    // 高级工具
                    case 7:
                        Intent intentAT = new Intent(HomeAcitvity.this, AToolsActivity.class);
                        intentAT.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intentAT);
                        break;
                    case 8:
                        // 高级设置
                        Intent intent = new Intent(HomeAcitvity.this, SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }

            }
        });
    }

    /**
     * ��֤�����Ƿ���ȷ�ĶԻ���
     */
    protected void testPwdDial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
        View view = View.inflate(mContext, R.layout.dialog_test_pwd, null);
        final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        Button bn_cancel = (Button) view.findViewById(R.id.bn_cancel);
        Button bn_conf = (Button) view.findViewById(R.id.bn_conf);
        bn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        bn_conf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_pwd.getText().toString();
                if (pwd.isEmpty()) {
                    Toast.makeText(mContext, "����Ϊ��", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Md5.md5(pwd).equals(AppConf.getFindLostPwd(mContext))) {
                    Toast.makeText(mContext, "��֤�ɹ�", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    if (AppConf.getsetIsSetup(mContext)) {
                        intent.setClass(mContext, LostFindActivity.class);
                    } else {
                        intent.setClass(mContext, Setup1Activity.class);
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "�������벻��ȷ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);
        dialog = builder.show();
    }

    /**
     * ��ʾ��������Ի���
     */
    private void settingPwdDial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
        View view = View.inflate(mContext, R.layout.dialog_setting_pwd, null);
        final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        final EditText et_pwd_conf = (EditText) view.findViewById(R.id.et_pwd_conf);
        Button bn_cancel = (Button) view.findViewById(R.id.bn_cancel);
        Button bn_conf = (Button) view.findViewById(R.id.bn_conf);
        bn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        bn_conf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_pwd.getText().toString();
                String pwd_conf = et_pwd_conf.getText().toString();
                if (pwd.isEmpty()) {
                    Toast.makeText(mContext, "����Ϊ��", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd_conf.isEmpty()) {
                    Toast.makeText(mContext, "�ٴ���������Ϊ��", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(pwd_conf)) {
                    Toast.makeText(mContext, "������������벻һ��", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppConf.setFindLostPwd(mContext, Md5.md5(pwd));
                Toast.makeText(mContext, "���óɹ�", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.show();
    }

    /**
     * ͼ����ʾ������
     */
    class IconListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return app_name.length;
        }

        @Override
        public Object getItem(int position) {
            return app_name[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeAcitvity.this, R.layout.home_item_list, null);
            ImageView iv_item_pic = (ImageView) view.findViewById(R.id.iv_item_pic);
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            iv_item_pic.setImageResource(app_icon[position]);
            tv_item_name.setText(app_name[position]);
            return view;
        }

    }
}
