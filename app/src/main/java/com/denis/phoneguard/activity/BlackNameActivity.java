package com.denis.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.denis.phoneguard.R;
import com.denis.phoneguard.bean.BlackName;
import com.denis.phoneguard.db.dao.BlackNameDao;

import java.util.List;
import java.util.Random;


public class BlackNameActivity extends Activity {
    private static final int DATA_READY = 1; //数据加载完毕
    private TextView tv_show;
    private ListView lv_list;
    private Button bn_add;
    private Context context;
    private List<BlackName> blackNameList;
    private BNAdapter bnAdapter;
    private BlackNameDao blackNameDao;
    private AlertDialog dialog;
    private int currentPage = 1; //当前页数
    private int totalPage;//总页数
    private int count = 20; //每页显示个数
    private Message message;
    private TextView tv_page; //页数显示
    private EditText et_jumpNumber;//页面跳转


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_black_name);
        message = handler.obtainMessage();
        blackNameDao = new BlackNameDao(context);
        initUI();
        initData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_READY:
                    if (bnAdapter == null) {
                        bnAdapter = new BNAdapter();
                        lv_list.setAdapter(bnAdapter);
                        tv_page.setText(currentPage + "/" + totalPage);
                    } else {
                        bnAdapter.notifyDataSetChanged();
                        tv_page.setText(currentPage + "/" + totalPage);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void initUI() {
        tv_show = (TextView) findViewById(R.id.tv_show);
        lv_list = (ListView) findViewById(R.id.lv_list);
        bn_add = (Button) findViewById(R.id.bn_add);
        tv_page = (TextView) findViewById(R.id.tv_page);
        et_jumpNumber = (EditText) findViewById(R.id.et_jumpNumber);
        bn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dlView = View.inflate(context, R.layout.dialog_add_blackname, null);
                final EditText et_blackname = (EditText) dlView.findViewById(R.id.et_blackname);
                final CheckBox cb_phone = (CheckBox) dlView.findViewById(R.id.cb_phone);
                final CheckBox cb_sms = (CheckBox) dlView.findViewById(R.id.cb_sms);
                Button bn_cancel = (Button) dlView.findViewById(R.id.bn_cancel);
                Button bn_confirm = (Button) dlView.findViewById(R.id.bn_confirm);
                bn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                bn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String blackName = et_blackname.getText().toString().trim();
                        if (blackName.isEmpty()) {
                            Toast.makeText(context, "黑名单号码为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int phoneCheck = cb_phone.isChecked() ? 2 : 0;
                        int smsCheck = cb_sms.isChecked() ? 1 : 0;
                        if (phoneCheck + smsCheck == 0) {
                            Toast.makeText(context, "请选择拦截内容", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            int mode = phoneCheck + smsCheck;
                            boolean isSuccess = blackNameDao.addBlackName(blackName, mode);
                            if (isSuccess) {
                                blackNameList.add(new BlackName(blackName, mode));
                                bnAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                builder.setView(dlView);
                dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void initData() {
        Thread t = new Thread() {
            @Override
            public void run() {
                if (blackNameDao.getCount() < 500) {
                    for (int i = 0; i < 200; i++) {
                        Random random = new Random();
                        int j = random.nextInt(4);
                        if (j == 0) {
                            continue;
                        }
                        blackNameDao.addBlackName("1380000000" + i, j);
                    }
                }
                blackNameList = blackNameDao.findPage(currentPage, count);
                int sum = blackNameDao.getCount();
                totalPage = sum % count == 0 ? (sum / count) : (sum / count + 1);
                Log.v("Shortcut", "totalPage" + totalPage);
                handler.sendEmptyMessage(DATA_READY);
            }
        };
        t.start();
    }

    /**
     * 上一页
     */
    public void prePage(View v) {
        if (currentPage <= 1) {
            Toast.makeText(context, "已经是第一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPage--;
        initData();
    }

    /**
     * 下一页
     */
    public void nextPage(View v) {
        if (currentPage >= totalPage) {
            Toast.makeText(context, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPage++;
        initData();
    }

    /**
     * 跳转页面
     */
    public void jumpPage(View v) {
        String trimCount = et_jumpNumber.getText().toString().trim();
        if (trimCount.isEmpty()) {
            Toast.makeText(context, "输入页码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        int etJumpNumber = Integer.valueOf(trimCount);
        if (etJumpNumber <= 0 | etJumpNumber > totalPage) {
            Toast.makeText(context, "请输入正确的页数", Toast.LENGTH_SHORT).show();
            return;
        } else {
            currentPage = etJumpNumber;
            initData();
        }
    }

    class BNAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return blackNameList.size();
        }

        @Override
        public Object getItem(int i) {
            return blackNameList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = null;
            ViewHolder viewHolder;
            if (blackNameList.size() == 0) {
                tv_show.setVisibility(View.VISIBLE);
            } else {
                tv_show.setVisibility(View.GONE);
            }
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_black_name, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
                viewHolder.tv_mode = (TextView) view.findViewById(R.id.tv_mode);
                viewHolder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                view = convertView;
            }
            viewHolder.tv_phone.setText(blackNameList.get(i).getPhone());
            int mode = blackNameList.get(i).getMode();
            if (mode == 1) {
                viewHolder.tv_mode.setText("短信截拦");
            } else if (mode == 2) {
                viewHolder.tv_mode.setText("来电截拦");
            } else if (mode == 3) {
                viewHolder.tv_mode.setText("来电截拦+短信截拦");
            }
            final BlackName blackName = blackNameList.get(i);
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = blackName.getPhone();
                    boolean isOK = blackNameDao.deleteBlackName(phone);
                    if (isOK) {
                        blackNameList=blackNameDao.findPage(currentPage, count);
                        bnAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        TextView tv_phone;
        TextView tv_mode;
        ImageView iv_delete;
    }
}
