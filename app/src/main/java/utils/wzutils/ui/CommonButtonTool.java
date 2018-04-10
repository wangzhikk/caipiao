package utils.wzutils.ui;

import android.widget.CompoundButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LogTool;

/**
 * Created by ishare on 2016/7/15.
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 使用了这个后就不要 使用 radiobutton 的 setOnCheckedChangeListener 了， 用下面这个
 * <p>
 * commonButtonTool_jine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
 *
 * @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
 * <p>
 * }
 * });
 */
public class CommonButtonTool implements Serializable {
    public  CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    String defaultGroup = "group";
    List<CompoundButton> compoundButtonList = new ArrayList<>();
    CompoundButton checkedBtn;
    boolean autoChangeIsStart = false;

    public  void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void add(CompoundButton compoundButton) {
        if(!compoundButtonList.contains(compoundButton))compoundButtonList.add(compoundButton);
        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setChecked(buttonView);
                }
            }
        });
    }

    public void addCommonBtns(CompoundButton... compoundButton) {
        for (CompoundButton compoundButton1 : compoundButton) {
            add(compoundButton1);
        }
    }

    public CompoundButton getChecked() {
        return checkedBtn;
    }

    public void setChecked(CompoundButton compoundButton) {
        try {
            compoundButton.setChecked(true);
            checkedBtn = compoundButton;
            for (int i = 0; i < compoundButtonList.size(); i++) {
                CompoundButton compoundButton1 = compoundButtonList.get(i);
                if (!compoundButton1.equals(compoundButton)) {
                    compoundButton1.setChecked(false);
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(compoundButton, false);
                    }
                } else {
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(compoundButton, true);
                    }
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public List<CompoundButton> getAllButtons() {
        return compoundButtonList;
    }

    public void setAutoChange(final long time) {
        try {
            if (!autoChangeIsStart) {
                if (compoundButtonList != null && compoundButtonList.size() > 0) {
                    autoChangeIsStart = true;
                    compoundButtonList.get(0).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean setChecked = false;
                            boolean ok = false;
                            for (CompoundButton compoundButton : compoundButtonList) {
                                if (setChecked) {
                                    compoundButton.setChecked(true);
                                    ok = true;
                                    break;
                                }
                                if (compoundButton.isChecked()) {
                                    setChecked = true;
                                }
                            }
                            if (!ok) compoundButtonList.get(0).setChecked(true);
                            compoundButtonList.get(0).postDelayed(this, time);
                        }
                    }, time);
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void destory() {
        if (compoundButtonList != null) compoundButtonList.clear();
    }


}
