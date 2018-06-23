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

        TextWatcher checkEditTextContent();

        void searchDR();

        void importProduct();
    }

    interface MainPresenter {
        void handleProceedButtonClick(View view);

        TextWatcher handleDROnTextChanged();

        TextWatcher handleEditTextOnTextChanged();

        void handleSearchDR();

        void handleImportProductMenuClick();
    }
}
