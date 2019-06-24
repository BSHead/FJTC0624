package com.foorich.auscashier.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foorich.auscashier.R;
import com.foorich.auscashier.activity.AboutActivity;
import com.foorich.auscashier.activity.AccountAecurityActivity;
import com.foorich.auscashier.activity.FailApplyActivity;
import com.foorich.auscashier.activity.GatheringCodeActivity;
import com.foorich.auscashier.activity.IinformationActivity;
import com.foorich.auscashier.activity.MessageActivity;
import com.foorich.auscashier.activity.RechargeRecordActivity;
import com.foorich.auscashier.activity.ShareActivity;
import com.foorich.auscashier.activity.StatementActivity;
import com.foorich.auscashier.activity.WaitingActivity;
import com.foorich.auscashier.api.ApiService;
import com.foorich.auscashier.api.HttpManager;
import com.foorich.auscashier.base.BaseApplication;
import com.foorich.auscashier.base.BaseFragment;
import com.foorich.auscashier.bean.Function;
import com.foorich.auscashier.bean.MessageList;
import com.foorich.auscashier.utils.AlertDialog;
import com.foorich.auscashier.utils.EncrypMD5;
import com.foorich.auscashier.utils.EncrypSHA;
import com.foorich.auscashier.utils.GlideRoundTransformUtil;
import com.foorich.auscashier.utils.LinkStringByGet;
import com.foorich.auscashier.utils.ToastUitl;
import com.foorich.auscashier.view.SetListViewHeight;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-3-11 14:53
 * desc   : 我的页面
 * version: 1.0
 */

public class PersonalFragment extends BaseFragment {

    // 图片的文字标题 
    private String[] titles = new String[]{"用户充值记录", "融资记录", "设置", "分享给好友", "关于"};
    // 图片ID数组 
    private int[] images = new int[]{R.mipmap.wd_yhcz, R.mipmap.wd_rzjl, R.mipmap.wd_zhaq, R.mipmap.wd_fxghy, R.mipmap.wd_gywm};

    //上下文
    public Context mContext;
    //返回
    @BindView(R.id.toolbar_right)
    ImageView mRight;
    //功能列表
    @BindView(R.id.lv_per)
    ListView listView;
    //头像
    @BindView(R.id.img_per_pic)
    ImageView mPerPic;
    //商户名称
    @BindView(R.id.tv_per_name)
    TextView mPerName;
    //商户手机号
    @BindView(R.id.tv_per_phone)
    TextView mPerPhone;
    //弹框
    private AlertDialog myDialog;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initPresenter() {
        //接收广播消息
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refresh");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        mPerName.setText(BaseApplication.getnickName());
        Glide.with(this).load(BaseApplication.getheadImgUrl()).transform(new GlideRoundTransformUtil(getActivity())).into(mPerPic);
        mPerPhone.setText(BaseApplication.getloginName());

    }

    @Override
    protected void initView() {
        mContext = getActivity();
        myDialog = new AlertDialog(mContext).builder();
        mRight.setVisibility(View.VISIBLE);
        Jurisdiction();
        initUpdateFlag();
    }


    //多个控件具有相同的点击事件
    @OnClick({R.id.toolbar_right, R.id.li_financing, R.id.li_print, R.id.Li_people})
    public void Click(View view) {
        switch (view.getId()) {

            case R.id.toolbar_right://消息
                if (BaseApplication.getstatus().equals("0")) {//审核成功
                    startActivity(MessageActivity.class);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }

                break;

            case R.id.li_financing://融资成功
                if (BaseApplication.getstatus().equals("0")) {//审核成功
                    ToastUitl.show("融资成功", 0);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }
                break;

            case R.id.li_print:
                if (BaseApplication.getstatus().equals("0")) {//审核成功
//                    ToastUitl.show("正在打印收款码", 0);
                    startActivity(GatheringCodeActivity.class);
                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
                        }
                    }).show();
                } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                    startActivity(FailApplyActivity.class);
                } else {//申请商户
                    SetMerchant();
                }

                break;


            case R.id.Li_people://个人信息
                startActivity(IinformationActivity.class);
