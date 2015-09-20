package com.example.luismillan.pymbil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luismillan.pymbil.Items;
import com.example.luismillan.pymbil.R;


import java.io.InputStream;
import java.util.List;


/**
 * Created by luismillan on 7/9/15.
 */
public class CustomAdapter extends ArrayAdapter<Items> {

    Context context;

    public CustomAdapter(Context context, int resourceId,
                         List<Items> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView image;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Items rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();

            holder.txtDesc = (TextView) convertView.findViewById(R.id.name_item);

            holder.txtDesc.setText("Puma");

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        return convertView;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap photo = null;
            try {
                System.out.println("HUBO UN BITMAP CREADO");
                //InputStream in = new java.net.URL(urldisplay).openStream();
                // mIcon11 = BitmapFactory.decodeStream(in);
                byte[] photoprep = Base64.decode(urldisplay, Base64.DEFAULT);
                photo = BitmapFactory.decodeByteArray(photoprep, 0, photoprep.length);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return photo;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }





}
