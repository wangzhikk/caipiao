package com.cp.touzhu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.cp.Data_wallet_remain;
import com.cp.im.IMTool;
import com.cp.im.MsgData;
import com.cp.im.MsgDataSend;
import com.cp.shouye.Data_room_queryGame;
import com.cp.touzhu.zoushitu.ZouShiTu_bj28_Fragment;
import com.cp.touzhu.zoushitu.ZouShiTu_cqssc_Fragment;
import com.cp.wode.Data_personinfo_query;

import net.openmob.mobileimsdk.android.ClientCoreSDK;
import net.openmob.mobileimsdk.android.core.KeepAliveDaemon;
import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.WebFragment;
import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.db.MapDB;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.textview.HtmlTool;

import static com.app.hubert.guide.NewbieGuide.TAG;


/**
 * Created by kk on 2017/3/23.
 */

public class TouZhuFragment extends ParentFragment {

    WzSimpleRecycleView recycleView;
    Data_room_queryGame.InfoBean.RoomLevelsBean roomsBean;
    Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean roomListBean;
    Data_room_queryGame dataRoomQueryGame;

    View btn_touzhu, btn_touzhu_lishi, vg_touzhu_yue;
    TextView tv_touzhu_dangqian_qi, tv_touzhu_daojishi;

    @Override
    public int initContentViewId() {
        return R.layout.touzhu;
    }

