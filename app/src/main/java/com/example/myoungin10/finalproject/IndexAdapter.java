package com.example.myoungin10.finalproject;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myoungin10.R;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class IndexAdapter extends ArrayAdapter<ProfileModel> {
    private LayoutInflater inflater;
    private SparseArray<WeakReference<View>> viewArray;

    public IndexAdapter(Context ctx, int txtViewId, List<ProfileModel> models) {
        super(ctx, txtViewId, models);
        this.viewArray = new SparseArray<WeakReference<View>>(models.size());
        this.inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View cView, ViewGroup pView) {
        if(viewArray != null && viewArray.get(position) != null) {
            cView = viewArray.get(position).get();
            if(cView != null)
                return cView;
        }

        try {
            cView = inflater.inflate(R.layout.activity_tab2_item, pView, false);

            ImageView profileView = (ImageView) cView.findViewById(R.id.tab2_profile_image);
            TextView nameView = (TextView) cView.findViewById(R.id.tab2_name);
            TextView chatView = (TextView) cView.findViewById(R.id.tab2_chat);
            TextView timestampView = (TextView) cView.findViewById(R.id.tab2_timestamp);

            final ProfileModel model = getItem(position);
            profileView.setBackground(new ShapeDrawable(new OvalShape()));
            profileView.setClipToOutline(true);
            profileView.setImageResource(model.profile);
//            GradientDrawable drawble = (GradientDrawable)getContext().getDrawable(R.drawable.background_rounding);
//            profileView.setBackground(drawble);
//            profileView.setClipToOutline(true);
            nameView.setText(model.name);
            chatView.setText(model.chat);
            Date date = new Date(model.timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            timestampView.setText(sdf.format(date));
        } finally {
            viewArray.put(position, new WeakReference<View>(cView));
        }
        return cView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void update() {
        viewArray.clear();
        notifyDataSetChanged();
    }
}
