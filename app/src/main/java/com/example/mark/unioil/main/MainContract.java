package com.example.mark.unioil.main;

import android.text.TextWatcher;
import android.view.View;

/**
 * Defines the contract between MainView {@link MainActivity}
 * and MainPresenter {@link MainPresenter}.
 */

interface MainContract {
    interface MainView {
        void showSignatureScreen();

        TextWatcher checkDRContent();

        TextWatcher checkEtUserNameContent();

        TextWatcher checkEtCustomerNameContent();

        void searchDR();

        void importProduct();
    }

    interface MainPresenter {
        void handleProceedButtonClick(View view);

        TextWatcher handleDROnTextChanged();

        TextWatcher handleEtUserNameOnTextChanged();

        TextWatcher handleEtCustomerNameOnTextChanged();

        void handleSearchDR();

        void handleImportProductMenuClick();
    }
}
