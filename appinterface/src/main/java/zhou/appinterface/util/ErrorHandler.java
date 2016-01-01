package zhou.appinterface.util;

/**
 * Created by zhou on 15-10-20.
 */
public interface ErrorHandler {

    ErrorHandler handleError(Throwable throwable);

    boolean isSuccess();
}
