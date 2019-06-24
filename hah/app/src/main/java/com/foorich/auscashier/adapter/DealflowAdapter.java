package com.foorich.auscashier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foorich.auscashier.R;
import com.foorich.auscashier.bean.DealflowBean;
import com.foorich.auscashier.utils.GlideRoundTransformUtil;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-4 14:38
 * desc   : 流水详情
 * version: 1.0
 */
public class DealflowAdapter extends RecyclerView.Adapter<DealflowAdapter.ViewHolder> {


    public Context mContext;
    List<DealflowBean.DataBean.ItemsBean.Item0Bean.OrderListBean> result;

    private OnItemClickListener mOnItemClickListener = null;

    public DealflowAdapter(List<DealflowBean.DataBean.ItemsBean.Item0Bean.OrderListBean> list, Context mContext) {
        this.result = list;
        this.mContext = mContext;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DealflowBean.DataBean.ItemsBean.Item0Bean.OrderListBean bean = result.get(position);
        Glide.with(holder.img.getContext()).load(bean.getPicture())
                .transform(new GlideRoundTransformUtil(mContext)).into((holder.img));

        holder.title.setText(bean.getPayType());
        holder.orderNum.setText("单号："+bean.getOrderNum());
        holder.time.setText(bean.getCreateTime());
        holder.price.setText(bean.getAmt());
        holder.tradeState.setText(""+bean.getTradeState());



        final int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(finalPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }


    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {

        //行布局中的控件
        TextView title;// 标题
        TextView orderNum;// 订单号
        TextView time; // 订单时间
        TextView price; // 交易金额
        TextView tradeState; // 交易状态
        ImageView img;// 头像


        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            title = (TextView) itemView.findViewById(R.id.tv_detail_title);
            orderNum = (TextView) itemView.findViewById(R.id.tv_detail_orderNum);
            time = (TextView) itemView.findViewById(R.id.tv_detail_time);
            price = (TextView) itemView.findViewById(R.id.tv_detail_price);
            tradeState = (TextView) itemView.findViewById(R.id.tv_detail_tradeState);
            img = (ImageView) itemView.findViewById(R.id.img_detail_picture);

        }
    }

    //设置一个Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



//    public void replaceAll(List<BillBean.DataBean.ItemsBean.Item0Bean.TransferRecordBean> list) {
//        this.result = list;
//        notifyDataSetChanged();
//    }




}
