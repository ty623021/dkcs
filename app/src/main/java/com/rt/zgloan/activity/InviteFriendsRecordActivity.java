package com.rt.zgloan.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.InviteFriendsRecordAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.InviteFriendsRecordInfo;
import com.rt.zgloan.bean.InviteFriendsRecordSonInfo;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.LoadingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * 我的邀请记录页面
 * Created by hjy on 2017/8/25.
 */

public class InviteFriendsRecordActivity extends BaseActivity<InviteFriendsRecordInfo> implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_invite_people)
    TextView tv_invite_people;
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    @BindView(R.id.no_record)
    LinearLayout no_record;
    @BindView(R.id.linear_no_net)
    LinearLayout linear_no_net;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    private List<InviteFriendsRecordSonInfo> info = new ArrayList<>();
    private InviteFriendsRecordAdapter adapter;
    private int str_invite_people;
    private int totalPages;
    private boolean isRefresh = true;
    private int page = 1;


    @Override
    public Observable<BaseResponse<InviteFriendsRecordInfo>> initObservable() {

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("id", SpUtil.getString(SpUtil.userId));
        mapParams.put("p", page + "");
        return HttpManager.getApi().inviteFriendsList(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_friends_record;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("我的邀请记录");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

//        adapter = new InviteFriendsRecordAdapter(info);
        recyclerView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);


    }

    @Override
    public void recordSuccess(InviteFriendsRecordInfo recordInfo) {
        setHttpData(recordInfo);
        AbRefreshUtil.hintView(adapter, false, linear_no_net, no_record);

    }


    private void setHttpData(InviteFriendsRecordInfo recordInfo) {
        info = recordInfo.getRelute();
        str_invite_people = recordInfo.getCount();
        tv_invite_people.setText(str_invite_people + "");
        totalPages = recordInfo.getTotalPages();

        if (isRefresh) {
//            adapter.setList(info);
        } else {
//            adapter.addAll(info);
        }
        AbRefreshUtil.isLoading(isRefresh, adapter.getItemCount(), str_invite_people, pull);


    }

    /**
     * 下拉刷新所调用的请求
     */
    private void setHttp() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }
        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("id", SpUtil.getString(SpUtil.userId));
        mapParams.put("p", page + "");
        mPresenter.toSubscribe(
                HttpManager.getApi().inviteFriendsList(mapParams), new HttpSubscriber<InviteFriendsRecordInfo>() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在刷新...");
                    }

                    @Override
                    protected void _onNext(InviteFriendsRecordInfo recordInfo) {
                        setHttpData(recordInfo);
//                        Log.e("loanListHttp", JSON.toJSONString(recordInfo.getRelute()));
                        AbRefreshUtil.hintView(adapter, false, linear_no_net, no_record);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);
                        AbRefreshUtil.hintView(adapter, true, linear_no_net, no_record);
                    }

                    @Override
                    protected void _onCompleted() {
                        LoadingFragment.getInstends().dismiss();
                    }
                }
        );

    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        setHttp();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        setHttp();
    }
}
