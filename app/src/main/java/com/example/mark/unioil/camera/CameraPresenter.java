package com.example.mark.unioil.camera;

/**
 * Responsible for handling actions from CameraView {@link CameraActivity}
 * and updating the UI as required.
 */

class CameraPresenter implements CameraContract.CameraPresenter {
    private CameraContract.CameraView cameraView;

    CameraPresenter(CameraContract.CameraView cameraView) {
        this.cameraView = cameraView;

    }
}
