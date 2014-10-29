
package com.innovzen.callbacks;

public interface CallbackStorageHandler<T> {

    public abstract void onSuccess(T result);

    public void onError();

}
