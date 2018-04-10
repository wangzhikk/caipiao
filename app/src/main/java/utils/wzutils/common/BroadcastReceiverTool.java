package utils.wzutils.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.parent.WzParentFragmentLife;

/**
 * Created by ishare on 2016/8/10.
 */
public class BroadcastReceiverTool {
    public static void sendAction(String action) {
        sendAction(action, null);
    }

    private static void sendAction(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) intent.putExtras(bundle);
        AppTool.getApplication().sendBroadcast(intent);
        LogTool.s("发送了广播" + action);
    }

    public static void sendAction(String action, Object obj) {
        Bundle bundle = new Bundle();
        bundle.putString("json", JsonTool.toJsonStr(obj));
        sendAction(action, bundle);
    }

    /***
     * 放置一个 action 和处理动作
     *
     * @param activity
     * @param runnable
     */
    public static void bindAction(FragmentActivity activity,  final BroadCastWork runnable,String... actions ) {
        LiftFragment.bindAction(activity,  runnable,actions);
    }

    public abstract static class BroadCastWork<T> implements Runnable {
        public Intent getIntent() {
            if(intent==null)intent=new Intent();
            return intent;
        }

        private Intent intent;
        public String getJsonStr() {
            return "" + getIntent().getStringExtra("json");
        }
    }

    public static class LiftFragment extends WzParentFragmentLife {
        ArrayList<BroadcastReceiver> broadcastReceivers = new ArrayList<>();

        public static void bindAction(FragmentActivity activity, final BroadCastWork runnable,String... actions ) {
            if (activity == null || actions == null || runnable == null||actions.length<1) return;
            String tag = activity.toString();
            Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment == null) {
                fragment = new LiftFragment();
                activity.getSupportFragmentManager().beginTransaction().add(fragment, tag).commitAllowingStateLoss();
            }


            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        runnable.intent = intent;
                        runnable.run();
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            };
            IntentFilter intentFilter=new IntentFilter();
            for(String actionItem:actions){
                intentFilter.addAction(actionItem);
            }
            activity.registerReceiver(broadcastReceiver,intentFilter);
            if (fragment instanceof LiftFragment) {
                ((LiftFragment) fragment).broadcastReceivers.add(broadcastReceiver);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            for (int i = 0; i < broadcastReceivers.size(); i++) {
                try {
                    getActivity().unregisterReceiver(broadcastReceivers.get(i));
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
            broadcastReceivers.clear();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return null;
        }
    }

}
