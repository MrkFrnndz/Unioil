package com.example.mark.unioil.main;

/**
 * Responsible for handling actions from MainView {@link MainActivity}
 * and updating the UI as required.
 */

class MainPresenter implements MainContract.MainPresenter {
    private MainContract.MainView mainView;

    MainPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
    }

    //      Presenter Method        //
    @Override
    public void handleProceedButtonClick() {
        mainView.showSignatureScreen();
    }
}
