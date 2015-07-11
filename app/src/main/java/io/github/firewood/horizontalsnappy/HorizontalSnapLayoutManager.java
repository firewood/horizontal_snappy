package io.github.firewood.horizontalsnappy;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HorizontalSnapLayoutManager extends LinearLayoutManager {

    final static int MAX_VELOCITY = 100;

    private boolean mSnapping;

    public boolean isSnapping() { return mSnapping; }

    public void setSnapping(boolean snapping) { mSnapping = snapping; }

    public HorizontalSnapLayoutManager(Context context) {
        super(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public int scrollHorizontallyBy(int dx, final RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = super.scrollHorizontallyBy(dx, recycler, state);
        if (!isSnapping()) {
           travel = Math.max(-MAX_VELOCITY, travel);
           travel = Math.min(MAX_VELOCITY, travel);
        }
        return travel;
    }
}
