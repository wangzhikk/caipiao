package utils.wzutils.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by WangZhi on 14-3-24.
 * <p>
 * final MyPopupDialog myPopupDialog=new MyPopupDialog(getContext());
 * myPopupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 * <p>
 * myPopupDialog.setContentView(canjiaView);
 * myPopupDialog.setCanceledOnTouchOutside(true);
 * myPopupDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
 * myPopupDialog.getWindow().setLayout(CommonTool.getWindowSize().x,ViewGroup.LayoutParams.WRAP_CONTENT);
 * myPopupDialog.show();
 */
public class MyPopupDialog extends Dialog {
    //            if (dialog == null) {
//                Activity activity=getActivity();
//                dialog = new MyPopupDialog(activity, R.style.dialog_transparent);
//                View view = activity.getLayoutInflater().inflate(R.layout.dialog_choose_house, null);
//                dialog.setCanceledOnTouchOutside(true);
//                dialog.setContentView(view);
//                ListView listview_choose_house = (ListView) view.findViewById(R.id.listview_choose_house);
//                AdaChooseHouse adaChooseHouse=new AdaChooseHouse(listview_choose_house);
//                dialog.getWindow().setLayout(CommonTool.dip2px(200), CommonTool.dip2px(200));
//            }
//            dialog.showAsDropDown(vg_choose_house,0,0, Gravity.TOP|Gravity.CENTER_HORIZONTAL,true);

    public MyPopupDialog(Context context, int theme) {
        super(context, theme);
    }


    public MyPopupDialog(Context context) {
        super(context);
    }

    protected MyPopupDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void showAsDropDown(View anchorView, int xOff, int yOff, int gravity, int w, int h) {
        if (anchorView != null) {
            Window win = getWindow();
            int[] location = new int[2];
            anchorView.getLocationInWindow(location);
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(gravity);
            lp.x = location[0] + xOff; // 新位置X坐标
            lp.y = 100;//location[1] -h- CommonTool.dip2px(25)-yOff; // 新位置Y坐标
            win.setAttributes(lp);
            win.setLayout(w, h);
        }
        show();
    }
}
