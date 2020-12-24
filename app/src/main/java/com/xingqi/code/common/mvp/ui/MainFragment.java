package com.xingqi.code.common.mvp.ui;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.xingqi.code.common.R;
import com.xingqi.code.common.mvp.contract.HotKeyWordContract;
import com.xingqi.code.common.mvp.model.HotKeyWordModel;
import com.xingqi.code.common.mvp.model.entity.HotKeyWord;
import com.xingqi.code.common.mvp.presenter.HotKeyWordPresenter;
import com.xingqi.code.commonlib.base.BaseFragment;
import com.xingqi.code.commonlib.entity.EventMessage;
import com.xingqi.code.commonlib.manager.LoadingDialogManager;
import com.xingqi.code.commonlib.utils.CommonUtils;
import com.xingqi.code.commonlib.utils.EventBusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment<HotKeyWordPresenter> implements HotKeyWordContract.View {
    @BindView(R.id.btn_send_sms)
    Button btnSendSms;

    @Override
    public int statusBarColor() {
        return R.color.colorPrimary;
    }

    @BindView(R.id.send_msg_btn)
    Button sendMsgBtn;
    @BindView(R.id.jump_to_page)
    Button jumpToPage;
    @BindView(R.id.jump_to_complex)
    Button jumpToComplex;
    @BindView(R.id.jump_to_image)
    Button jumpToImage;
    @BindView(R.id.jump_to_indicator)
    Button jumpToIndicator;
    @BindView(R.id.jump_to_tab)
    Button jumpToTab;
    @BindView(R.id.jump_to_option)
    Button jumpToOption;
    @BindView(R.id.jump_to_page_food)
    Button jumpToPageFood;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void release() {

    }


    @Override
    public void initData() {
        mPresenter.getHotWordList();
    }


    @Override
    public boolean hasToolbar() {
        return false;
    }

    @Override
    public String toolbarTitle() {
        return null;
    }

    @OnClick(R.id.send_msg_btn)
    public void onViewClicked() {
        EventBusUtil.post(new EventMessage(2, "sddfsdf"));
    }

    @Override
    protected HotKeyWordPresenter initPresenter() {
        return new HotKeyWordPresenter(new HotKeyWordModel(), this);
    }

    @Override
    public void showKeyWord(List<HotKeyWord> hotKeyWordList) {
        for (HotKeyWord hotKeyWord : hotKeyWordList) {
            sendMsgBtn.setText(hotKeyWord.getKeyWordText());
        }
    }

    private static final String TAG = "MainFragment";

    @Override
    public void showMessage(@NonNull String message) {
        Log.e(TAG, "showMessage: " + message);
    }

    @Override
    public void showLoading() {

        LoadingDialogManager.getInstance().showLoading(getActivity().getSupportFragmentManager());

    }

    @Override
    public void hideLoading() {
        LoadingDialogManager.getInstance().hideLoading();
    }


    @OnClick(R.id.jump_to_page)
    public void jumpToPage() {
        CommonUtils.startActivity(RecyclePaginateActivity.class);
    }

    @OnClick(R.id.jump_to_complex)
    public void jumpToComplex() {
//        CommonUtils.startActivity(ComplexActivity.class);

        CommonUtils.startActivityWithTransition(ComplexActivity.class);
    }

    @OnClick(R.id.jump_to_image)
    public void jumpToImage() {
        CommonUtils.startActivity(ImageLoaderActivity.class);
        EventBusUtil.postSticky(new EventMessage(1, "sticky"));
    }

    @OnClick(R.id.jump_to_indicator)
    public void jumpToIndicator() {
        CommonUtils.startActivity(IndicatorActivity.class);
    }

    @OnClick(R.id.jump_to_tab)
    public void jumpToTab() {
        CommonUtils.startActivity(TabActivity.class);
    }

    @OnClick(R.id.jump_to_option)
    public void jumpToOption() {
        CommonUtils.startActivity(OptionActivity.class);
    }

    @OnClick(R.id.jump_to_page_food)
    public void jumpToPageFood() {
        CommonUtils.startActivity(FoodListActivity.class);
    }

    @OnClick(R.id.btn_send_sms)
    public void onSendSms() {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("address", "123456"); //电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body","短信内容");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }
}
