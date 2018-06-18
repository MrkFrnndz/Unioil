package com.example.mark.unioil.main;

/**
 * Defines the contract between MainView {@link MainActivity}
 * and MainPresenter {@link MainPresenter}.
 */

interface MainContract {
    interface MainView {
        void showSignatureScreen();
    }

    interface MainPresenter {
        void handleProceedButtonClick();
    }
}
