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

import com.joooonho.SelectableRoundedImageView;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.LoginActivity;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.activity.creditCardActivity.BankListActivity;
import com.rt.zgloan.activity.creditCardActivity.CreditCardDetailsActivity;
import com.rt.zgloan.activity.creditCardActivity.CreditCardListActivity;
import com.rt.zgloan.bean.CreditCardBean;
import com.rt.zgloan.bean.CreditCardHomeBean;
import com.rt.zgloan.bean.CreditCardHomeListBean;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;

import java.util.List;

import static com.rt.zgloan.R.id.tv_des;


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
    private List<CreditCardHomeListBean> list;

    public CreditCardAdapter(Context mContext, List<CreditCardHomeListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_TYPE1) {
//            return new HolderType(LayoutInflater.from(mContext).inflate(R.layout.item_credit_card2, parent, false));
//        } else {
//            return new HolderType(LayoutInflater.from(mContext).inflate(R.layout.item_credit_card, parent, false));
//        }
        return new HolderType(LayoutInflater.from(mContext).inflate(R.layout.item_credit_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderType) {
            bindType((HolderType) holder, position);
        }
    }

    private void bindType(HolderType holder, int position) {
        CreditCardHomeListBean creditCardBean = list.get(position);
        int itemViewType = creditCardBean.getType();
        if (itemViewType == TYPE_TYPE1) {
            holder.mTvLabel.setText("今日推荐");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else if (itemViewType == TYPE_TYPE2) {
            holder.mTvLabel.setText("按银行选卡");
            holder.item_recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        } else if (itemViewType == TYPE_TYPE3) {
            holder.mTvLabel.setText("按用途选卡");
            holder.item_recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        } else if (itemViewType == TYPE_TYPE4) {
            holder.mTvLabel.setText("按主题选卡");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else if (itemViewType == TYPE_TYPE5) {
            holder.mTvLabel.setText("热门信用卡");
            holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
        ItemRecycleAdapter itemRecycleAdapter = new ItemRecycleAdapter(itemViewType, creditCardBean.getList());
        holder.item_recyclerView.setAdapter(itemRecycleAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderType extends RecyclerView.ViewHolder {
        private TextView mTvLabel;
        private RecyclerView item_recyclerView;

        public HolderType(View itemView) {
            super(itemView);
            item_recyclerView = (RecyclerView) itemView.findViewById(R.id.item_recyclerView);
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
            public ImageView iv_img;
            public TextView tv_name;
            public TextView tv_des;
            View itemView;

            HolderType1(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
                this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                this.tv_des = (TextView) itemView.findViewById(R.id.tv_des);
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
                this.tvDes = (TextView) itemView.findViewById(tv_des);
            }
        }

        /**
         * 按用途选择
         */
        class HolderType3 extends RecyclerView.ViewHolder {
            ImageView ivImg;
            TextView tvName;
            View itemView;

            HolderType3(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
                this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }

        /**
         * 按主题选择
         */
        class HolderType4 extends RecyclerView.ViewHolder {
            ImageView ivImg;
            TextView tvName;
            TextView tvDes;
            View itemView;

            HolderType4(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
                this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
                this.tvDes = (TextView) itemView.findViewById(R.id.tv_des);
            }
        }

        /**
         * 按热门选择
         */
        class HolderType5 extends RecyclerView.ViewHolder {
            TextView tv_title, tv_loan_number, tv_des, tv_pointsOne, tv_pointsTow, tv_labels1, tv_labels2;
            SelectableRoundedImageView ivImg;
            View itemView;

            HolderType5(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.ivImg = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_img);
                this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                this.tv_loan_number = (TextView) itemView.findViewById(R.id.tv_loan_number);
                this.tv_des = (TextView) itemView.findViewById(R.id.tv_des);
                this.tv_pointsOne = (TextView) itemView.findViewById(R.id.tv_pointsOne);
                this.tv_pointsTow = (TextView) itemView.findViewById(R.id.tv_pointsTow);
                this.tv_labels1 = (TextView) itemView.findViewById(R.id.tv_labels1);
                this.tv_labels2 = (TextView) itemView.findViewById(R.id.tv_labels2);
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
            final CreditCardBean info = (CreditCardBean) data.get(position);
            holder.tv_name.setText(info.getName() + "");
            holder.tv_des.setText(info.getSummary() + "");
            AbImageUtil.glideImageList(info.getImg(), holder.iv_img, R.mipmap.error_img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0".equals(info.getShowType())) {
                        CreditCardDetailsActivity.startActivity(mContext, info.getId() + "");
                    } else if ("1".equals(info.getShowType())) {
                        if (SpUtil.getBoolean(SpUtil.isLogin)) {
                            if (!AbStringUtil.isEmpty(info.getLinkUrl())) {
                                WebViewActivity.startActivity(mContext, info.getLinkUrl());
                            }
                        } else {
                            ((MainActivity) mContext).startActivity(LoginActivity.class);
                        }
                    }
                }
            });
        }

        private void bindType2(HolderType2 holder, final int position) {
            final CreditCardHomeBean.BankBean info = (CreditCardHomeBean.BankBean) data.get(position);
            if (position == data.size() - 1) {
                holder.tvName.setText("全部银行>>");
                holder.tvDes.setVisibility(View.GONE);
                holder.ivImg.setImageResource(R.mipmap.all_bank_icon);
            } else {
                holder.tvName.setText(info.name);
                holder.tvDes.setText(info.summary);
                holder.tvDes.setVisibility(View.VISIBLE);
                AbImageUtil.glideImageList(info.logo, holder.ivImg, R.mipmap.error_img);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == data.size() - 1) {
                        BankListActivity.startActivity(mContext, info.id + "");
                    } else {
                        CreditCardListActivity.startActivity(mContext, info.id + "", CreditCardListActivity.TYPE_BANK);
                    }
                }
            });
        }

        private void bindType3(HolderType3 holder, int position) {
            final CreditCardHomeBean.PurposeBean info = (CreditCardHomeBean.PurposeBean) data.get(position);
            holder.tvName.setText(info.name);
            AbImageUtil.glideCircleImage(info.logo, holder.ivImg, R.mipmap.error_img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreditCardListActivity.startActivity(mContext, info.id + "", CreditCardListActivity.TYPE_PURPOSE);
                }
            });
        }

        private void bindType4(HolderType4 holder, int position) {
            final CreditCardHomeBean.SubjectBean info = (CreditCardHomeBean.SubjectBean) data.get(position);
            holder.tvName.setText(info.name);
            holder.tvDes.setText(info.summary);
            AbImageUtil.glideImageList(info.logo, holder.ivImg, R.mipmap.error_img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreditCardListActivity.startActivity(mContext, info.id + "", CreditCardListActivity.TYPE_SUBJECT);
                }
            });
        }

        private void bindType5(HolderType5 holder, int position) {
            final CreditCardBean info = (CreditCardBean) data.get(position);
            holder.tv_title.setText(info.getName());
            holder.tv_loan_number.setText(info.getApplicants() + "人");
            holder.tv_des.setText(info.getSummary());
            holder.tv_pointsOne.setText(info.getPointsOne());
            holder.tv_pointsTow.setText(info.getPointsTwo());
            if (AbStringUtil.isEmpty(info.getLabelsOne())) {
                holder.tv_labels1.setVisibility(View.GONE);
            } else {
                holder.tv_labels1.setVisibility(View.VISIBLE);
                holder.tv_labels1.setText(info.getLabelsOne());
            }
            if (AbStringUtil.isEmpty(info.getLabelsTwo())) {
                holder.tv_labels2.setVisibility(View.GONE);
            } else {
                holder.tv_labels2.setVisibility(View.VISIBLE);
                holder.tv_labels2.setText(info.getLabelsTwo());
            }
            AbImageUtil.glideImageList(info.getImg(), holder.ivImg, R.mipmap.credit_card_default);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0".equals(info.getShowType())) {
                        CreditCardDetailsActivity.startActivity(mContext, info.getId() + "");
                    } else if ("1".equals(info.getShowType())) {
                        if (SpUtil.getBoolean(SpUtil.isLogin)) {
                            if (!AbStringUtil.isEmpty(info.getLinkUrl())) {
                                WebViewActivity.startActivity(mContext, info.getLinkUrl());
                            }
                        } else {
                            ((MainActivity) mContext).startActivity(LoginActivity.class);
                        }
                    }
                }
            });
        }

    }
}
