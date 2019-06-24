package com.foorich.auscashier.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.base.BaseActivity;
import com.foorich.auscashier.bean.Function;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.state.Sofia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 20:29
 * desc   : 关于
 * version: 1.0
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.Lv_about)
    ListView listView;
    @BindView(R.id.toolbar_title)
    TextView Toolbar_title;
    @BindView(R.id.toolbar_left)
    ImageView Toolbar_left;


    // 文字标题 
    private String[] titles = new String[]{"更新版本", "去评分", "服务协议"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Toolbar_title.setOnClickListener(this);
        Toolbar_title.setVisibility(View.VISIBLE);
        Toolbar_title.setText("关于");
        Toolbar_left.setOnClickListener(this);
        Toolbar_left.setVisibility(View.VISIBLE);
        Jurisdiction();
        //设置状态栏颜色和状态栏字体颜色
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.black));
    }

    //权限管理
    private void Jurisdiction() {

        FunctionAdapter adapter = new FunctionAdapter(titles,
                this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://
                        ToastUitl.show("更新版本",0);
//                        startActivity(RechargeRecordActivity.class);
                        break;

                    case 1://
                        ToastUitl.show("去评分",0);
                        break;

                    case 2://
                        ToastUitl.show("服务协议",0);
                        break;


                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_left:
                finish();
                break;
        }
    }

    //功能内部类
    class FunctionAdapter extends BaseAdapter {

        private List<Function> functions;
        private LayoutInflater inflater;

        public FunctionAdapter(String[] titles, Context context) {
            super();
            functions = new ArrayList<Function>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < titles.length; i++) {
                Function function = new Function(titles[i]);
                functions.add(function);
            }

        }

        @Override
        public int getCount() {
            if (null != functions) {
                return functions.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return functions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_commonality, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.tv_commonality);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.title
                    .setText(functions.get(position).getTitle());
            return convertView;
        }

        class ViewHolder {
            public TextView title;
        }
    }
}
