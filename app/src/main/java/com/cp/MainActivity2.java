//package com.cp;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.cp.cp.Data_cqssc_betting;
//import com.cp.cp.Data_login_validate;
//import com.cp.im.IMTool;
//import com.cp.im.MsgData;
//import com.cp.shouye.Data_room_queryGame;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import utils.wzutils.common.CommonTool;
//import utils.wzutils.common.TimeTool;
//import utils.wzutils.common.UiTool;
//import utils.wzutils.common.ViewTool;
//import utils.wzutils.http.HttpUiCallBack;
//import utils.wzutils.parent.WzParentActivity;
//import utils.wzutils.parent.WzViewOnclickListener;
//import utils.wzutils.ui.WzSimpleRecycleView;
//
//public class MainActivity2 extends WzParentActivity {
//    WzSimpleRecycleView recycleView;
//    View btn_login,btn_send,btn_touzhu;
//
//    TextView et_im_id,et_neirong,et_my_id;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ViewTool.initViewsByActivity(this);
//
//        init();
//
//    }
//    public void init(){
//        IMTool.getIntance().init(getApplication());
//
//
//
//        IMTool.getIntance().addMsgListener(new IMTool.MsgHandlerListener() {
//            @Override
//            public boolean handMsgRecive(MsgData msgData) {
//                msgDataList.add(msgData);
//                initListView();
//                return false;
//            }
//
//            @Override
//            public boolean handError(int errorCode, String errorMsg) {
//                return false;
//            }
//        });
//
//
//        btn_login.setOnClickListener(new WzViewOnclickListener() {
//            @Override
//            public void onClickWz(View v) {
//
//                showWaitingDialog("正在登录..");
//                final String name=et_my_id.getText().toString().trim();;
//                String pwd="111111";
//                if(IMTool.getIntance().myIp){//测试
//                    IMTool.getIntance().login(name,pwd,"CQSSC001");
//                }else {
//                    Data_login_validate.load(name, pwd, new HttpUiCallBack<Data_login_validate>() {
//                        @Override
//                        public void onSuccess(Data_login_validate data) {
//                            hideWaitingDialog();
//                            if(data.isDataOkAndToast()){
//                                IMTool.getIntance().login(data.uuid,data.token,"CQSSC001");
//                            }
//                        }
//                    });
//                }
//
//            }
//        });
//        btn_send.setOnClickListener(new WzViewOnclickListener() {
//            @Override
//            public void onClickWz(View v) {
//                String id=et_im_id.getText().toString().trim();
//                String content=et_neirong.getText().toString().trim();
//                IMTool.getIntance().sendMsg(content,id);
//            }
//        });
//        btn_touzhu.setOnClickListener(new WzViewOnclickListener() {
//            @Override
//            public void onClickWz(View v) {
//
//
//
//                showWaitingDialog("");
//
//                String issue="180224076";
//                String roomLevel="1";
//                String roomNo="CQSSC001";
//                String type="大";
//                String amount="1000";
//                showWaitingDialog("");
//                Data_cqssc_betting.load(Data_room_queryGame.YouXiEnum.valueOf(roomsBean.game), issue, roomNo, type, amount, new HttpUiCallBack<Data_cqssc_betting>() {
//                    @Override
//                    public void onSuccess(Data_cqssc_betting data) {
//                        hideWaitingDialog();
//                        CommonTool.showToast(data.getMsg());
//                        if(data.isDataOk()){
//
//                        }
//
//                    }
//                });
//            }
//        });
//        initListView();
//    }
//
//
//    List<MsgData> msgDataList=new ArrayList<>();
//    public void initListView(){
//            recycleView.setData(msgDataList, R.layout.im_msg_item, new WzSimpleRecycleView.WzRecycleAdapter() {
//                @Override
//                public void initData(int position, int type, View itemView) {
//                    super.initData(position, type, itemView);
//                    MsgData msgData=msgDataList .get(position);
//                    UiTool.setTextView(itemView,R.id.tv_im_name,""+msgData.userid);
//                    UiTool.setTextView(itemView,R.id.tv_im_content,""+msgData.dataContent);
//                    UiTool.setTextView(itemView,R.id.tv_im_time,""+ TimeTool.getLongTimeStr(""+msgData.msgDetailData.timestamp));
//                }
//            });
//
//        int lastIndex=recycleView.getAdapter().getItemCount()-1;
//        if(lastIndex>-1)recycleView.smoothScrollToPosition(lastIndex);
//    }
//
//}
