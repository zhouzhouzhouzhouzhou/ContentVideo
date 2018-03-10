package com.example.content.Adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 佳南 on 2017/9/18.
 */
//Recyclerview 滑动监听
public abstract class OnRecyclerviewScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrollToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrollToBottom();
        } else if (dy < 0) {
            onScrollDown();
        }
        else if (dy > 0){
            onScrollUp();
        }
    }

    protected abstract void onScrollDown();

    protected abstract void onScrollUp();

    protected abstract void onScrollToBottom();

    protected abstract void onScrollToTop();
}
