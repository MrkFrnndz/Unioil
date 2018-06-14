package com.example.mark.unioil.submit;

/**
 * Created by PROGRAMMER2 on 6/14/2018.
 * ABZTRAK INC.
 */

class SubmitPresenter implements SubmitContract.SubmitPresenter {
    private SubmitContract.SubmitView submitView;

    SubmitPresenter(SubmitContract.SubmitView submitView) {
        this.submitView = submitView;
    }
}
