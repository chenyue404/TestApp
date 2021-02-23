package com.cy.testapp.Animer.core.state;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PhysicsState {
    private float value;
    private float velocity;
    Map<String, Float> kvMap = new HashMap<>();

    // ############################################
    // Constructor
    // ############################################
    public PhysicsState() {
        updatePhysics(0, 0);
        setStateValue("Start", 0);
        setStateValue("End", 1);
    }

    public PhysicsState(float start) {
        updatePhysics(start, 0);
        setStateValue("Start", start);
        setStateValue("End", 0);
    }

    public PhysicsState(float start, float end) {
        updatePhysics(start, 0);
        setStateValue("Start", start);
        setStateValue("End", end);
    }

    // ############################################
    // PhysicsState Value's Getter & Setter
    // ############################################

    public void updatePhysics(float val, float vel) {
        updatePhysicsValue(val);
        updatePhysicsVelocity(vel);
    }

    public void updatePhysicsVelocity(float vel) {
        velocity = vel;
    }

    public void updatePhysicsValue(float val) {
        value = val;
    }

    public float getPhysicsValue() {
        return value;
    }

    public float getPhysicsVelocity() {
        return velocity;
    }

    // ############################################
    // PhysicsState State's Getter & Setter
    // ############################################

    public void setStateValue(String key, float value) {
        kvMap.put(key, value);
    }

    public float getStateValue(String key) {

        try {
            return kvMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("setStateValue first", Log.getStackTraceString(e));
        }

        return -1;
    }

    //TODO: Prev State


}
