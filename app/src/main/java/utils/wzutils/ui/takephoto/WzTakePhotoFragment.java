package utils.wzutils.ui.takephoto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UpLoadFilesTool;
import utils.wzutils.parent.WzParentFragmentLife;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by ishare on 2016/6/14.
 * <p>
 * 2.0 用于拍照用的
 *
     new TakePhotoSimpleFragment().addToParent(getChildFragmentManager(), R.id.vg_dongtai_fabu_tupian, R.layout.dongtai_fabu_tupian_item, 9,
 new WzTakePhotoFragment.OnAddPhotoInitDataListenerImpDefault(R.id.imgv_add_photo, R.id.imgv_delete_photo, R.drawable.kk_send_picture_add, R.drawable.kk_send_picture_add));

 */
public class WzTakePhotoFragment extends WzParentFragmentLife implements Serializable {

    public static final String tag = "TakePhotoFragmentTag";
    public WzSimpleRecycleView recycleView;
    public int add_photo_id;
    public int itemId;
    public int itemImageViewId;
    public View parent;
    public int maxSize;
    public ArrayList<String> datas = new ArrayList<>();
    public String addPhoto = "addphoto";
    protected WzTakePhotoTool takePhotoTool;
    OnAddPhotoInitDataListener onAddPhotoInitDataListener;

