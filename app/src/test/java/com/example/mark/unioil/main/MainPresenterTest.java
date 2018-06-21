package com.example.mark.unioil.main;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Local unit test for the Main Presenter.
 */
public class MainPresenterTest {

    @Mock
    private MainContract.MainView mainView;
    private MainPresenter mainPresenter;
    private View mockedView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(mainView);
        mockedView = Mockito.mock(View.class);
    }

    @Test
    public void handleProceedButtonClick() throws Exception {
        mainPresenter.handleProceedButtonClick(mockedView);
        verify(mainView).showSignatureScreen();
    }

    @Test
    public void handleEtUserNameOnTextChanged() throws Exception {
        mainPresenter.handleEtUserNameOnTextChanged();
        verify(mainView).checkEtUserNameContent();
    }

    @Test
    public void handleEtCustomerNameOnTextChanged() throws Exception {
        mainPresenter.handleEtCustomerNameOnTextChanged();
        verify(mainView).checkEtCustomerNameContent();
    }

    @Test
    public void handleDROnTextChanged() throws Exception {
        mainPresenter.handleDROnTextChanged();
        verify(mainView).checkDRContent();
    }

    @Test
    public void handleSearchDR() throws Exception {
        mainPresenter.handleSearchDR();
        verify(mainView).searchDR();
    }

    @Test
    public void handleImportProductMenuClick() throws Exception {
        mainPresenter.handleImportProductMenuClick();
        verify(mainView).importProduct();
    }

}