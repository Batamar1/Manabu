package me.manabu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.manabu.R;
import me.manabu.adapters.models.DecksDeckModel;
import me.manabu.adapters.models.LessonItemModel;

public class DecksDeckAdapter extends ArrayAdapter<DecksDeckModel> {

    private Context context;
    private ArrayList<DecksDeckModel> data;

    public DecksDeckAdapter(Context context, ArrayList<DecksDeckModel> data){
        super(context, R.layout.item_decks_deck, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ItemHolder holder = new ItemHolder();
        View infoItem = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        infoItem = inflater.inflate(R.layout.item_lesson_text, null);

        holder.title = (TextView) infoItem.findViewById(R.id.item_decks_deck_title);
        holder.desc = (TextView) infoItem.findViewById(R.id.item_decks_deck_description);
        holder.cards = (TextView) infoItem.findViewById(R.id.item_decks_deck_cards_count);

        holder.title.setText(data.get(position).getTitle());
        holder.desc.setText(data.get(position).getDescription());
        holder.cards.setText(data.get(position).getCards());

        return infoItem;
    }

    private class ItemHolder{
        TextView title;
        TextView desc;
        TextView cards;
    }
}