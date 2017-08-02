package com.example.patitas.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patitas.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{


    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private static FirebaseUser firebaseUser;


    private GoogleApiClient mGoogleApiClient;
    private TextView titleText;
    private TextView subtitleText;
    private SignInButton signInButton;
    private LinearLayout signOutButton;
    private static boolean isSignedIn;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sign_in);

        this.titleText = (TextView) this.findViewById(R.id.sign_in_title_text);
        this.subtitleText = (TextView) this.findViewById(R.id.sign_in_subtitle);

        this.findViewById(R.id.google_sign_in).setOnClickListener(this);
        this.findViewById(R.id.sign_out_button).setOnClickListener(this);

        this.signInButton = (SignInButton) this.findViewById(R.id.google_sign_in);
        this.signOutButton = (LinearLayout) this.findViewById(R.id.sign_out_and_disconnect);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.oauth_id))
                .requestEmail()
                .build();

        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseUser = this.mAuth.getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isUserSignedIn()){
            this.updateUI(firebaseUser);
        }
    }

    public static boolean isUserSignedIn() {
        return isSignedIn;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_sign_in:
                this.signIn();
                break;
            case R.id.sign_out_button:
                this.signOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient);
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                this.firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        this.mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = SignInActivity.this.mAuth.getCurrentUser();
                    SignInActivity.this.updateUI(user);
                    isSignedIn = true;
                } else {
                    Toast.makeText(SignInActivity.this, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user){
        this.titleText.setText("Hello " + user.getDisplayName().split(" ")[0] + "!");
        this.signInButton.setVisibility(View.INVISIBLE);
        this.signOutButton.setVisibility(View.VISIBLE);
        this.subtitleText.setVisibility(View.INVISIBLE);

    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                SignInActivity.this.titleText.setText(R.string.signed_out);
            }
        });
        this.mAuth.signOut();
        isSignedIn = false;
        this.signInButton.setVisibility(View.VISIBLE);
        this.signOutButton.setVisibility(View.INVISIBLE);
        this.subtitleText.setVisibility(View.VISIBLE);

    }
}
