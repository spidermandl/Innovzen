
package adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.entities.SoundGroup;

public class AdapterSound extends BaseAdapter {

    // Hold the ID of the SILENCE sound. We use this when we add an "empty" sound in the sounds list
    public static final int SILENCE_ID = 615313351;

    // Hold layout inflater
    private Resources mRes;
    private LayoutInflater mInflater;
    public List<SoundGroup> mSounds = new ArrayList<SoundGroup>();

    // Hold the ID of the item currently selected
    private int mSelectedItemId = -1;

    // Holds the list item widget references
    public static class RowHolder {
        public TextView text;
    }

    public AdapterSound(Context ctx, List<SoundGroup> soundList, int selectedSoundId) {
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSounds = soundList;
        this.mRes = ctx.getResources();
        this.mSelectedItemId = selectedSoundId;
    }

    public AdapterSound(Context ctx, List<SoundGroup> soundList) {
        this(ctx, soundList, -1);
    }

    @Override
    public int getCount() {
        return mSounds.size() + 1; // +1 to account for the SILENCE item
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = initializeNewRow();
        } else {
            resetRow(convertView);
        }

        updateRowData(convertView, position);

        return convertView;
    }

    /**
     * Inflates a new EMPTY row
     * 
     * @param view
     * @author MAB
     */
    private View initializeNewRow() {

        // Instantiate a new row holder
        RowHolder holder = new RowHolder();

        // Inflate the new row
        View view = mInflater.inflate(R.layout.list_item_sound, null);

        // Place the views in the row holder
        holder.text = (TextView) view.findViewById(R.id.list_item_sound_text);

        // Attach the row holder to the content view's tag
        view.setTag(holder);

        return view;
    }

    /**
     * Resets (empties) the data of the list item <Br/>
     * Note: the content of the view will change
     * 
     * @param view
     * @author MAB
     */
    private void resetRow(View view) {

        // Get the row holder
        RowHolder holder = (RowHolder) view.getTag();

        // Reset all the data inside the row
        holder.text.setText("");
    }

    /**
     * Retrieves the data from the list of items (from the specified position) and populates the list item <br/>
     * Note: the content of the view will change
     * 
     * @param view
     * @param position
     * @author MAB
     */
    private void updateRowData(View view, int position) {

        // Get the row holder
        RowHolder holder = (RowHolder) view.getTag();

        // Get the sound item
        SoundGroup item;
        if (position == 0) {
            item = new SoundGroup(SILENCE_ID, mRes.getString(R.string.sound_no_sound), null);
        } else {
            item = mSounds.get(position - 1); // -1 because getCount returned a value +1 more than the actual size
        }

        /*
         * Populate the holder with values from the list item
         */
        // Set the text
        holder.text.setText(item.getName());

        // Mark the selected item
        if (mSelectedItemId == item.getId()) {
            holder.text.setBackgroundColor(mRes.getColor(R.color.list_item_sound_bckgnd_selected));
        } else {
            holder.text.setBackgroundColor(mRes.getColor(R.color.list_item_sound_bckgnd));
        }

    }

    /**
     * When the user taps on a sound item it marks it highlights it
     * 
     * @param position
     * @return the ID of the sound currently selected
     * @author MAB
     */
    public int markAsSelected(int position) {
        if (position == 0) {
            mSelectedItemId = SILENCE_ID;
        } else {
            mSelectedItemId = mSounds.get(position - 1).getId();// -1 because getCount returned a valute +1 more than the actual size
        }

        notifyDataSetChanged();

        return mSelectedItemId;
    }

    /**
     * Returns the ID of the sound currently selected
     * 
     * @return
     * @author MAB
     */
    public int getSelectedSoundId() {
        return mSelectedItemId;
    }

    /**
     * Returns the id of the first sound in the array.<br/>
     * May return {@link #SILENCE_ID} if the array is null or empty
     * 
     * @return
     * @author MAB
     */
    public int getFirstSoundId() {
        if (mSounds != null && mSounds.size() >= 1) {
            return mSounds.get(0).getId();
        }

        return AdapterSound.SILENCE_ID;
    }
    
    /**
     * Returns the id of the second sound in the array.<br/>
     * May return {@link #SILENCE_ID} if the array is null or empty
     * 
     * @return
     * @author MAB
     */
    public int getSecondSoundId() {
        if (mSounds != null && mSounds.size() >= 1) {
            return mSounds.get(1).getId();
        }
        
        return AdapterSound.SILENCE_ID;
    }

    public void setSelectedSoundID(int selectedSoundId) {
        mSelectedItemId = selectedSoundId;
    }
}