    @Override
    public void initData() {
        roomsBean = (Data_room_queryGame.InfoBean.RoomLevelsBean) getArgument("roomsBean", new Data_room_queryGame.InfoBean.RoomLevelsBean());
        dataRoomQueryGame = roomsBean.getGameData();
        roomListBean = (Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean) getArgument("roomListBean", new Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean());


        titleTool.setTitle(Data_room_queryGame.YouXiEnum.valueOf(roomsBean.game).name + " " + roomListBean.name);

        titleTool.setTitleRight(R.drawable.icon_bet_add, new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                View view = LayoutInflaterTool.getInflater(3, R.layout.touzhu_dialog_more).inflate();
                final Dialog dialog = new Dialog(AppTool.currActivity, R.style.dialog);
                dialog.setContentView(view);
                DialogTool.showAsDropDown(dialog, titleTool.title_right_img, 0, 10);
                final View btn_touzhu_jilu = view.findViewById(R.id.btn_touzhu_jilu);
                btn_touzhu_jilu.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        TouZhuJiLuFragment.byData(roomsBean.game, roomListBean.no).go();
                        dialog.dismiss();
                    }
                });
                view.findViewById(R.id.btn_touzhu_zoushi).setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        if (Data_room_queryGame.YouXiEnum.CQSSC_CX.value.equals(roomsBean.game)) {//重庆时时彩
                            new ZouShiTu_cqssc_Fragment().go();
                        } else if (Data_room_queryGame.YouXiEnum.BJ28.value.equals(roomsBean.game)) {//北京28
                            new ZouShiTu_bj28_Fragment().go();
                        }
                        dialog.dismiss();
                    }
                });
                view.findViewById(R.id.btn_touzhu_jieshao).setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        new WebFragment().go(HttpConfigAx.getHtmlUrl(dataRoomQueryGame.info.playHtmlSrc), "玩法说明");
                        dialog.dismiss();
                    }
                });

            }
        });
        TouZhuDialog.bindClick(this, btn_touzhu);//投注弹框相关的全在这里
        initLiShiDialog();


        refreshYuE();


        showYingDao();

        titleTool.showService();
        init();

    }



    /***
     * 不能放在destory 里面， 那里 可能比 oncreate 晚执行
     * @return
     */
    @Override
    public boolean onBackPressed() {
        IMTool.getIntance().removeMsgListener(msgHandlerListener);
        IMTool.getIntance().sendMsg(MsgDataSend.getEXITROOMMsg(),"0");
        return super.onBackPressed();
    }


    public void showYingDao() {
        if (StringTool.notEmpty(MapDB.loadObj("guid" + Data_login_validate.getData_login_validate().uuid, String.class))) {
            return;
        }
        MapDB.saveObj("guid" + Data_login_validate.getData_login_validate().uuid, "1");
        NewbieGuide.with(this)
                .setLabel("page")//设置引导层标示区分不同引导层，必传！否则报错
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        Log.e(TAG, "NewbieGuide onShowed: ");
                        //引导层显示
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        Log.e(TAG, "NewbieGuide  onRemoved: ");
                        //引导层消失（多页切换不会触发）
                    }
                })
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int page) {
                        Log.e(TAG, "NewbieGuide  onPageChanged: " + page);
                        //引导页切换，page为当前页位置，从0开始
                    }
                })
                .alwaysShow(true)//是否每次都显示引导层，默认false，只显示一次
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                .addHighLight(titleTool.vg_title_right)//添加高亮的view
                                .addHighLight(tv_yue_lb, HighLight.Shape.RECTANGLE)
                                .addHighLight(btn_touzhu)//添加高亮的view
                                .addHighLight(img_touzhu_lishi_xiala)//添加高亮的view
                                .setLayoutRes(R.layout.touzhu_yindao)//设置引导页布局
                                .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                    @Override
                                    public void onLayoutInflated(View view) {

                                    }
                                })
                )
                .show();//显示引导层(至少需要一页引导页才能显示)

    }

    TextView tv_yue_lb;
    View tv_touzhu_lishi_item, img_touzhu_lishi_xiala;

    int countDown; //-2 封盘中；  -3已停售；
    public String currIssue;

    String getCountDownStr() {
        int xiaoshi = countDown / 3600;
        int fen = countDown / 60;
        int miao = countDown % 60;

        if (xiaoshi > 0) {
            return xiaoshi + "时" + fen + "分" + miao + "秒";
        } else {
            return fen + "分" + miao + "秒";
        }
    }

    List<MsgData> msgDataList = new ArrayList<>();
    IMTool.MsgHandlerListener msgHandlerListener;

    public void init() {
        {//登录房间
            tv_touzhu_daojishi.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String daojishi = getCountDownStr();
                    if (!isDetached()) {
                        if (countDown > -1) {
                            countDown--;
                            setTextView(tv_touzhu_daojishi, daojishi);
                        }
                        tv_touzhu_daojishi.postDelayed(this, 1000);
                    }
                }
            }, 1000);
            IMTool.getIntance().removeMsgListener(msgHandlerListener);
            msgHandlerListener = new IMTool.MsgHandlerListener() {
                @Override
                public boolean handError(int errorCode, String errorMsg) {
                    return false;
                }

                MsgData oldMsgData;

                @Override
                public boolean handMsgRecive(MsgData msgData) {
                    boolean showMsg = true;
                    if (msgData.msgDetailData.isZhuangTaiMsg()) {//上边状态显示
                        if (msgData.msgDetailData.state == 0) {//开奖结果
                            initLiShiDialog();
                        } else if (msgData.msgDetailData.state == 1) {//可以开始投注了
                            setTextView(tv_touzhu_dangqian_qi, "" + msgData.msgDetailData.issue);
                            countDown = msgData.msgDetailData.countDown;
                            currIssue = msgData.msgDetailData.issue;

                            if (oldMsgData != null) {
                                if (oldMsgData.msgDetailData.state == msgData.msgDetailData.state
                                        && oldMsgData.msgDetailData.issue == oldMsgData.msgDetailData.issue
                                        ) {//同一期 发送了两次， 不显示ui 了
                                    showMsg = false;
                                }
                            }

                        } else if (msgData.msgDetailData.state == 3) {//封盘中
                            setTextView(tv_touzhu_dangqian_qi, "" + msgData.msgDetailData.issue);
                            countDown = -2;
                            setTextView(tv_touzhu_daojishi, "封盘中");
                        } else if (msgData.msgDetailData.state == 4) {//已经停售
                            setTextView(tv_touzhu_dangqian_qi, "" + msgData.msgDetailData.issue);
                            countDown = -3;
                            setTextView(tv_touzhu_daojishi, "已停售");
                        } else if (msgData.msgDetailData.state == 5) {//兑奖了， 刷新余额
                            refreshYuE();
                            showMsg = false;//不添加UI
                        }
                    }
                    oldMsgData = msgData;
                    if (showMsg) {
                        msgDataList.add(msgData);
                        initListView();
                    }

                    return false;
                }
            };
            IMTool.getIntance().addMsgListener(msgHandlerListener);
            BroadcastReceiverTool.bindAction(getActivity(), new BroadcastReceiverTool.BroadCastWork() {
                @Override
                public void run() {
                    IMTool.getIntance().sendMsg(MsgDataSend.getIntoRoomMsg(roomListBean.no),"0");
                }
            },IMTool.action_login_im_success);
            IMTool.getIntance().sendMsg(MsgDataSend.getIntoRoomMsg(roomListBean.no),"0");
        }
    }

    public void initListView() {
        recycleView.setDividerNormal(10);
        recycleView.setData(msgDataList, R.layout.im_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final MsgData msgData = msgDataList.get(position);

                TextView im_time_tv = itemView.findViewById(R.id.im_time_tv);
                UiTool.setTextView(im_time_tv, TimeTool.getLongTimeStr(msgData.msgDetailData.timestamp));


                View im_vg_left = itemView.findViewById(R.id.im_vg_left);
                View im_vg_right = itemView.findViewById(R.id.im_vg_right);
                View im_vg_center = itemView.findViewById(R.id.im_vg_center);

                im_time_tv.setVisibility(View.GONE);
                im_vg_left.setVisibility(View.GONE);
                im_vg_right.setVisibility(View.GONE);
                im_vg_center.setVisibility(View.GONE);


                if (msgData.msgDetailData.isZhuangTaiMsg() || msgData.msgDetailData.isXinJinYongHu()) {
                    TextView im_vg_center_tv = itemView.findViewById(R.id.im_vg_center);
                    HtmlTool.setHtmlText(im_vg_center_tv, msgData.msgDetailData.getZhuangTaiMsg(roomsBean));
                    im_vg_center.setVisibility(View.VISIBLE);
                } else if (msgData.msgDetailData.isTouZhuMsg()) {
                    im_time_tv.setVisibility(View.VISIBLE);

                    int idTouXiang = 0;
                    int idName = 0;
                    int idContent = 0;
                    if (Data_login_validate.getData_login_validate().uuid.equals(msgData.msgDetailData.uuid)) {//是我自己投注的
                        im_vg_right.setVisibility(View.VISIBLE);
                        idTouXiang = R.id.im_right_touxiang;
                        idName = R.id.im_right_name_tv;
                        idContent = R.id.im_right_content_tv;
                    } else {//别人投注的
                        im_vg_left.setVisibility(View.VISIBLE);
                        idTouXiang = R.id.im_left_touxiang;
                        idName = R.id.im_left_name_tv;
                        idContent = R.id.im_left_content_tv;

                        im_vg_left.setOnClickListener(new WzViewOnclickListener() {//跟投
                            @Override
                            public void onClickWz(View v) {

                                if (!currIssue.equals(msgData.msgDetailData.issue)) {
                                    DialogTool.initNormalQueDingDialog("", "只能跟投当前期", "确定", null, "").show();
                                    return;
                                }
                                View view = LayoutInflaterTool.getInflater(5, R.layout.touzhu_gentou_dialog).inflate();
                                final Dialog dialog = DialogTool.initNormalDialog(view, 0);

                                view.setOnClickListener(new WzViewOnclickListener() {
                                    @Override
                                    public void onClickWz(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                view.findViewById(R.id.tv_gentou_queding).setOnClickListener(new WzViewOnclickListener() {
                                    @Override
                                    public void onClickWz(View v) {
                                        dialog.dismiss();
                                        new TouZhuDialog(TouZhuFragment.this).touzhu(null, msgData.msgDetailData.issue, msgData.msgDetailData.amount, msgData.msgDetailData.betting);
                                    }
                                });

                                setTextView(view, R.id.tv_gentou_name, msgData.msgDetailData.nickname);
                                setTextView(view, R.id.tv_gentou_qishu, "" + msgData.msgDetailData.issue);
                                setTextView(view, R.id.tv_gentou_type, "" + msgData.msgDetailData.betting);
                                setTextView(view, R.id.tv_gentou_num, "" + Common.getPriceYB(msgData.msgDetailData.amount));

                                dialog.show();
                            }
                        });

                    }
                    TextView nick = itemView.findViewById(idName);
                    UiTool.setCompoundDrawables(getActivity(), nick, 0, 0, Data_personinfo_query.getGradeResImg(msgData.msgDetailData.grade), 0);
                    setTextView(nick, msgData.msgDetailData.nickname);
                    loadImage(msgData.msgDetailData.headImage, itemView, idTouXiang);
                    TextView im_left_content_tv = itemView.findViewById(idContent);
                    HtmlTool.setHtmlText(im_left_content_tv, msgData.msgDetailData.getZhuangTaiMsg(roomsBean));
                }

            }
        });
        int lastIndex = recycleView.getAdapter().getItemCount() - 1;
        if (lastIndex > -1) recycleView.smoothScrollToPosition(lastIndex);
    }


    /**
     * 刷新余额
     */
    public void refreshYuE() {
        Data_wallet_remain.load(new HttpUiCallBack<Data_wallet_remain>() {
            @Override
            public void onSuccess(Data_wallet_remain data) {
                hideWaitingDialog();
                if (data.isDataOkAndToast()) {
                    setTextView(parent, R.id.tv_touzhu_yue, "" + Common.getPriceYB(data.remain));
                }
            }
        });
    }


    Dialog dialogLiShi;

    /****
     * 中间历史数据
     */
    public void initLiShiDialog() {

        Data_cqssc_top10.load(Data_room_queryGame.YouXiEnum.valueOf(roomsBean.game), new HttpUiCallBack<Data_cqssc_top10>() {
            @Override
            public void onSuccess(Data_cqssc_top10 data) {
                if (data.isDataOkAndToast()) {
                    Data_cqssc_top10.CQSSCBean top10Bean = data.top10.get(0);
                    HtmlTool.setHtmlText((TextView) parent.findViewById(R.id.tv_touzhu_lishi_item), top10Bean.getShowStr("" + top10Bean.lottery_issue, top10Bean.lottery_num));
                }
            }
        });


        btn_touzhu_lishi.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                if (dialogLiShi != null && dialogLiShi.isShowing()) {
                    dialogLiShi.dismiss();
                    return;
                }
                Data_cqssc_top10.load(Data_room_queryGame.YouXiEnum.valueOf(roomsBean.game), new HttpUiCallBack<Data_cqssc_top10>() {
                    @Override
                    public void onSuccess(final Data_cqssc_top10 data) {
                        if (data.isDataOkAndToast()) {

                            View view = LayoutInflaterTool.getInflater(3, R.layout.touzhu_dialog_lishi).inflate();
                            dialogLiShi = new AppCompatDialog(AppTool.currActivity, R.style.dialog);
                            dialogLiShi.setContentView(view);
                            DialogTool.showAsDropDown(dialogLiShi, btn_touzhu_lishi, 0, 15);
                            DialogTool.setDialogWH(dialogLiShi, CommonTool.getWindowSize().x, dialogLiShi.getWindow().getAttributes().height);//设置满屏宽

                            WzSimpleRecycleView recycleView_touzhu_lishi = view.findViewById(R.id.recycleView_touzhu_lishi);
                            recycleView_touzhu_lishi.setData(data.top10, R.layout.touzhu_dialog_lishi_item, new WzSimpleRecycleView.WzRecycleAdapter() {
                                @Override
                                public void initData(int position, int type, View itemView) {
                                    super.initData(position, type, itemView);
                                    Data_cqssc_top10.CQSSCBean top10Bean = data.top10.get(position);
                                    HtmlTool.setHtmlText((TextView) itemView.findViewById(R.id.tv_touzhu_lishi_item), top10Bean.getShowStr("" + top10Bean.lottery_issue, top10Bean.lottery_num));
                                }
                            });
                            view.setOnClickListener(new WzViewOnclickListener() {
                                @Override
                                public void onClickWz(View v) {
                                    dialogLiShi.dismiss();
                                }
                            });
                            recycleView_touzhu_lishi.setOnClickListener(new WzViewOnclickListener() {
                                @Override
                                public void onClickWz(View v) {
                                    dialogLiShi.dismiss();
                                }
                            });


                        }
                    }
                });


            }
        });


    }


    @Override
    public void initListener() {
        vg_touzhu_yue.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                showWaitingDialog("");
                refreshYuE();
            }
        });
    }

    public static TouZhuFragment byData(Data_room_queryGame.InfoBean.RoomLevelsBean roomsBean, Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean roomListBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("roomsBean", roomsBean);
        bundle.putSerializable("roomListBean", roomListBean);
        TouZhuFragment touZhuFragment = new TouZhuFragment();
        touZhuFragment.setArguments(bundle);
        return touZhuFragment;
    }

}
