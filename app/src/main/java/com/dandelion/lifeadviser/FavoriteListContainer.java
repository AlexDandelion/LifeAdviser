package com.dandelion.lifeadviser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoriteListContainer extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment listFragment = getFragmentManager().findFragmentById(R.id.listFragment);
        if (listFragment != null)
            getFragmentManager().beginTransaction().remove(listFragment).commit();
    }
}