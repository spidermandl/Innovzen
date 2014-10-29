package com.innovzen.async.tasks;

import android.content.Context;

import com.innovzen.async.ExecutorAsyncTask;
import com.innovzen.callbacks.DataLoadedListener;
import com.innovzen.db.History;
import com.innovzen.db.HistoryDao;
import com.innovzen.utils.LocalDbUtil;

public class SaveHistoryAsyncTask extends ExecutorAsyncTask<History, Void, Boolean> {

	private static final String TAG = SaveHistoryAsyncTask.class.getSimpleName();
	private HistoryDao dao;
	private DataLoadedListener dataLoadedListener;

	public SaveHistoryAsyncTask(Context context, DataLoadedListener dataLoadedListener) {
		dao = LocalDbUtil.getHistoryDao(context);
		this.dataLoadedListener = dataLoadedListener;
	}

	@Override
	protected Boolean doInBackground(History... params) {
		History history = params[0];
		long result = dao.insert(history);
		if (result != -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		dataLoadedListener.onDataLoaded(result);
	}
}
