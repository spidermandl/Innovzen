
package com.innovzen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.AsyncTask;

import com.innovzen.callbacks.CallbackStorageHandler;

public class FileUtil {

    /**
     * Reads the data from a file in the assets folder
     * 
     * @param ctx
     * @param filename
     * @param result
     *            may return null in the onSuccess method
     * @author MAB
     */
    public static final void readFileFromAssets(final Context ctx, final String filename, final CallbackStorageHandler<String> result) {

        new AsyncTask<Void, Void, String>() {

            @Override
            public String doInBackground(Void... params) {

                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(filename), "UTF-8"));

                    // Do reading, usually loop until end of file reading
                    String mLine = reader.readLine();
                    while (mLine != null) {
                        // process line
                        sb.append(mLine);
                        mLine = reader.readLine();
                    }

                    reader.close();
                } catch (IOException e) {
                    // Oups, could not read from assets
                    return null;
                }

                return sb.toString();

            }

            @Override
            protected void onPostExecute(String str) {
                if (str == null) {
                    result.onError();
                } else {
                    result.onSuccess(str);
                }
            }
        }.execute();

    }

}
