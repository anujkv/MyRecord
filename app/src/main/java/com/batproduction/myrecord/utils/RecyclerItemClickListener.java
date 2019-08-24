package com.batproduction.myrecord.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
   OnItemClickListener mListener;
    private GestureDetector gestureDetector;
    Context context;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final OnItemClickListener mListener) {
        this.mListener = mListener;

        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //  return super.onSingleTapUp(e);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child =  recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child !=null &&mListener!=null){

                    mListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                }
//                     super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if (child!=null && mListener!=null &&gestureDetector.onTouchEvent(e)){
            mListener.onItemClick(child,rv.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public boolean onLongClick(View view, int position);
    }
}
