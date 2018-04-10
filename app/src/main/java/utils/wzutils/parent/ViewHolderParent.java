package utils.wzutils.parent;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;

import utils.wzutils.common.ViewTool;

/**
 * Created by coder on 15/12/30.
 */
public abstract class ViewHolderParent extends RecyclerView.ViewHolder implements Serializable {
    public ViewHolderParent(View itemView, View.OnClickListener onClickListener) {
        super(itemView);
        ViewTool.initViews(itemView, this, onClickListener);
    }

    public void initViews(View view) {

        ViewTool.initViews(view, this, null);
    }

    public void initViews(View view, View.OnClickListener onClickListener) {
        ViewTool.initViews(view, this, onClickListener);
    }

}
