package com.example.luismillan.pymbil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static MainActivity main;
    public static int resource;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    List<Items> mainCodedList;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main = this;
        resource = R.layout.fragment_main;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String result = data.getExtras().getString("la.droid.qr.result");

        Toast mensaje = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
        mensaje.show();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }




    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
               resource = R.layout.fragment_main;
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                resource = R.layout.success;
                Intent qrDroid = new Intent("la.droid.qr.scan");
                qrDroid.putExtra( "la.droid.qr.complete" , true);
                startActivityForResult(qrDroid, 0);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(resource, container, false);

if (resource == R.layout.fragment_main) {
    Button payIt = (Button) rootView.findViewById(R.id.pay_it_button);


    payIt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             new PutMethod().execute();
                refresh();
        }
    });
}

            return rootView;



        }


        public void refresh(){
            main.mainCodedList = new ArrayList<Items>();
            Items customer = new Items("PUMA");
            main.mainCodedList.add(customer);
            ListView mainList = (ListView) rootView.findViewById(R.id.list_item);
            CustomAdapter adapter = new CustomAdapter(main, android.R.layout.simple_list_item_1, main.mainCodedList);
            mainList.setAdapter(adapter);
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



    private static class PutMethod extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);

        }

        protected String doInBackground(String... urls) {
            JSONObject toSendJson = new JSONObject();
            List<NameValuePair> nameValuePairs;
            String  _response = "No lo cogio bruh";
            try{
                HttpPost post = new HttpPost("http://54.175.238.70:8080/api/login");
                HttpClient httpclient = new DefaultHttpClient();

                toSendJson.put("username","duude");
                toSendJson.put("password","duuude");

                StringEntity se = new StringEntity(toSendJson.toString());
                se.setContentType("application/json");
                post.setEntity(se);
                post.setHeader("Content-type", "application/json");

                HttpResponse response = httpclient.execute(post);
                _response= EntityUtils.toString(response.getEntity());}
            catch(IOException e){
                Log.v("IO error", e.getMessage() + "-" + _response);
            }
            catch (JSONException o){

                Log.v("IO error", o.getMessage());
            }



            return _response;
        }
    }
}
