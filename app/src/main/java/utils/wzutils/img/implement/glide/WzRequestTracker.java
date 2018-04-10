package utils.wzutils.img.implement.glide;

import android.support.annotation.Nullable;

import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.SingleRequest;
import com.bumptech.glide.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import utils.wzutils.common.SafeTool;

/**
 * Created by wz on 2017/12/22.
 */
@Deprecated
public class WzRequestTracker extends RequestTracker{
    private final Set<Request> requests =
            Collections.newSetFromMap(new WeakHashMap<Request, Boolean>());
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<Request> pendingRequests = new ArrayList<>();
    private boolean isPaused;

    /**
     * 滑动的时候依然可以加载内存里面已经有的图片
     */
    public void runRequest(Request request) {
        requests.add(request);
        if(isPaused){
            try {
                Object model= SafeTool.getPrivateField(request,SingleRequest.class,"model");
                if(GlideConfiguration.isCache(model.hashCode())){
                    request.begin();
                    return;
                }else {
                    pendingRequests.add(request);
                }
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }else {
            request.begin();
        }
    }
    public boolean clearRemoveAndRecycle(@Nullable Request request) {
        return clearRemoveAndMaybeRecycle(request, /*isSafeToRecycle=*/ true);
    }
    private boolean clearRemoveAndMaybeRecycle(@Nullable Request request, boolean isSafeToRecycle) {
        if (request == null) {
            // If the Request is null, the request is already cleared and we don't need to search further
            // for its owner.
            return true;
        }
        boolean isOwnedByUs = requests.remove(request);
        // Avoid short circuiting.
        isOwnedByUs = pendingRequests.remove(request) || isOwnedByUs;
        if (isOwnedByUs) {
            request.clear();
            if (isSafeToRecycle) {
                request.recycle();
            }
        }
        return isOwnedByUs;
    }
    public boolean isPaused() {
        return isPaused;
    }
    public void pauseRequests() {
        isPaused = true;
        for (Request request : Util.getSnapshot(requests)) {
            if (request.isRunning()) {
                request.pause();
                pendingRequests.add(request);
            }
        }
    }
    public void resumeRequests() {
        isPaused = false;
        for (Request request : Util.getSnapshot(requests)) {
            if (!request.isComplete() && !request.isCancelled() && !request.isRunning()) {
                request.begin();
            }
        }
        pendingRequests.clear();
    }

    public void clearRequests() {
        for (Request request : Util.getSnapshot(requests)) {
            // It's unsafe to recycle the Request here because we don't know who might else have a
            // reference to it.
            clearRemoveAndMaybeRecycle(request, /*isSafeToRecycle=*/ false);
        }
        pendingRequests.clear();
    }

    public void restartRequests() {
        for (Request request : Util.getSnapshot(requests)) {
            if (!request.isComplete() && !request.isCancelled()) {
                // Ensure the request will be restarted in onResume.
                request.pause();
                if (!isPaused) {
                    request.begin();
                } else {
                    pendingRequests.add(request);
                }
            }
        }
    }
    @Override
    public String toString() {
        return super.toString() + "{numRequests=" + requests.size() + ", isPaused=" + isPaused + "}";
    }
}
