package com.denis.phoneguard.activity;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;
import com.denis.phoneguard.service.AddrService;
import com.denis.phoneguard.view.StyleRelativeLayout;
import com.denis.phoneguard.view.UpdateRelativeLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 设置activity
 *
 * @author yongda
 */
public class SettingActivity extends Activity {
    private UpdateRelativeLayout ur_auto_update;
    private UpdateRelativeLayout rl_show_addr;
    private StyleRelativeLayout rl_show_style;
    private StyleRelativeLayout rl_show_location;
    private Context mContext;
    private CharSequence[] styles = {"卫士蓝", "半透明", "活力橙", "金属灰", "苹果绿"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_setting);
        ur_auto_update = (UpdateRelativeLayout) findViewById(R.id.ur_auto_update);
        rl_show_addr = (UpdateRelativeLayout) findViewById(R.id.rl_show_addr);
        rl_show_style = (StyleRelativeLayout) findViewById(R.id.rl_show_style);
        rl_show_location = (StyleRelativeLayout) findViewById(R.id.rl_show_location);
        if (AppConf.getAutoUpdate(mContext)) {
            ur_auto_update.setChecked(true);
            ur_auto_update.setDetail("自动更新已经开启");
        } else {
            ur_auto_update.setChecked(false);
            ur_auto_update.setDetail("自动更新已经关闭");
        }
        ur_auto_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ur_auto_update.isChecked()) {
                    ur_auto_update.setChecked(false);
                    ur_auto_update.setDetail("自动更新已经关闭");
                    AppConf.setAutoUpdate(mContext, false);
                } else {
                    ur_auto_update.setChecked(true);
                    ur_auto_update.setDetail("自动更新已经开启");
                    AppConf.setAutoUpdate(mContext, true);
                }
            }
        });
        if (AppConf.getShowAddr(mContext)) {
            rl_show_addr.setDetail("归属地显示已经开启");
            rl_show_addr.setChecked(true);
            startService(new Intent(mContext, AddrService.class));
        } else {
            rl_show_addr.setDetail("归属地显示已经关闭");
            rl_show_addr.setChecked(false);
        }
        rl_show_addr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rl_show_addr.isChecked()) {
                    rl_show_addr.setChecked(false);
                    rl_show_addr.setDetail("归属地显示已经关闭");
                    AppConf.setShowAddr(mContext, false);
                    startService(new Intent(mContext, AddrService.class));
                } else {
                    rl_show_addr.setChecked(true);
                    rl_show_addr.setDetail("归属地显示已经开启");
                    AppConf.setShowAddr(mContext, true);
                    stopService(new Intent(mContext, AddrService.class));
                }
            }
        });
        int style = AppConf.getShowStyle(mContext);
        rl_show_style.setDetail(styles[style].toString());
        rl_show_style.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setSingleChoiceItems(styles, AppConf.getShowStyle(mContext),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppConf.setShowStyle(mContext, which);
                                rl_show_style.setDetail(styles[which].toString());
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        rl_show_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ShowLocationActivity.class);
                startActivity(intent);
            }
        });
    }
}
