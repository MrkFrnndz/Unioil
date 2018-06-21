package com.example.mark.unioil.main;

import android.text.TextWatcher;
import android.view.View;

/**
 * Responsible for handling actions from MainView {@link MainActivity}
 * and updating the UI as required.
 */

public class MainPresenter implements MainContract.MainPresenter {
    private MainContract.MainView mainView;

    MainPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
    }

    //      Presenter Method        //
    @Override
    public void handleProceedButtonClick(View view) {
        mainView.showSignatureScreen();
    }

    @Override
    public TextWatcher handleDROnTextChanged() {
        return mainView.checkDRContent();
    }

    @Override
    public TextWatcher handleEtUserNameOnTextChanged() {
        return mainView.checkEtUserNameContent();
    }

    @Override
    public TextWatcher handleEtCustomerNameOnTextChanged() {
        return mainView.checkEtCustomerNameContent();
    }

    @Override
    public void handleSearchDR() {
        mainView.searchDR();
    }

    @Override
    public void handleImportProductMenuClick() {
        mainView.importProduct();
    }
}
