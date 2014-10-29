package com.innovzen.async.tasks;

import android.content.Context;

import com.innovzen.async.ExecutorAsyncTask;
import com.innovzen.callbacks.DataErasedListener;
import com.innovzen.utils.LocalDbUtil;

public class EraseAllHistoryAsyncTask extends ExecutorAsyncTask<Void, Void, Boolean> {

    private Context mCtx;
    private DataErasedListener mDataErasedListener;

    public EraseAllHistoryAsyncTask(Context context, DataErasedListener dataErasedListener) {
        this.mCtx = context;
        this.mDataErasedListener = dataErasedListener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        LocalDbUtil.eraseAllData(mCtx);

        return true;
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);

        mDataErasedListener.onDataErased(bool);
    }

}
