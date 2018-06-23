package com.example.mark.unioil.signature;

/**
 * Created by PROGRAMMER2 on 6/14/2018.
 * ABZTRAK INC.
 */

interface SignatureContract {
    interface SignatureView {
        void showCaptureScreen();

        void clearSignatureScreen();
    }

    interface SignaturePresenter {
        void handleSaveMenuClick();

        void handleClearMenuClick();
    }
}
