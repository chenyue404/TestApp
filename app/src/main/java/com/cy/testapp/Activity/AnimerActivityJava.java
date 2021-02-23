package com.cy.testapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cy.testapp.Animer.Animer;
import com.cy.testapp.R;

public class AnimerActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animer);

        View vGreen = findViewById(R.id.vGreen);

        // 创建一个 Animer 解算器对象，采用了原生的 DynamicAnimation 动画器
        Animer.AnimerSolver solver = Animer.springiOSUIView(0.5f, 0.5f);
        Animer animer = new Animer(vGreen, solver, Animer.TRANSLATION_Y);
        animer.setUpdateListener(new Animer.UpdateListener() {
            @Override
            public void onUpdate(float value, float velocity, float progress) {
                vGreen.setTranslationY(value);
            }
        });
        animer.setCurrentValue(0f);
        vGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animer.setEndValue(1000f);
            }
        });
    }
}