    public static WzTakePhotoFragment getCurrTakePhotoFragment(FragmentManager fragmentManager){
        try {
            return ((WzTakePhotoFragment) fragmentManager.findFragmentByTag(tag));
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return new WzTakePhotoFragment();
    }
    public static ArrayList<String> getSelectPhotos(FragmentManager fragmentManager) {
        try {
            return getCurrTakePhotoFragment(fragmentManager).getSelectPhoto();
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return new ArrayList<>();
    }

    /****
     * @param fragmentManager
     * @param containerId     父容器 用于装这个控件的 id
     * @param add_photo_id    默认 加号图片
     * @param itemId          listview item布局的layoutid
     * @param itemImageViewId item 里面的图片id
     */
    public void addToParent(FragmentManager fragmentManager, int containerId, int add_photo_id, int itemId, int itemImageViewId, int maxSize) {
        Bundle bundle = new Bundle();
        bundle.putInt("add_photo_id", add_photo_id);
        bundle.putInt("itemId", itemId);
        bundle.putInt("itemImageViewId", itemImageViewId);
        bundle.putInt("maxSize", maxSize);
        addToParent(fragmentManager, containerId, bundle);
    }

    /***
     *
     * @param fragmentManager
     * @param containerId  父容器 用于装这个控件的 id
     * @param itemId  listview item布局的layoutid
     * @param maxSize 最大数量
     * @param onAddPhotoInitDataListener  recycle的适配器回调
     */
    public void addToParent(FragmentManager fragmentManager, int containerId, int itemId, int maxSize, OnAddPhotoInitDataListener onAddPhotoInitDataListener) {
        Bundle bundle = new Bundle();
        bundle.putInt("itemId", itemId);
        bundle.putInt("maxSize", maxSize);
        bundle.putSerializable("onAddPhotoInitDataListener", onAddPhotoInitDataListener);
        addToParent(fragmentManager, containerId, bundle);
    }

    private void addToParent(FragmentManager fragmentManager, int containerId, Bundle bundle) {
        setArguments(bundle);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, this, tag);
        ft.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = initContentView();
        initData();
        return parent;
    }

    public View initContentView() {
        recycleView = new WzSimpleRecycleView(getContext());
        recycleView.setBackgroundColor(Color.WHITE);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recycleView.setLayoutParams(lp);
        parent = recycleView;
        return parent;
    }

    public void initData() {
        try {
            if (getArguments() != null) {
                this.add_photo_id = getArguments().getInt("add_photo_id", add_photo_id);
                this.itemId = getArguments().getInt("itemId", itemId);
                this.itemImageViewId = getArguments().getInt("itemImageViewId", itemImageViewId);
                this.maxSize = getArguments().getInt("maxSize", 3);
                Object temListener = getArguments().getSerializable("onAddPhotoInitDataListener");
                if (temListener != null && temListener instanceof OnAddPhotoInitDataListener) {
                    this.onAddPhotoInitDataListener = (OnAddPhotoInitDataListener) temListener;
                }
            }
            if (add_photo_id > 0)
                addPhoto = "android.resource://" + AppTool.getApplication().getPackageName() + "/res/" + add_photo_id;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        initListView();
    }

    /***
     * 获取当前选中的 图片路径
     *
     * @return
     */
    protected ArrayList<String> getSelectPhoto() {
        ArrayList<String> paths = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            String path = datas.get(i);
            if (!addPhoto.equals(path)) {
                paths.add(path);
            }
        }
        return paths;
    }

    public void initListView() {
        if (itemId < 1) return;
        try {
            if (datas.size() == 0) {
                datas.add(addPhoto);
            } else if (!datas.get(datas.size() - 1).equals(addPhoto) && datas.size() < maxSize) {//最后一张 不是 添加的话就加上
                datas.add(addPhoto);
            }


            takePhotoTool = new WzTakePhotoTool(this);
            int col = 3;
            if (maxSize < col) {
                col = maxSize;
            }
            recycleView.setLayoutManager(new StaggeredGridLayoutManager(col, StaggeredGridLayoutManager.VERTICAL));
            recycleView.setDividerDp(10,10);
            recycleView.setData(datas, itemId, new WzSimpleRecycleView.WzRecycleAdapter() {
                @Override
                public void initData(final int positon, int type, final View itemView) {
                    if (onAddPhotoInitDataListener != null) {
                        onAddPhotoInitDataListener.onInitData(WzTakePhotoFragment.this, datas, positon, type, itemView);
                        return;
                    }
                    final String path = datas.get(positon);
                    if (addPhoto.equals(path)) {//点击了添加图片的按钮
                        itemView.setOnClickListener(new WzViewOnclickListener() {
                            @Override
                            public void onClickWz(View v) {
                                showChooseDialog();
                            }
                        });
                    } else {//点击了已经选择的图片
                        itemView.setOnClickListener(new WzViewOnclickListener() {
                            @Override
                            public void onClickWz(View v) {
                                try {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setMessage("是否删除这张图片？")
                                            .setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    datas.remove(path);
                                                    initListView();
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .create();
                                    alertDialog.show();
                                } catch (Exception e) {
                                    LogTool.ex(e);
                                }

                            }
                        });
                    }
                    ImgTool.loadImage(getActivity(), Uri.parse(path), (ImageView) itemView.findViewById(itemImageViewId));
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }


    }

    public void showChooseDialog() {
        if (takePhotoTool != null) {
            takePhotoTool.showDefaultChooseDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = takePhotoTool.readCurrChoosePhotoUri(data);
        try {
            File file = new File(path);
            long size = file.length();
            if (size > 0) {
                if (datas.get(datas.size() - 1).equals(addPhoto)) {
                    datas.remove(datas.size() - 1);
                }
                datas.add(file.getAbsolutePath());
                initListView();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    /***
     * 发送到服务端
     * @param upLoadImp
     * @param upLoadFilesListener
     *
     * 例子
     *
    public static void upLoadImg(FragmentManager fragmentManager,final CommitToServer commitToServer){
    ParentActivity.showWaitingDialogStac("");
    if(WzTakePhotoFragment.getSelectPhotos(fragmentManager).size()>0){//有文件需要上传
    WzTakePhotoFragment.getCurrTakePhotoFragment(fragmentManager).sendToServer(new UpLoadFilesTool.UpLoadImp() {
    @Override
    public void upLoadImpl(final UpLoadFilesTool.UpLoadData upLoadData) {
    Data_upload_file.load(upLoadData.file, Data_upload_file.UpLoadType.questions_images, new HttpUiCallBack<Data_upload_file>() {
    @Override
    public void onSuccess(Data_upload_file data) {
    upLoadData.notifyUploadEnd(data.isDataOk(),data.fileName);
    }
    });
    }
    }, new UpLoadFilesTool.UpLoadFilesListener() {
    @Override
    public void onUpLoadFileEnd(String localPath, String serverPath) {

    }

    @Override
    public void onUpLoadFileEndAll(List<String> serverPaths) {
    StringTool.StringItems stringItems=new StringTool.StringItems(",");
    String serverPath=stringItems.addItems(serverPaths).toString();
    commitToServer.run(serverPath);
    }
    });
    }else {
    commitToServer.run("");
    }
    }
     *
     *
     *
     *
     */
    public void sendToServer(UpLoadFilesTool.UpLoadImp upLoadImp,UpLoadFilesTool.UpLoadFilesListener upLoadFilesListener){
        ArrayList<UpLoadFilesTool.UpLoadData> upLoadDataList=new ArrayList<>();
        ArrayList <String> photos=getSelectPhoto();
        for(String photo:photos){
            upLoadDataList.add(new UpLoadFilesTool.UpLoadData(new File(photo)));
        }
        UpLoadFilesTool.upLoadImages(upLoadDataList, upLoadImp, upLoadFilesListener);
    }

    public static interface OnAddPhotoInitDataListener extends Serializable {
        void onInitData(WzTakePhotoFragment currFragment, List<String> datas, final int positon, int type, final View itemView);
    }

    /**
     * 用于默认的配置方式
     */
    public static class OnAddPhotoInitDataListenerImpDefault implements OnAddPhotoInitDataListener {
        /***
         * <?xml version="1.0" encoding="utf-8"?>
         <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
         xmlns:app="http://schemas.android.com/apk/res-auto"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         xmlns:tools="http://schemas.android.com/tools"
         >
         <RelativeLayout
         android:layout_width="wrap_content"
         android:layout_height="wrap_content">
         <RelativeLayout
         android:layout_marginRight="15dp"
         android:layout_width="78dp"
         android:layout_height="90dp">
         <ImageView
         android:scaleType="centerCrop"
         android:id="@+id/imgv_add_photo"
         android:layout_centerVertical="true"
         android:layout_width="70dp"
         android:layout_height="70dp"
         />
         <ImageView
         android:id="@+id/imgv_delete_photo"
         android:layout_alignParentRight="true"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:src="@drawable/icon_delete"
         />
         </RelativeLayout>
         </RelativeLayout>

         </RelativeLayout>
         */


        public int res_imgv_add_photo = 0;//用于显示图片的  控件id
        public int res_imgv_delete_photo = 0;//用于显示 删除按钮的控件id
        public int drawable_btn_camera;//没选过的时候列表为空， 这时候 显示的图片
        public int drawable_btn_add;//选过后 但是没选择满 这时候显示的图片

        public OnAddPhotoInitDataListenerImpDefault(int res_imgv_add_photo, int res_imgv_delete_photo, int drawable_btn_camera, int drawable_btn_add) {
            this.res_imgv_add_photo = res_imgv_add_photo;
            this.res_imgv_delete_photo = res_imgv_delete_photo;
            this.drawable_btn_camera = drawable_btn_camera;
            this.drawable_btn_add = drawable_btn_add;
        }

        @Override
        public void onInitData(final WzTakePhotoFragment currFragment, final List<String> datas, int positon, int type, View itemView) {
            final String path = datas.get(positon);
            ImageView imgv_add_photo = (ImageView) itemView.findViewById(res_imgv_add_photo);
            ImageView imgv_delete_photo = (ImageView) itemView.findViewById(res_imgv_delete_photo);
            if (currFragment.addPhoto.equals(path)) {
                if (positon == 0) {
                    imgv_add_photo.setImageResource(drawable_btn_camera);
                } else {
                    imgv_add_photo.setImageResource(drawable_btn_add);
                }
                imgv_delete_photo.setVisibility(View.GONE);

            } else {
                imgv_delete_photo.setVisibility(View.VISIBLE);
                ImgTool.loadImage(Uri.parse(path), (ImageView) itemView.findViewById(res_imgv_add_photo));
            }
            imgv_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currFragment.showChooseDialog();
                }
            });
            imgv_delete_photo.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(AppTool.currActivity)
                                .setMessage("是否删除这张图片？")
                                .setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        datas.remove(path);
                                        currFragment.initListView();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create();
                        alertDialog.show();
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            });
        }
    }

}
