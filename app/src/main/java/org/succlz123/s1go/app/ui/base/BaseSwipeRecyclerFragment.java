package org.succlz123.s1go.app.ui.base;

import org.succlz123.s1go.app.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by succlz123 on 16/4/29.
 */
public class BaseSwipeRecyclerFragment extends BaseSwipeRefreshFragment {
    protected RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, SwipeRefreshLayout layout, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recyclerview, layout, false);
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = f(view, R.id.recycler_view);
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView not found");
        }
        onViewCreated(recyclerView, savedInstanceState);
    }

    public void onViewCreated(RecyclerView recyclerView, @Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void lazyLoad() {

    }
}
