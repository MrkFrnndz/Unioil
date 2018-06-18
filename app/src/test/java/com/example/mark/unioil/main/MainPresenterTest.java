package com.example.mark.unioil.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Local unit test for the Main Presenter.
 */
public class MainPresenterTest {

    @Mock
    private MainContract.MainView mainView;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(mainView);
    }

    @Test
    public void handleProceedButtonClick() throws Exception {
        mainPresenter.handleProceedButtonClick();
        verify(mainView).showSignatureScreen();
    }

}