//                if (BaseApplication.getstatus().equals("0")) {//审核成功
//                    startActivity(IinformationActivity.class);
//                } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
//                    myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("申请", R.color.orange, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    }).show();
//                } else if (BaseApplication.getstatus().equals("2")) {//用户审核中
//                    myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("申请", R.color.orange, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(WaitingActivity.class);
//                        }
//                    }).show();
//                } else if (BaseApplication.getstatus().equals("3")) {//商户审核拒绝
//                    startActivity(FailApplyActivity.class);
//                } else {//申请商户
//                    SetMerchant();
//                }

                break;
        }
    }


    //权限管理
    private void Jurisdiction() {

        FunctionAdapter adapter = new FunctionAdapter(titles, images,
                getActivity());
        listView.setAdapter(adapter);
        SetListViewHeight.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://用户充值记录
                        if (BaseApplication.getstatus().equals("0")) {//审核成功
                            startActivity(RechargeRecordActivity.class);
                        } else if (BaseApplication.getstatus().equals("1")) {//用户以冻结
                            myDialog.setGone().setCancelable(false).setMsg("您的用户以冻结!").setPositiveButton("联系管理员", R.color.orange, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        } else if (BaseApplication.getstatus().equals("3")) {//用户审核中
                            myDialog.setGone().setCancelable(false).setMsg("商户审核中，请耐心等待").setPositiveButton("知道了", R.color.orange, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    startActivity(WaitingActivity.class);
                                }
                            }).show();
                        } else if (BaseApplication.getstatus().equals("2")) {//商户审核拒绝
                            startActivity(FailApplyActivity.class);
                        } else {//申请商户
                            SetMerchant();
                        }

                        break;

                    case 1://
                        ToastUitl.show("融资记录", 0);
                        break;

                    case 2://账户安全
                        startActivity(AccountAecurityActivity.class);
                        break;

                    case 3://分享给好友
                        startActivity(ShareActivity.class);
                        break;
                    case 4://
                        ToastUitl.show("关于", 0);
                        startActivity(AboutActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
    }


    //功能内部类
    class FunctionAdapter extends BaseAdapter {

        private List<Function> functions;
        private LayoutInflater inflater;

        public FunctionAdapter(String[] titles, int[] images, Context context) {
            super();
            functions = new ArrayList<Function>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < images.length; i++) {
                Function function = new Function(titles[i], images[i]);
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
                convertView = inflater.inflate(R.layout.layout_function_item, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.tv_per_content);
                viewHolder.imageid = (ImageView) convertView
                        .findViewById(R.id.img_per_pic);
                convertView.setTag(viewHolder);
                viewHolder.imageid.setVisibility(View.VISIBLE);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.title
                    .setText(functions.get(position).getTitle());
            viewHolder.imageid.setImageResource(functions.get(position)
                    .getImageId());


            return convertView;
        }

        class ViewHolder {
            public TextView title;
            public TextView version;
            public ImageView imageid;

        }
    }


    //申请成为商户
    public void SetMerchant() {

        myDialog.setGone().setCancelable(false).setMsg("您要先开通商户，才能使用该公能哦，快去开通吧！").setNegativeButton("谢谢，不了", R.color.black, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }).setPositiveButton("申请", R.color.orange, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(SetPaymentPdActivity.class);
            }
        }).show();
    }


    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refresh")) {
                mPerName.setText(BaseApplication.getnickName());
                Glide.with(getActivity()).load(BaseApplication.getheadImgUrl()).transform(new GlideRoundTransformUtil(getActivity())).into(mPerPic);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
    }


    public void initUpdateFlag() {
        //提交后台参数
        JSONObject json = new JSONObject();
        try {

            //data里面参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Integer.parseInt(BaseApplication.getuserId()));
            jsonObject.put("usertype", "0");

            //拆分data里面key、value进行拼接
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", BaseApplication.getuserId());
            map.put("usertype", "0");
            map.put("key", BaseApplication.gettoken());
            //SHA1加密拼接好的key、value
            EncrypSHA sha = new EncrypSHA();
            String sha1 = sha.hexString(sha.eccryptSHA1(LinkStringByGet.createLinkStringByGet(map)));
            //MD5在次加密SHA1
            EncrypMD5 md5 = new EncrypMD5();
            byte[] resultBytes = md5.eccrypt(sha1);
            String md52 = EncrypMD5.hexString(resultBytes);
            //最终提交参数
            json.put("appVersion", "1.0");
            json.put("data", jsonObject);
            json.put("language", "zh");
            json.put("sign", md52);
            json.put("tenant", "platform");
            json.put("terminal", "Android");
            json.put("token", BaseApplication.gettoken());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        //请求接口
        HttpManager.getHttpManager().postMethod(ApiService.getMessageNum, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        if (e.getMessage().equals("connect timed out")) {
//                            ToastUitl.show("网络连接超时", 0);
//                        } else if (e.getMessage().contains("Failed to connect to")) {
//                            ToastUitl.show("服务器异常,请重试", 0);
//                        } else {
//                            ToastUitl.show("服务器异常,请重试", 0);
//                        }
                    }

                    @Override
                    public void onNext(String json) {
                        Gson gson = new Gson();
                        MessageList messageList = gson.fromJson(json, MessageList.class);
                        if (messageList.getRetCode().equals("SUCCESS")) {
                            if (messageList.getData().getJiaoYiIsRead().equals("false")&&messageList.getData().getXiTongIsRead().equals("false")&&messageList.getData().getJiaoYiIsRead().equals("false")) {
                                mRight.setImageResource(R.mipmap.sy_xx);
                            }else{
                                mRight.setImageResource(R.mipmap.sy_xx);
                            }

                        } else {

                        }
                    }
                }, body
        );
    }

}
