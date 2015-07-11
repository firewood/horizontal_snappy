package io.github.firewood.horizontalsnappy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends ActionBarActivity {

    final static Integer ResourceIds[] = { R.drawable.earth, R.drawable.lunar_module, R.drawable.columbia };
    final static int SNAP_THRESHOLD = 4;

    private HorizontalSnapLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutManager = new HorizontalSnapLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ImageAdapter(this, Arrays.asList(ResourceIds)));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mDirection;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mDirection = dx;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScrollStopped(mDirection);
                }
            }
        });
    }

    void onScrollStopped(int direction) {
        int latter_pos = mLayoutManager.findLastVisibleItemPosition();
        if (latter_pos == RecyclerView.NO_POSITION) {
            return;
        }

        if (mLayoutManager.isSnapping()) {
            // snapping is finished
            mLayoutManager.setSnapping(false);
            return;
        }

        View a = mLayoutManager.getChildAt(0);      // former
        View b = mLayoutManager.getChildAt(1);      // latter

        int diff_a = a != null ? Math.abs(mLayoutManager.getDecoratedLeft(a)) : 0;
        int diff_b = b != null ? Math.abs(mLayoutManager.getDecoratedLeft(b)) : 0;
        if (diff_a > 0 && diff_b > 0) {
            int snap_to;
            if (direction < 0) {
                // left
                snap_to = (latter_pos > 0 && diff_a < diff_b * SNAP_THRESHOLD) ? (latter_pos - 1) : latter_pos;
            } else {
                snap_to = (latter_pos > 0 && diff_a * SNAP_THRESHOLD < diff_b) ? (latter_pos - 1) : latter_pos;
            }

            mLayoutManager.setSnapping(true);
            mRecyclerView.smoothScrollToPosition(snap_to);
        }
    }
}
