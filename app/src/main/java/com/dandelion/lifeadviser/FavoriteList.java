package com.dandelion.lifeadviser;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteList extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, MainActivity.database.getAll());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, new FavoriteAdvice()).commit();

        TextView textView = (TextView) view;
        MainActivity.database.setLine(textView.getText().toString());
    }
}
