package com.example.patitas.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patitas.R;
import com.example.patitas.data.source.FirebasePetsRepository;
import com.example.patitas.pets.UserPetsAdapter;
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

public class AuthActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private GoogleApiClient mGoogleApiClient;
    private String userName;

    private TextView titleText;
    private TextView subtitleText;
    private SignInButton signInButton;
    private ProgressBar progressBar;

    public static boolean isUserSignedIn() {
        return firebaseUser != null;
    }

    public static String getCurrentUserId() {
        return firebaseUser.getUid();
    }

    public static String getUserName() {
        return firebaseUser.getDisplayName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_auth);

        this.titleText = (TextView) this.findViewById(R.id.sign_in_title_text);
        this.subtitleText = (TextView) this.findViewById(R.id.sign_in_subtitle);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progres_bar_sign_in);

        this.findViewById(R.id.google_sign_in).setOnClickListener(this);

        this.signInButton = (SignInButton) this.findViewById(R.id.google_sign_in);

        this.setSupportActionBar((Toolbar) this.findViewById(R.id.sign_in_toolbar));

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.oauth_id))
                .requestEmail()
                .build();

        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseUser == null) {
            this.updateUIForSignOut();
        } else {
            this.updateUIForSignIn(firebaseUser.getDisplayName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out_button){
            this.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                this.firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AuthActivity.this.userName = account.getDisplayName();
                            firebaseUser = mAuth.getCurrentUser();
                            AuthActivity.this.updateUIForSignIn(AuthActivity.this.userName);
                            FirebasePetsRepository.getInstance().setListenerForUserPets();
                            AuthActivity.this.finish();
                        } else {
                            Toast.makeText(AuthActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        this.progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient);
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUIForSignIn(String displayName) {
        this.signInButton.setVisibility(View.GONE);
        this.subtitleText.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.GONE);
        this.titleText.setText(this.getString(R.string.hello) +
                " " + displayName.split(" ")[0] + "!");
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });
        mAuth.signOut();
        firebaseUser = null;
        UserPetsAdapter.getInstance().clearPets();
        this.updateUIForSignOut();
    }

    private void updateUIForSignOut() {
        this.signInButton.setVisibility(View.VISIBLE);
        this.subtitleText.setVisibility(View.VISIBLE);
        this.titleText.setText(R.string.howdy);
        this.subtitleText.setText(R.string.please_sign_in);
    }
}
