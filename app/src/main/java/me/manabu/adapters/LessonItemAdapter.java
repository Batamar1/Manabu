package me.manabu.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.manabu.R;
import me.manabu.adapters.models.LessonItemModel;

public class LessonItemAdapter extends ArrayAdapter<LessonItemModel> {

    private Context context;
    private ArrayList<LessonItemModel> data;

    public LessonItemAdapter(Context context, ArrayList<LessonItemModel> data){
        super(context, R.layout.item_lesson_text, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ItemHolder holder = new ItemHolder();
        View infoItem = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        infoItem = inflater.inflate(R.layout.item_lesson_text, null);

        holder.header = (TextView) infoItem.findViewById(R.id.item_lesson_header);
        holder.text = (TextView) infoItem.findViewById(R.id.item_lesson_text);

        holder.header.setText(data.get(position).getHeader());
        holder.text.setText(data.get(position).getText());

        return infoItem;
    }

    private class ItemHolder{
        TextView header;
        TextView text;
    }
}
