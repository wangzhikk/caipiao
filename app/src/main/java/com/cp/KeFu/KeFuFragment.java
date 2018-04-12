package com.cp.KeFu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.cp.im.KeFuMsgTool;
import com.cp.im.MsgData;
import com.cp.im.MsgDataSend;
import com.cp.shouye.Data_room_queryGame;
import com.cp.touzhu.Data_cqssc_top10;
import com.cp.touzhu.TouZhuDialog;
import com.cp.touzhu.TouZhuJiLuFragment;
import com.cp.touzhu.zoushitu.ZouShiTu_bj28_Fragment;
import com.cp.touzhu.zoushitu.ZouShiTu_cqssc_Fragment;
import com.cp.wode.Data_personinfo_query;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.WebFragment;
import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
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

public class KeFuFragment extends ParentFragment {

    WzSimpleRecycleView recycleView;

    View btn_send_msg;
    EditText et_send_msg;
    public  String toId="service";
    @Override
    public int initContentViewId() {
        return R.layout.liaotian;
    }

    @Override
    public void initData() {
        titleTool.setTitle("与客服聊天中");
        //UiTool.setSoftInputModeSpan(getActivity());
        getActivityWz().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        msgHandlerListener=new IMTool.MsgHandlerListener() {
            @Override
            public boolean handError(int errorCode, String errorMsg) {
                return false;
            }

            @Override
            public boolean handMsgRecive(MsgData msgData) {
                final List<MsgData> msgDataList=KeFuMsgTool.getMsgDataList();
                initListView(msgDataList);
                return false;
            }

            @Override
            public boolean messagesBeReceived(String theFingerPrint) {
                return super.messagesBeReceived(theFingerPrint);
            }
        };
        IMTool.getIntance().addMsgListener(msgHandlerListener);
        MsgDataSend.sendInToService();
        loadMsgDataList();

    }

    IMTool.MsgHandlerListener msgHandlerListener;

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMTool.getIntance().removeMsgListener(msgHandlerListener);
        MsgDataSend.sendExitService();
    }

    public void loadMsgDataList(){
        final List<MsgData> msgDataList=KeFuMsgTool.getMsgDataList();
        if(msgDataList!=null&&msgDataList.size()>0){
            initListView(msgDataList);
            recycleView.scrollToPosition(msgDataList.size()-1);
        }
    }



    public void initListView(final List<MsgData> msgDataList) {
        recycleView.setDividerNormal(10);
        MsgData.initShowTimeStamp(msgDataList);
        recycleView.setData(msgDataList, R.layout.im_item_kefu, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final MsgData msgData = msgDataList.get(position);

                    TextView im_time_tv = itemView.findViewById(R.id.im_time_tv);


                    View im_vg_left = itemView.findViewById(R.id.im_vg_left);
                    View im_vg_right = itemView.findViewById(R.id.im_vg_right);
                    View im_vg_center = itemView.findViewById(R.id.im_vg_center);

                    im_time_tv.setVisibility(View.GONE);
                    im_vg_left.setVisibility(View.GONE);
                    im_vg_right.setVisibility(View.GONE);
                    im_vg_center.setVisibility(View.GONE);

                if(msgData.msgDetailData.isChatMsg()){
                    if(StringTool.notEmpty(msgData.msgDetailData.showTimeStamp)){
                        im_time_tv.setVisibility(View.VISIBLE);
                        UiTool.setTextView(im_time_tv, msgData.msgDetailData.showTimeStamp);
                    }else {
                        im_time_tv.setVisibility(View.GONE);
                    }


                    int idTouXiang = 0;
                    int idContent = 0;
                    if (Data_login_validate.getData_login_validate().uuid.equals(msgData.msgDetailData.uuid)) {//是我自己投注的
                        im_vg_right.setVisibility(View.VISIBLE);
                        idTouXiang = R.id.im_right_touxiang;
                        idContent = R.id.im_right_content_tv;
                    } else {//别人投注的
                        im_vg_left.setVisibility(View.VISIBLE);
                        idTouXiang = R.id.im_left_touxiang;
                        idContent = R.id.im_left_content_tv;

                    }

                    if("service".equals(msgData.msgDetailData.uuid)){
                        msgData.msgDetailData.headImage=R.drawable.img_servicer;
                    }
                    loadImage(msgData.msgDetailData.headImage,itemView,idTouXiang);
                    TextView im_left_content_tv = itemView.findViewById(idContent);
                    if(msgData.msgDetailData.isChatMsg()&&msgData.msgDetailData.state==10){
                        HtmlTool.setHtmlText(im_left_content_tv, msgData.msgDetailData.msgContent);
                    }
                }else if(msgData.msgDetailData.isServiceError()){
                        TextView im_vg_center_tv = itemView.findViewById(R.id.im_vg_center);
                        HtmlTool.setHtmlText(im_vg_center_tv, msgData.msgDetailData.msg);
                        im_vg_center.setVisibility(View.VISIBLE);
                }



            }
        });
        int lastIndex = recycleView.getAdapter().getItemCount() - 1;
        if (lastIndex > -1) recycleView.smoothScrollToPosition(lastIndex);
    }


    @Override
    public void initListener() {
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=et_send_msg.getText().toString();
                if(StringTool.isEmpty(text)){
                    CommonTool.showToast("消息内容不能为空!");
                    return;
                }
                showWaitingDialog("");
                MsgDataSend.sendChatMsg(text, toId, new IMTool.SendMsgListener() {
                    @Override
                    public void sendMsgResult(String content, String friendId, int code) {
                        hideWaitingDialog();
                        if(code==0){
                            MsgData msgData=new MsgData("",friendId,content,-1);
                            msgData.msgDetailData= JsonTool.toJava(content, MsgData.MsgDetailData.class);
                            KeFuMsgTool.addMsgData(msgData);
                            initListView(KeFuMsgTool.getMsgDataList());
                            et_send_msg.setText("");
                        }else {
                            CommonTool.showToast("发送消息失败");
                        }
                    }
                });
            }
        });

    }

}
