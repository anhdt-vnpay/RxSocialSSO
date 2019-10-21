package com.anhdt.rxsocialsso.library.smartlock;


public class SmartLockOptions {

    public boolean requestEmail;
    public boolean requestProfile;

    public SmartLockOptions(boolean requestEmail, boolean requestProfile) {
        this.requestEmail = requestEmail;
        this.requestProfile = requestProfile;
    }

}
