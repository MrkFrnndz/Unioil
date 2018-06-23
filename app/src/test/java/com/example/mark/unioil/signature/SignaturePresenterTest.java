package com.example.mark.unioil.signature;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Local unit test for the Main Presenter.
 */
public class SignaturePresenterTest {

    @Mock
    private SignatureContract.SignatureView signatureView;
    private SignaturePresenter signaturePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signaturePresenter = new SignaturePresenter(signatureView);
    }

    @Test
    public void handleProceedButtonClick() throws Exception {
        signaturePresenter.handleSaveMenuClick();
        verify(signatureView).showCaptureScreen();
    }

    @Test
    public void handleEtUserNameOnTextChanged() throws Exception {
        signaturePresenter.handleClearMenuClick();
        verify(signatureView).clearSignatureScreen();
    }
}