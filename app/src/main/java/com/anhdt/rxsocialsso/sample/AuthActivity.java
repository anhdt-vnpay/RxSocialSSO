package com.anhdt.rxsocialsso.sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.anhdt.rxsocialsso.library.auth.RxFacebookAuth;
import com.anhdt.rxsocialsso.library.auth.RxGoogleAuth;
import com.anhdt.rxsocialsso.library.common.RxAccount;
import com.anhdt.rxsocialsso.library.smartlock.RxSmartLockPasswords;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.IdentityProviders;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.toString();
    // xml elements
    @BindView(R.id.google_button) Button mGoogleButton;
    @BindView(R.id.facebook_button) Button mFacebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        // butter knife
        ButterKnife.bind(this);

        // request smart lock credential on launch
        new RxSmartLockPasswords.Builder(this)
                .disableAutoSignIn()
                .setAccountTypes(IdentityProviders.GOOGLE, IdentityProviders.FACEBOOK)
                .build()
                .requestCredentialAndAutoSignIn()
                .subscribe(o -> {
                    if(o instanceof RxAccount) {
                        // user is signed in using google or facebook
                        RxAccount rxAccount = (RxAccount) o;
                        // log info
                        Log.d(TAG, "provider: " + rxAccount.getProvider());
                        Log.d(TAG, "userId: " + rxAccount.getId());
                        Log.d(TAG, "photoUrl: " +
                                (rxAccount.getPhotoUri() != null? rxAccount.getPhotoUri().toString(): ""));
                        Log.d(TAG, "accessToken: " + rxAccount.getAccessToken());
                        Log.d(TAG, "firstname: " + rxAccount.getFirstname());
                        Log.d(TAG, "lastname: " + rxAccount.getLastname());
                        Log.d(TAG, "name: " + rxAccount.getDisplayName());
                        Log.d(TAG, "email: " + rxAccount.getEmail());

                        Toast.makeText(AuthActivity.this, "Hello " + rxAccount.getDisplayName(),
                                Toast.LENGTH_SHORT).show();
                        // go to main activity
                        startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        finish();
                    }

                    else if(o instanceof Credential) {
                        Credential credential = (Credential) o;

                        if(credential.getAccountType() == null) {
                            // credential contains login and password
                            signInWithLoginPassword(credential.getId(), credential.getPassword());
                        }
                        else {
                            // credential from other provider than Google or Facebook
                            handleCredential(credential);
                        }
                    }

                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });
    }

    @OnClick(R.id.google_button)
    public void googleSignIn() {
        // build RxGoogleAuth object
        RxGoogleAuth rxGoogleAuth = new RxGoogleAuth.Builder(this)
                .requestIdToken(getString(R.string.oauth_google_key))
                .requestEmail()
                .requestProfile()
                .disableAutoSignIn()
                .enableSmartLock(true)
                .build();

        // sign in
        rxGoogleAuth.signIn()
                .subscribe(rxAccount -> {
                    // log info
                    Log.d(TAG, "provider: " + rxAccount.getProvider());
                    Log.d(TAG, "userId: " + rxAccount.getId());
                    Log.d(TAG, "photoUrl: " +
                            (rxAccount.getPhotoUri() != null? rxAccount.getPhotoUri().toString(): ""));
                    Log.d(TAG, "accessToken: " + rxAccount.getAccessToken());
                    Log.d(TAG, "firstname: " + rxAccount.getFirstname());
                    Log.d(TAG, "lastname: " + rxAccount.getLastname());
                    Log.d(TAG, "name: " + rxAccount.getDisplayName());
                    Log.d(TAG, "email: " + rxAccount.getEmail());

                    Toast.makeText(AuthActivity.this, "Hello " + rxAccount.getDisplayName(),
                            Toast.LENGTH_SHORT).show();
                    // go to main activity
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();

                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });
    }

    @OnClick(R.id.facebook_button)
    public void facebookSignIn() {
        // build RxFacebookAuth object
        RxFacebookAuth rxFacebookAuth = new RxFacebookAuth.Builder(this)
                .enableSmartLock(true)
                .requestEmail()
                .requestProfile()
                .requestPhotoSize(200, 200)
                .build();

        // sign in
        rxFacebookAuth.signIn()
                .subscribe(rxAccount -> {
                    // log info
                    Log.d(TAG, "provider: " + rxAccount.getProvider());
                    Log.d(TAG, "userId: " + rxAccount.getId());
                    Log.d(TAG, "photoUrl: " +
                            (rxAccount.getPhotoUri() != null? rxAccount.getPhotoUri().toString(): ""));
                    Log.d(TAG, "accessToken: " + rxAccount.getAccessToken());
                    Log.d(TAG, "firstname: " + rxAccount.getFirstname());
                    Log.d(TAG, "lastname: " + rxAccount.getLastname());
                    Log.d(TAG, "name: " + rxAccount.getDisplayName());
                    Log.d(TAG, "email: " + rxAccount.getEmail());

                    Toast.makeText(AuthActivity.this, "Hello " + rxAccount.getDisplayName(),
                            Toast.LENGTH_SHORT).show();
                    // go to main activity
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();

                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });
    }

    private void signInWithLoginPassword(String login, String password) {
        // authenticate the user like you want
        // here we'll just go to the main activity
        Toast.makeText(AuthActivity.this, "Hello " + login,
                Toast.LENGTH_SHORT).show();
        // go to main activity
        startActivity(new Intent(AuthActivity.this, MainActivity.class));
        finish();
    }

    private void handleCredential(Credential credential) {
        // do your logic here
    }

}
