package com.rt.zgloan.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.bean.CreditCardBean;

import java.util.List;


/**
 * Created by Administrator on 2017/12/25 0025.
 * 信用卡首页适配器
 */

public class CreditCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TYPE1 = 1;//今日推荐
    public static final int TYPE_TYPE2 = 2;//按银行卡选择
    public static final int TYPE_TYPE3 = 3;//按用途选择
    public static final int TYPE_TYPE4 = 4;//按主题选择
    public static final int TYPE_TYPE5 = 5;//热门推荐

    private Context mContext;
    private List<CreditCardBean> list;

    public CreditCardAdapter(Context mContext, List<CreditCardBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderType(LayoutInflater.from(mContext).inflate(R.layout.item_credit_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderType) {
            bindType((HolderType) holder, position);
        }
    }

    private void bindType(HolderType holder, int position) {
        CreditCardBean creditCardBean = list.get(position);
        int itemViewType = creditCardBean.getType();
        if (itemViewType == TYPE_TYPE1) {
            holder.mTvLabel.setText("今日推荐");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
        } else if (itemViewType == TYPE_TYPE2) {
            holder.mTvLabel.setText("按银行卡选择");
            holder.item_recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        } else if (itemViewType == TYPE_TYPE3) {
            holder.mTvLabel.setText("按用途选择");
            holder.item_recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        } else if (itemViewType == TYPE_TYPE4) {
            holder.mTvLabel.setText("按主题选择");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
        } else if (itemViewType == TYPE_TYPE5) {
            holder.mTvLabel.setText("热门推荐");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
//        Glide.with(mContext)
//                .load(list.get(position).getImage_url())
//                .placeholder(R.drawable.image_default)
//                .error(R.drawable.image_default)
//                .centerCrop()
//                .into(holder.mIvLabel);//设置图片
        ItemRecycleAdapter itemRecycleAdapter = new ItemRecycleAdapter(itemViewType, creditCardBean.getList());
        holder.item_recyclerView.setAdapter(itemRecycleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderType extends RecyclerView.ViewHolder {
        private ImageView mIvLabel;
        private TextView mTvLabel;
        private RecyclerView item_recyclerView;

        public HolderType(View itemView) {
            super(itemView);
            item_recyclerView = (RecyclerView) itemView.findViewById(R.id.item_recyclerView);
            mIvLabel = (ImageView) itemView.findViewById(R.id.iv_label);
            mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            item_recyclerView.setNestedScrollingEnabled(false);
            item_recyclerView.setFocusable(false);
        }
    }

    private class ItemRecycleAdapter extends RecyclerView.Adapter {
        private int type;
        private List data;

        public ItemRecycleAdapter(int type, List data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (type) {
                case TYPE_TYPE1:
                    return new HolderType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card_item1, parent, false));
                case TYPE_TYPE2:
                    return new HolderType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card_item2, parent, false));
                case TYPE_TYPE3:
                    return new HolderType3(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card_item3, parent, false));
                case TYPE_TYPE4:
                    return new HolderType4(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card_item4, parent, false));
                case TYPE_TYPE5:
                    return new HolderType5(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card_item5, parent, false));
                default:
                    return null;
            }

        }

        /**
         * 今日推荐
         */
        class HolderType1 extends RecyclerView.ViewHolder {

            HolderType1(View itemView) {
                super(itemView);
            }
        }

        /**
         * 按银行卡选择
         */
        class HolderType2 extends RecyclerView.ViewHolder {
            ImageView ivImg;
            TextView tvName;
            TextView tvDes;
            View itemView;

            HolderType2(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
                this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
                this.tvDes = (TextView) itemView.findViewById(R.id.tv_des);
            }
        }

        /**
         * 按用途选择
         */
        class HolderType3 extends RecyclerView.ViewHolder {

            HolderType3(View itemView) {
                super(itemView);

            }
        }

        /**
         * 按主题选择
         */
        class HolderType4 extends RecyclerView.ViewHolder {

            HolderType4(View itemView) {
                super(itemView);

            }
        }

        /**
         * 按热门选择
         */
        class HolderType5 extends RecyclerView.ViewHolder {

            HolderType5(View itemView) {
                super(itemView);

            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HolderType1) {
                bindType1((HolderType1) holder, position);
            } else if (holder instanceof HolderType2) {
                bindType2((HolderType2) holder, position);
            } else if (holder instanceof HolderType3) {
                bindType3((HolderType3) holder, position);
            } else if (holder instanceof HolderType4) {
                bindType4((HolderType4) holder, position);
            } else if (holder instanceof HolderType5) {
                bindType5((HolderType5) holder, position);
            }
        }

        private void bindType1(HolderType1 holder, int position) {

        }

        private void bindType2(HolderType2 holder, int position) {
            if (position == 5) {
                holder.tvName.setText("全部银行>>");
                holder.tvDes.setVisibility(View.GONE);
            } else {
                holder.tvDes.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void bindType3(HolderType3 holder, int position) {

        }

        private void bindType4(HolderType4 holder, int position) {

        }

        private void bindType5(HolderType5 holder, int position) {

        }


    }
}
