依赖



    implementation 'com.android.support:support-v4:27.0.2'
    implementation 'com.android.support:appcompat-v7:27.0.2'
    implementation 'com.android.support:recyclerview-v7:27.0.2'

    implementation 'org.xutils:xutils:3.5.0'
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'me.iwf.photopicker:PhotoPicker:0.9.8@aar'
    implementation 'com.github.bumptech.glide:glide:4.4.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.4.0'

    implementation 'com.yanzhenjie:album:1.0.7'
    implementation 'com.yanzhenjie:durban:1.0.1'
    implementation 'com.yanzhenjie:sofia:1.0.4'


    compile 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
    compile 'com.scwang.smartrefresh:SmartRefreshHeader:1.0.5.1'//没有使用特殊Header，可以不加这行



 权限


        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="android.permission.GET_TASKS"  />