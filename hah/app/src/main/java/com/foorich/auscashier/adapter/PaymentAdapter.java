package com.foorich.auscashier.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.bean.PaymentBean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-12 16:15
 * desc   : 报表支付方式适配器
 * version: 1.0
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {


    public Context mContext;
    List<PaymentBean.DataBean> result;

    private AllMerchantAdapter.OnItemClickListener mOnItemClickListener = null;

    public PaymentAdapter(List<PaymentBean.DataBean> list, Context mContext) {
        this.result = list;
        this.mContext = mContext;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PaymentBean.DataBean bean = result.get(position);
        holder.payTypeDesc.setText(bean.getPayTypeDesc());
        holder.ratio.setText(bean.getRatio());
        //设置后台传过来颜色（kank）
        holder.color.setColorFilter(Color.parseColor(String.valueOf(bean.getColor())));


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
        TextView payTypeDesc;//qq钱包
        ImageView color;//颜色
        TextView ratio;//%


        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            payTypeDesc = (TextView) itemView.findViewById(R.id.tv_payment_payTypeDesc);
            color = (ImageView) itemView.findViewById(R.id.img_payment_color);
            ratio = (TextView) itemView.findViewById(R.id.tv_payment_ratio);
        }
    }


    //设置一个Listener
    public void setOnItemClickListener(AllMerchantAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
