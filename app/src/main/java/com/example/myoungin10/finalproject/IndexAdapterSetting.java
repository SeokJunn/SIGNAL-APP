package com.example.myoungin10.finalproject;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myoungin10.R;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class IndexAdapterSetting extends ArrayAdapter<ProfileModel> {
    private LayoutInflater inflater;
    private SparseArray<WeakReference<View>> viewArray;

    public IndexAdapterSetting(Context ctx, int txtViewId, List<ProfileModel> models) {
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
            cView = inflater.inflate(R.layout.activity_tab5_item, pView, false);

            TextView chatView = (TextView) cView.findViewById(R.id.tab5_chat);
            final ProfileModel model = getItem(position);
            chatView.setText(model.chat);

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
