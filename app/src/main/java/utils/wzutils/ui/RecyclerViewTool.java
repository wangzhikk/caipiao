package utils.wzutils.ui;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import utils.wzutils.common.LogTool;

/**
 * abc on 2017/3/24.
 */

public class RecyclerViewTool {
    /***
     * 必须用的 StaggeredGridLayoutManager  这个才能调用
     * @param itemView
     */
    public static void setFullSpan(View itemView) {
        try {
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    new StaggeredGridLayoutManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            itemView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
}
