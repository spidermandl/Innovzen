package com.innovzen.async.tasks;

import java.util.List;

import android.content.Context;

import com.innovzen.async.ExecutorAsyncTask;
import com.innovzen.callbacks.DataLoadedListener;
import com.innovzen.db.History;
import com.innovzen.db.HistoryDao;
import com.innovzen.utils.LocalDbUtil;

public class GetAllHistoryAsyncTask extends ExecutorAsyncTask<Void, Void, List<History>> {

	private HistoryDao dao;
	private DataLoadedListener dataLoadedListener;

	public GetAllHistoryAsyncTask(Context context, DataLoadedListener dataLoadedListener) {
		dao = LocalDbUtil.getHistoryDao(context);
		this.dataLoadedListener = dataLoadedListener;
	}

	@Override
	protected List<History> doInBackground(Void... params) {
		return dao.loadAll();
	}

	@Override
	protected void onPostExecute(List<History> result) {
		super.onPostExecute(result);
		dataLoadedListener.onDataLoaded(result);
	}
}
