package com.example.user.elevate.adapter;

/**
 * Created by User on 12/02/2018.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.elevate.R;
import com.example.user.elevate.model.MyDataModel;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<MyDataModel> {

    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.textViewName.setText(item.getnama());
        vh.textViewName.setTypeface(Typeface.createFromAsset(parent.getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        vh.textViewCountry.setText(item.getabsen1());
        vh.textViewCountry.setTypeface(Typeface.createFromAsset(parent.getContext().getAssets(),"American_Typewriter_Regular.ttf"));

        vh.textViewID.setText(item.getId());
        vh.textViewID.setTypeface(Typeface.createFromAsset(parent.getContext().getAssets(),"American_Typewriter_Regular.ttf"));

        vh.textViewtgl.setText(item.gettgl1());
        vh.textViewtgl.setTypeface(Typeface.createFromAsset(parent.getContext().getAssets(),"American_Typewriter_Regular.ttf"));
        return vh.rootView;
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2016-01-05 00:50:26 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewtgl;
        public final TextView textViewName;
        public final TextView textViewCountry;
        public final TextView textViewID;
        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewCountry, TextView textViewID,TextView textViewtgl) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewCountry = textViewCountry;
            this.textViewID = textViewID;
            this.textViewtgl = textViewtgl;
        }

        public static ViewHolder create(RelativeLayout rootView) {
           TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);

            TextView textViewCountry = (TextView) rootView.findViewById(R.id.textViewCountry);
            TextView textViewtgl = (TextView) rootView.findViewById(R.id.textViewtgl);
            TextView textViewID = (TextView) rootView.findViewById(R.id.textViewID);
            return new ViewHolder(rootView, textViewName, textViewCountry,textViewID,textViewtgl);
        }
    }
}
