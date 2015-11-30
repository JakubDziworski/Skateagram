package com.kuba.skateagramclient.view;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kuba.skateagramclient.R;

/**
 * Created by kuba on 29.11.2015.
 */
public class PostView extends LinearLayout {
    public PostView(Context context) {
        super(context);
    }

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static PostView inflate(ViewGroup parent) {
        PostView view = (PostView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return view;
    }


}
