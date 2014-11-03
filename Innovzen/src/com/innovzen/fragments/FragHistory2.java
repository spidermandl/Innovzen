package com.innovzen.fragments;

import java.util.ArrayList;
import java.util.List;

import adapters.HistoryListAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.async.tasks.EraseAllHistoryAsyncTask;
import com.innovzen.async.tasks.GetAllHistoryAsyncTask;
import com.innovzen.callbacks.DataErasedListener;
import com.innovzen.callbacks.DataLoadedListener;
import com.innovzen.db.History;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.ui.HorizontalListView;

public class FragHistory2 extends FragBase implements OnClickListener {

    // Hold the footer handler
  

    // Hold the header handler
  

    private HorizontalListView historyListViewPortrait;
    private ListView historyListViewLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history2, container, false);

        init(view);

        return view;
    }
  


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.history_clear) {
            clearHistory();
        }
    }

    @Override
    public void init(View view) {
        Resources res = getResources();

        // Get references
        RelativeLayout footer = (RelativeLayout) view.findViewById(R.id.history_footer);

        // Set listeners
      

        // Init the footer
     

        // Init the header

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ActivityBase.IS_TABLET) {
            historyListViewLandscape = (ListView) view.findViewById(R.id.history_list_view2);
        } else {
            historyListViewPortrait = (HorizontalListView) view.findViewById(R.id.history_list_view2);
        }

        new GetAllHistoryAsyncTask(getActivity(), new DataLoadedListener() {

            @Override
            public <T> void onDataLoaded(T t) {
                populateHistoryList((List<History>) t);
            }
        }).execute();
    }

    private void populateHistoryList(List<History> historyList) {
        if (ActivityBase.IS_TABLET) {
            historyListViewLandscape.setAdapter(new HistoryListAdapter(getActivity(), historyList));
        } else {
            historyListViewPortrait.setAdapter(new HistoryListAdapter(getActivity(), historyList));
        }
    }

    private void clearHistory() {
        new EraseAllHistoryAsyncTask(getActivity(), new DataErasedListener() {

            @Override
            public <T> void onDataErased(boolean success) {
                if (success) {
                    // Update the adapter now
                    populateHistoryList(new ArrayList<History>());

                    if (ActivityBase.IS_TABLET) {
                        ((HistoryListAdapter) historyListViewLandscape.getAdapter()).clearData();
                    } else {
                        ((HistoryListAdapter) historyListViewPortrait.getAdapter()).clearData();
                    }

                } else {
                    // TODO: ??
                }

            }

        }).execute();
    }

}
