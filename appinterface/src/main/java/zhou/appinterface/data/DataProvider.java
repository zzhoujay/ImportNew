package zhou.appinterface.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.functions.Action1;
import zhou.appinterface.util.ErrorHandler;

/**
 * Created by zhou on 15-10-20.
 */
public interface DataProvider<T> {

    /**
     * 数据持久化（应异步进行）
     */
    void persistence();

    /**
     * 获取数据
     *
     * @return 数据
     */
    @Nullable
    T get();

    /**
     * 设置数据
     *
     * @param t 数据
     */
    void set(@Nullable T t, boolean more);

    /**
     * 从缓存中加载数据（应异步实现）
     */
    void loadByCache(Action1<T> closure);

    /**
     * 加载数据（必须异步实现）
     */
    void load(Action1<T> closure, boolean more);

    /**
     * 是否已经加载
     *
     * @return boolean
     */
    boolean hasLoad();

    /**
     * 是否需要缓存
     *
     * @return boolean
     */
    boolean needCache();

    /**
     * 清空缓存
     */
    boolean clearCache();

    /**
     * 获取该加载器的唯一标识
     *
     * @return key
     */
    @NonNull
    String key();

    /**
     * 设置错误回调
     *
     * @param closure
     */
    void setErrorHandle(ErrorHandler closure);

    /**
     * 设置provide能否发出提醒
     *
     * @param noticeable
     */
    void setNoticeable(boolean noticeable);
}
