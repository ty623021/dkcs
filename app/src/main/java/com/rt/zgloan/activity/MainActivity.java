package com.rt.zgloan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.db.UserActionDao;
import com.rt.zgloan.dbentity.UserAction;
import com.rt.zgloan.dbentity.UserActionList;
import com.rt.zgloan.fragment.FragmentCreditCard;
import com.rt.zgloan.fragment.FragmentFirstPage;
import com.rt.zgloan.fragment.FragmentLoan;
import com.rt.zgloan.fragment.FragmentMy;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.presenter.BasePresenter;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.util.UpdateVersionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_first_page_text)
    TextView firstPageTextView;
    @BindView(R.id.tab_loan_text)
    TextView loanTextView;
    @BindView(R.id.tab_my_text)
    TextView myTextView;
    @BindView(R.id.tab_first_page_icon)
    ImageView firstPageImageView;
    @BindView(R.id.tab_loan_icon)
    ImageView loanImageView;
    @BindView(R.id.tab_my_icon)
    ImageView myImageView;
    @BindView(R.id.tab_credit_card_icon)
    ImageView tabCreditCardIcon;
    @BindView(R.id.tab_credit_card_text)
    TextView tabCreditCardText;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FragmentFirstPage fragmentFirstPage;
    private FragmentLoan fragmentLoan;
    private FragmentMy fragmentMy;
    private FragmentCreditCard fragmentCreditCard;
    private String curFragmentTag;//当前选中的fragment
    private static final String FRAGMENT_TAG_FIRSTPAGE = "firstPage";
    private static final String FRAGMENT_TAG_LOAN = "loan";
    private static final String FRAGMENT_TAG_CREDITCARD = "creditCard";
    private static final String FRAGMENT_TAG_MY = "my";

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        switchTab(0);
        UpdateVersionUtil updateVersionUtil = new UpdateVersionUtil(this);
        updateVersionUtil.checkVersion(false);
    }

    private void initTab() {
        firstPageTextView.setSelected(false);
        loanTextView.setSelected(false);
        myTextView.setSelected(false);
        tabCreditCardText.setSelected(false);
        firstPageImageView.setSelected(false);
        loanImageView.setSelected(false);
        myImageView.setSelected(false);
        tabCreditCardIcon.setSelected(false);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentFirstPage != null) {
            transaction.hide(fragmentFirstPage);
        }
        if (fragmentLoan != null) {
            transaction.hide(fragmentLoan);
        }
        if (fragmentCreditCard != null) {
            transaction.hide(fragmentCreditCard);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }
    }

    public void switchTab(int index) {
        initTab();
        transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (fragmentFirstPage == null) {
                    fragmentFirstPage = new FragmentFirstPage();
                    transaction.add(R.id.container, fragmentFirstPage, FRAGMENT_TAG_FIRSTPAGE);
                } else {
                    transaction.show(fragmentFirstPage);
                }
                curFragmentTag = FRAGMENT_TAG_FIRSTPAGE;
                firstPageTextView.setSelected(true);
                firstPageImageView.setSelected(true);
                break;
            case 1:
                if (fragmentLoan == null) {
                    fragmentLoan = new FragmentLoan();
                    transaction.add(R.id.container, fragmentLoan, FRAGMENT_TAG_LOAN);
                } else {
                    transaction.show(fragmentLoan);
                }
                curFragmentTag = FRAGMENT_TAG_LOAN;
                loanTextView.setSelected(true);
                loanImageView.setSelected(true);
                break;
            case 2:
                if (fragmentCreditCard == null) {
                    fragmentCreditCard = new FragmentCreditCard();
                    transaction.add(R.id.container, fragmentCreditCard, FRAGMENT_TAG_CREDITCARD);
                } else {
                    transaction.show(fragmentCreditCard);
                }
                curFragmentTag = FRAGMENT_TAG_CREDITCARD;
                tabCreditCardText.setSelected(true);
                tabCreditCardIcon.setSelected(true);
                break;
            case 3:
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.add(R.id.container, fragmentMy, FRAGMENT_TAG_MY);
                } else {
                    transaction.show(fragmentMy);
                }
                curFragmentTag = FRAGMENT_TAG_MY;
                myTextView.setSelected(true);
                myImageView.setSelected(true);
                break;

        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    public Observable<BaseResponse> initObservable() {
        mapParams.put("channelOid", "8a7df1b15cfcfa6f015cfd6d8d52054b");
        mapParams.put("page", String.valueOf(1));
//        mapParams.put("rows", "10");
//        mapParams.put("sort", "expAror");
//        mapParams.put("order", "desc");
        return HttpManager.getApi().request(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @OnClick({R.id.first_page, R.id.loan, R.id.credit_card, R.id.my})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_page:
                switchTab(0);
                break;
            case R.id.loan:
                switchTab(1);
                break;
            case R.id.credit_card:
                switchTab(2);
                break;
            case R.id.my:
                switchTab(3);
                break;
        }

    }


    @Override
    public void showLoading(String content) {
    }


    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {
    }


    // 再次点击返回键退出
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再次点击返回键退出");
                exitTime = System.currentTimeMillis();
            } else {

                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int currPager = intent.getIntExtra("currPager", 0);
            switchTab(currPager);
        }
    }

    /**
     * 跳转到 MainActivity
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startMainActivity(Activity context, int currPager) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("currPager", currPager);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        List<UserAction> list = UserActionDao.getInstance(this).qureAll();
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            return;
        }

        UserActionList userActionList = new UserActionList();
        userActionList.setData(list);

        mapParams.clear();
        if (!TextUtils.isEmpty(SpUtil.getString(SpUtil.userId))) {
            mapParams.put("uid", SpUtil.getString(SpUtil.userId));
        }
        if (!TextUtils.isEmpty(SpUtil.getString(SpUtil.mobile))) {
            mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        }
        mapParams.put("content", JSON.toJSONString(list));
        BasePresenter.getInstence().toSubscribe(HttpManager.getApi().getBehaviorOperation(mapParams), new HttpSubscriber() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(Object o) {
                UserActionDao.getInstance(MainActivity.this).clear();
            }

            @Override
            protected void _onError(String message) {

            }

            @Override
            protected void _onCompleted() {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Fragment f = fragmentManager.findFragmentByTag(curFragmentTag);
            f.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
