package utils.wzutils.img.implement.glide;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import utils.wzutils.common.LogTool;

/**
 * abc on 2017/4/19.
 */
@GlideModule
public class GlideConfiguration extends AppGlideModule {
    public static Set<Integer> currCacheUrlList = new HashSet<>();
    public static boolean isCache(Object model) {
        if (model == null) return false;
        return currCacheUrlList.contains(model);
    }

    Field fieldEngineKeyModel;


    public void applyOptions(Context context, GlideBuilder builder) {

        //initMemoryCache(builder);

      //  initExecutor(builder);

        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);//确实省了内存， 暂时没发现什么问题包括透明图片

    }

    public void registerComponents(Context context, Registry registry) {

    }

    public void registerComponents(Context context, Glide glide, Registry registry) {

    }


    public void initMemoryCache(GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 100; // 20mb
        try {
            if (fieldEngineKeyModel == null) {
                fieldEngineKeyModel = Class.forName("com.bumptech.glide.load.engine.EngineKey").getDeclaredField("model");
                fieldEngineKeyModel.setAccessible(true);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        LruResourceCache lruResourceCache = new LruResourceCache(memoryCacheSizeBytes) {
            @Override
            public synchronized Resource<?> put(Key key, @Nullable Resource<?> item) {
                try {
                    Object model = fieldEngineKeyModel.get(key);
                    if (model != null) {
                        currCacheUrlList.add(model.hashCode());
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
                return super.put(key, item);
            }
        };
        builder.setMemoryCache(lruResourceCache);
    }

    public void initExecutor(GlideBuilder builder) {
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor(8, "test", new GlideExecutor.UncaughtThrowableStrategy() {
            @Override
            public void handle(Throwable t) {
                LogTool.ex(t);
            }
        }));

        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(5, "test", new GlideExecutor.UncaughtThrowableStrategy() {
            @Override
            public void handle(Throwable t) {
                LogTool.ex(t);
            }
        }));
    }

}
