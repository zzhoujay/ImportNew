package zhou.appinterface.data;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;
import zhou.appinterface.util.ErrorHandler;

/**
 * Created by zhou on 15-10-20.
 * 数据管理器
 */
public final class DataManager {

    private static DataManager dataManager;

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    private HashMap<String, DataProvider> providers;

    private DataManager() {
        providers = new HashMap<>();
    }

    /**
     * 添加数据提供器（不进行重复检查）
     *
     * @param provider 提供器
     */
    public void add(DataProvider provider) {
        if (provider != null)
            providers.put(provider.key(), provider);
    }

    /**
     * 添加数据提供器
     *
     * @param provider 数据提供器
     * @param check    是否进行重复检查
     */
    public void add(DataProvider provider, boolean check) {
        if (provider == null) {
            return;
        }
        if (check) {
            if (!providers.containsKey(provider.key())) {
                providers.put(provider.key(), provider);
            }
        } else {
            providers.put(provider.key(), provider);
        }
    }

    /**
     * 移除数据提供器
     *
     * @param key key
     */
    public void remove(String key) {
        providers.remove(key);
    }

    /**
     * 移出数据提供器
     *
     * @param provider 数据提供器
     */
    public void remove(DataProvider provider) {
        if (provider != null) {
            providers.remove(provider.key());
        }
    }

    /**
     * 获取数据 按照（内存->本地缓存->网络）的顺序获取
     *
     * @param key          key
     * @param loadCallback 回调
     */
    @SuppressWarnings("unchecked")
    public <T> void get(String key, Action1<T> loadCallback) {
        try {
            DataProvider<T> provider = (DataProvider<T>) providers.get(key);
            get(provider, loadCallback);
        } catch (Exception e) {
            Log.d("get", "DataManager", e);
            loadCallback.call(null);
        }
    }

    /**
     * 获取数据
     *
     * @param provider     数据提供器
     * @param loadCallback 回调
     */
    public static <T> void get(DataProvider<T> provider, Action1<T> loadCallback) {
        if (provider.hasLoad()) {
            loadCallback.call(provider.get());
        } else {
            provider.loadByCache(t -> {
                if (t != null) {
                    provider.set(t, false);
                    if (provider.needCache()) {
                        provider.persistence();
                    }
                    loadCallback.call(provider.get());
                } else {
                    provider.load(tn -> {
                        provider.set(tn, false);
                        if (provider.needCache()) {
                            provider.persistence();
                        }
                        loadCallback.call(provider.get());
                    }, false);
                }
            });
        }
    }

    /**
     * 加载数据
     *
     * @param key          key
     * @param loadCallback 回调
     */
    @SuppressWarnings("unchecked")
    public <T> void load(String key, Action1<T> loadCallback, boolean more) {
        try {
            DataProvider<T> provider = (DataProvider<T>) providers.get(key);
            load(provider, loadCallback, more);
        } catch (Exception e) {
            Log.d("load", "DataManager", e);
            loadCallback.call(null);
        }
    }

    /**
     * 加载数据
     *
     * @param provider     数据提供器
     * @param loadCallback 回调
     */
    public static <T> void load(DataProvider<T> provider, Action1<T> loadCallback, boolean more) {
        provider.load(t -> {
            provider.set(t, more);
            if (provider.needCache()) {
                provider.persistence();
            }
            loadCallback.call(provider.get());
        }, more);
    }

    public static <T> void update(DataProvider<T> provider, Action1<T> loadCallback) {
        load(provider, loadCallback, false);
    }

    public <T> void update(String key, Action1<T> loadCallback) {
        load(key, loadCallback, false);
    }

    public static <T> void more(DataProvider<T> provider, Action1<T> loadCallback) {
        load(provider, loadCallback, true);
    }

    public <T> void more(String key, Action1<T> loadCallback) {
        load(key, loadCallback, true);
    }

    /**
     * 持久化所有数据
     */
    void persistence() {
        for (Map.Entry<String, DataProvider> entry : providers.entrySet()) {
            DataProvider dataProvider = entry.getValue();
            if (dataProvider.needCache()) {
                dataProvider.persistence();
            }
        }
    }

    void clearAllCache() {
        for (Map.Entry<String, DataProvider> entry : providers.entrySet()) {
            DataProvider provider = entry.getValue();
            provider.clearCache();
        }
    }

    void clearCache(String key) {
        DataProvider provider = providers.get(key);
        if (provider != null) {
            provider.clearCache();
        }
    }

    boolean hasLoad(String key) {
        DataProvider provider = providers.get(key);
        return provider != null && provider.hasLoad();
    }

    boolean exist(String key) {
        return providers.containsKey(key);
    }

    public void reset() {
        providers.clear();
    }
}
