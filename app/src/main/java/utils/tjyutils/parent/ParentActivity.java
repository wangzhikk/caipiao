package utils.tjyutils.parent;


import utils.wzutils.parent.WzParentActivity;

/**
 * Created by wz on 2017/5/23.
 */

public class ParentActivity extends WzParentActivity {
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String getPageName(){
        return ""+getClass();
    }
    @Override
    public void onBackPressed() {

    }

}
