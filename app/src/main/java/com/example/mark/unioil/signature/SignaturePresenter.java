package com.example.mark.unioil.signature;

/**
 * Created by PROGRAMMER2 on 6/14/2018.
 * ABZTRAK INC.
 */

public class SignaturePresenter implements SignatureContract.SignaturePresenter {
    private SignatureContract.SignatureView signatureView;

    SignaturePresenter(SignatureContract.SignatureView signatureView) {
        this.signatureView = signatureView;
    }
}
