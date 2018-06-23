package com.example.mark.unioil.signature;

/**
 * Created by PROGRAMMER2 on 6/14/2018.
 * ABZTRAK INC.
 */

class SignaturePresenter implements SignatureContract.SignaturePresenter {
    private SignatureContract.SignatureView signatureView;

    SignaturePresenter(SignatureContract.SignatureView signatureView) {
        this.signatureView = signatureView;
    }

    @Override
    public void handleSaveMenuClick() {
        signatureView.showCaptureScreen();
    }

    @Override
    public void handleClearMenuClick() {
        signatureView.clearSignatureScreen();
    }

}
