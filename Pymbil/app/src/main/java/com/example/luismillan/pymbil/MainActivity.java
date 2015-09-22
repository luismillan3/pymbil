package com.example.luismillan.pymbil;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
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
    public static JSONObject fromScan;
    public static UserProfile myprofile;
    public static boolean makingReceipt=false;
    public static TextView transferId;
    public static TextView date;
    public static TextView amount;

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
        System.out.println("Result String: " + result);
        try{
            fromScan = new JSONObject(result);


            System.out.println("From Scan: " + fromScan);

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Transaction Alert");
            alertDialog.setMessage("Do you want to continue with this transaction?\n\nAmount: $" + fromScan.getString("amount") + ".00");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (fromScan != null) {
                                new Transfer().execute();
                                //try{
//                                Items receipt = new Items(fromScan.getString("date"));
//                                    receipt.setAmount(fromScan.getString("amount"));
//                                    receipt.setDate(fromScan.getString("date"));
//                                    receipt.setPhoneNumber(fromScan.getString("recipientPhoneNum"));
//                                    receipt.setId(fromScan.getString("is"));
//
//                                mainCodedList.add(receipt);}
//                                catch(JSONException e){
//
//                                }

                            }
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        catch(JSONException e){
            System.out.println("JSON ERROR"+e.toString());
        }










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
                resource = R.layout.receipt_view;
                break;
            case 3:
                resource = R.layout.profile_view;
                mTitle = getString(R.string.title_section3);
                break;

            case 4:
                resource = R.layout.request_qr;
                mTitle = getString(R.string.title_section4);
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
                amount=(TextView) rootView.findViewById(R.id.textView);

                payIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent qrDroid = new Intent("la.droid.qr.scan");
                        qrDroid.putExtra( "la.droid.qr.complete" , true);
                        startActivityForResult(qrDroid, 0);

                    }
                });
            }
            else if (resource == R.layout.request_qr) {

                ImageView qris = (ImageView) rootView.findViewById(R.id.imageView);
                EditText phone = (EditText) rootView.findViewById(R.id.editText);
                EditText Amount = (EditText) rootView.findViewById(R.id.editText2);
                Button doneButton = (Button) rootView.findViewById(R.id.button);

                doneButton.setOnClickListener(new View.OnClickListener() {


                    public void onClick(View v) {


                    }
                });




            }

            else if (resource == R.layout.receipt_view){

                if(makingReceipt){


                makingReceipt=false;
                System.out.println("entreeee");}

                else{
                    transferId=(TextView) rootView.findViewById(R.id.transaction_id_receipt);
                    date=(TextView) rootView.findViewById(R.id.date_receipt);
                    amount=(TextView) rootView.findViewById(R.id.amount_receipt);
                }

            }

            return rootView;
        }

public void makeReceipt(){

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



    private static class LogIn extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            try{
            JSONObject forUser = new JSONObject(result);
                myprofile = new UserProfile(forUser.getString("id"),forUser.getString("phoneNumber"),forUser.getString("name"),"100");
            }

            catch(JSONException e){

            }


            Toast mensaje = Toast.makeText(main.getApplicationContext(), result, Toast.LENGTH_LONG);
            mensaje.show();


        }

        protected String doInBackground(JSONObject... object) {
            JSONObject toSendJson = new JSONObject();
            List<NameValuePair> nameValuePairs;
            String  _response = "No lo cogio bruh";
            try{
                HttpPost post = new HttpPost("http://54.175.238.70:8080/api/login");
                HttpClient httpclient = new DefaultHttpClient();

                toSendJson.put("username","duude");
                toSendJson.put("password","duuude");

                StringEntity se = new StringEntity(fromScan.toString());
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



    private static class Register extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {

            System.out.println(result);

        }

        protected String doInBackground(String... urls) {
            JSONObject toSendJson = new JSONObject();
            List<NameValuePair> nameValuePairs;
            String  _response = "No lo cogio bruh";
            try{
                HttpPost post = new HttpPost("http://54.175.238.70:8080/api/registerCustomer");
                HttpClient httpclient = new DefaultHttpClient();

                toSendJson.put("username","Luis");
                toSendJson.put("password","hackpr");
                toSendJson.put("cardNumber","0911231");
                toSendJson.put("isPublic","true");
                toSendJson.put("name","TechCompany");
                toSendJson.put("phoneNumber","7874563232");

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



    private class Transfer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            Toast mensaje = Toast.makeText(main.getApplicationContext(), result, Toast.LENGTH_LONG);

            mensaje.show();
            try {

                JSONObject obj = new JSONObject(result);
                makingReceipt=true;
                amount.setVisibility(View.VISIBLE);
                amount.setText("    Receipt\n"+
                        " Transfer Id: " + obj.getJSONObject("transfer").getString("id") + "\n" +
                        " Date: " + obj.getJSONObject("transfer").getString("date") + "\n" +
                        "Amount: $"+ obj.getJSONObject("transfer").getString("amount") );
            } catch (JSONException e) {
                e.printStackTrace();
            }





        }

        protected String doInBackground(String... urls) {
            JSONObject toSendJson = new JSONObject();
            List<NameValuePair> nameValuePairs;
            String  _response = "No lo cogio bruh";
            try{
                HttpPost post = new HttpPost("http://54.175.238.70:8080/api/makeTransfer");
                HttpClient httpclient = new DefaultHttpClient();

                toSendJson.put("recipientPhoneNum","5673634");
                toSendJson.put("amount","4.00");


                StringEntity se = new StringEntity(fromScan.toString());
                se.setContentType("application/json");
                post.setEntity(se);
                post.setHeader("Content-type", "application/json");

                HttpResponse response = httpclient.execute(post);
                _response= EntityUtils.toString(response.getEntity());
                System.out.println(_response);
            }
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
