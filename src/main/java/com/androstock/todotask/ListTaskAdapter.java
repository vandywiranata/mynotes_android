package com.androstock.todotask;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.ArrayList;
import java.util.HashMap;

public class ListTaskAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListTaskAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListTaskViewHolder holder = null;
        if (convertView == null) {
            holder = new ListTaskViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.task_list_row, parent, false);
            holder.task_image   = (TextView) convertView.findViewById(R.id.task_image);
            holder.task_notes   = (TextView) convertView.findViewById(R.id.task_notes);
            holder.task_date    = (TextView) convertView.findViewById(R.id.task_date);
            holder.btnDelete    = (ImageButton) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ListTaskViewHolder) convertView.getTag();
        }

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        holder.task_image.setId(position);
        holder.task_notes.setId(position);
        holder.task_date.setId(position);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                HashMap<String, String> jp = new HashMap<String, String>();
                jp = data.get(position);
                TaskDBHelper db = new TaskDBHelper(v.getContext());
                db.deleteData(Integer.parseInt(jp.get(TaskHome.KEY_ID)));
                ((TaskHome)v.getContext()).populateData();
            }
        });

        try{
            holder.task_notes.setText(song.get(TaskHome.KEY_NOTES));
            holder.task_date.setText(song.get(TaskHome.KEY_DATE));

            /* Image */
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(getItem(position));
            holder.task_image.setTextColor(color);
            holder.task_image.setText(Html.fromHtml("&#11044;"));
            /* Image */

            }catch(Exception e) {}
        return convertView;
    }
}

class ListTaskViewHolder {
    TextView task_image;
    TextView task_notes, task_date;
    ImageButton btnDelete;
}