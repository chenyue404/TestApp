package com.cy.testapp.animer.core.solver;


public class AnSolver extends Object {

    // ############################################
    // Construct
    // ############################################

    private Object arg1, arg2;
    private SolverListener mListener = null;
    private int SOLVER_MODE = -1;

    public AnSolver(Object val1, Object val2, int mode) {
        unBindSolverListener();
        setSolverMode(mode);
        setArg1(val1);
        setArg2(val2);
    }

    public interface SolverListener {
        void onSolverUpdate(Object arg1, Object arg2);
    }

    public void bindSolverListener(SolverListener listener) {
        mListener = listener;
    }

    public void unBindSolverListener() {
        if (mListener != null) {
            mListener = null;
        }
    }

    public void setArg1(Object val) {
        arg1 = val;
        if (mListener != null) {
            mListener.onSolverUpdate(arg1, arg2);
        }
    }

    public Object getArg1() {
        return arg1;
    }

    public void setArg2(Object val) {
        arg2 = val;
        if (mListener != null) {
            mListener.onSolverUpdate(arg1, arg2);
        }
    }

    public Object getArg2() {
        return arg2;
    }

    public int getSolverMode() {
        return SOLVER_MODE;
    }

    public void setSolverMode(int solverMode) {
        if (getSolverMode() != solverMode) {
            unBindSolverListener();
            SOLVER_MODE = solverMode;
        }
    }
}




