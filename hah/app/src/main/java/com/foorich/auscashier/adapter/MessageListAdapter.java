package com.foorich.auscashier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;
import com.foorich.auscashier.bean.MessageListBean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-6-10 11:10
 * desc   : 消息列表适配器
 * version: 1.0
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {


    public Context mContext;
    List<MessageListBean.DataBean.MessageArrayBean> result;

    private OnItemClickListener mOnItemClickListener = null;

    public MessageListAdapter(List<MessageListBean.DataBean.MessageArrayBean> list, Context mContext) {
        this.result = list;
        this.mContext = mContext;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MessageListBean.DataBean.MessageArrayBean messageArrayBean = result.get(position);
        holder.Messagefirsttitle.setText(messageArrayBean.getMessagefirsttitle());
        holder.CreatTime.setText(messageArrayBean.getCreatTime());
        holder.Message.setText(messageArrayBean.getMessage());

        String id = String.valueOf(messageArrayBean.getIsRead());
        if (id.equals("1")) {
            holder.MessageId.setVisibility(View.VISIBLE);
        } else {
            holder.MessageId.setVisibility(View.INVISIBLE);
        }


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
        TextView Messagefirsttitle;// 标题
        TextView CreatTime;// 时间
        TextView Message; //消息
        ImageView MessageId; //图标

        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            Messagefirsttitle = (TextView) itemView.findViewById(R.id.tv_message_title);
            MessageId = (ImageView) itemView.findViewById(R.id.tv_message_img);
            CreatTime = (TextView) itemView.findViewById(R.id.tv_message_time);
            Message = (TextView) itemView.findViewById(R.id.tv_message_message);

        }
    }


    //设置一个Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
