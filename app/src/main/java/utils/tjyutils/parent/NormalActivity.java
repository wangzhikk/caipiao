package utils.tjyutils.parent;


import com.cp.R;

import utils.wzutils.parent.WzNormalFragmentActivity;

public class NormalActivity extends WzNormalFragmentActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_normal;
    }

    @Override
    public int getContentFragmentId() {
        return R.id.fragment_container;
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
