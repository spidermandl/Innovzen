package adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.db.History;

public class HistoryListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<History> historyList;
	private Calendar calendar;
	private TimeZone tz;

	public HistoryListAdapter(Context context, List<History> historyList) {
		inflater = LayoutInflater.from(context);
		this.historyList = historyList;
		calendar = Calendar.getInstance();
		tz = TimeZone.getDefault();
	}

	@Override
	public int getCount() {
		return historyList.size();
	}

	@Override
	public History getItem(int position) {
		return historyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HistoryItemViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_history, null);
			holder = new HistoryItemViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.date_textview);
			holder.inhale = (TextView) convertView.findViewById(R.id.inhale_textview);
			holder.holdInhale = (TextView) convertView.findViewById(R.id.hold_inhale_textview);
			holder.exhale = (TextView) convertView.findViewById(R.id.exhale_textview);
			holder.holdExhale = (TextView) convertView.findViewById(R.id.hold_exhale_textview);
			holder.duration = (TextView) convertView.findViewById(R.id.duration_textview);
			convertView.setTag(holder);
		} else {
			holder = (HistoryItemViewHolder) convertView.getTag();
		}

		// alternate row colors if in landscape mode (TABLET)
		if (ActivityBase.IS_TABLET) {
			if (position % 2 == 1) {
				convertView.setBackgroundColor(Color.WHITE);
			} else {
				convertView.setBackgroundColor(parent.getResources().getColor(R.color.history_list_item_light_gray));
			}
		}

		History h = getItem(position);
		holder.date.setText(convertTimestampToDateString(h.getDate()));
		holder.inhale.setText("" + h.getInhale());
		holder.holdInhale.setText("" + h.getHoldInhale());
		holder.exhale.setText("" + h.getExhale());
		holder.holdExhale.setText("" + h.getHoldExhale());
		holder.duration.setText(convertSecondsToMinutes(h.getDuration()));

		return convertView;
	}

	private static class HistoryItemViewHolder {

		private TextView date;
		private TextView inhale;
		private TextView holdInhale;
		private TextView exhale;
		private TextView holdExhale;
		private TextView duration;

	}

	private String convertTimestampToDateString(long unixTimestamp) {
		calendar.setTimeInMillis(unixTimestamp);
		calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
		Date currentTimeZone = (Date) calendar.getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy");
		return dateFormatter.format(currentTimeZone);
	}

	private String convertSecondsToMinutes(long seconds) {
		int m = (int) seconds / 60;
		int s = (int) seconds % 60;
		return m + " min " + s + " sec";
	}
	
	public void clearData(){
	    
	    historyList = new ArrayList<History>();
	    
	    notifyDataSetChanged();
	    
	}
}
