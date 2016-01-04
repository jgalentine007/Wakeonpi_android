package jgalentine007.wakeonpi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private MyTwitter twit;
    private ArrayAdapter<String> lvComputersAdapter;
    private final Context actContext = this;

    // preferences and consts
    private static SharedPreferences prefs;
    public static final String PREF_consumerKey = "consumerKey";
    public static final String PREF_consumerSecret = "consumerSecret";
    public static final String PREF_token = "token";
    public static final String PREF_tokenSecret = "tokenSecret";
    public static final String PREF_computerList = "computerList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(actContext);

        // prepare computers ListView and refreshes data
        lvComputersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        final ListView lvComputers = (ListView) findViewById(R.id.lvComputers);
        lvComputers.setAdapter(lvComputersAdapter);
        Refresh();

        // long click event handler
        lvComputers.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String message = "WOL " + parent.getItemAtPosition(position);
                        freshTwitter();
                        twit.sendMessage(message);
                        return true;
                    }
                }
        );

        // shared preference event handler
        SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(PREF_computerList))
                    Refresh();
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(mListener);
    }

    // creates new twitter4j object for refreshing configuration values
    private void freshTwitter() {
        // prepare twitter
        twit = new MyTwitter(
                actContext,
                prefs.getString(PREF_consumerKey, ""),
                prefs.getString(PREF_consumerSecret, ""),
                prefs.getString(PREF_token, ""),
                prefs.getString(PREF_tokenSecret, "")
        );

        twit.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Refresh:
                Refresh();
                break;
            case R.id.Settings:
                Settings();
                break;
            case R.id.About:
                About();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Settings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void Refresh(){
        String data = prefs.getString(PREF_computerList, "");
        final List<String> items = Arrays.asList(data.split("\\s*,\\s*"));
        lvComputersAdapter.clear();
        lvComputersAdapter.addAll(items);
        lvComputersAdapter.notifyDataSetChanged();
    }

    public void About(){
        Toast.makeText(getApplicationContext(), "The secret to humor is surprise.", Toast.LENGTH_LONG).show();
    }
}
