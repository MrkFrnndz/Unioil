package com.example.mark.unioil;

/**
 * Created by PROGRAMMER2 on 6/14/2018.
 * ABZTRAK INC.
 */

public class Constants {

    private static Constants singletonConstants;
    private String FTPHost = "files.000webhost.com";
    private String user = "attendancemonitor";
    ////        MainActivity Constants         ////

    ////        SignatureActivity Constants     ////

    ////        CaptureActivity Constants       ////

    ////        SubmitActivity Constants        ////
    private String pass = "darksalad12";
    private int PORT = 21;

    private Constants() {

    }

    public static Constants getSingletonConstants() {
        if (singletonConstants == null) {
            singletonConstants = new Constants();
        }
        return singletonConstants;
    }

    public String getFTPHost() {
        return FTPHost;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public int getPORT() {
        return PORT;
    }
}
