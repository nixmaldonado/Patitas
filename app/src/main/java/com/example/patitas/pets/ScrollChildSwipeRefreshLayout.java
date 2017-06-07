package com.example.patitas.pets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;


public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    private View scrollUpChild;

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollUpChild(View view) {
        this.scrollUpChild = view;
    }
}
