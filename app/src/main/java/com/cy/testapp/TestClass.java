package com.cy.testapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Eddie on 2020/3/16 0016.
 */
public class TestClass {
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_0 = itemView.findViewById(R.id.tv_title);
        }
    }
}
