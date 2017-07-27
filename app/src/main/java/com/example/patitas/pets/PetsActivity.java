package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.patitas.R;
import com.example.patitas.petcreator.PetCreatorActivity;
import com.example.patitas.util.ActivityUtils;
import com.example.patitas.util.Injection;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PetsActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MenuItem signIn;
    private MenuItem signOut;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        this.initToolbar();

        PetsFragment petsFragment = (PetsFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (petsFragment == null) {
            petsFragment = PetsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    this.getSupportFragmentManager(), petsFragment, R.id.contentFrame);
        }

        new PetsPresenter(Injection.providePetsRepository(), petsFragment);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.oauth_id))
                .requestEmail()
                .build();

        this.googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_catalog, menu);
        this.signIn = menu.findItem(R.id.action_log_in);
        this.signOut = menu.findItem(R.id.action_log_out);
        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Please Log In", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hello" + user.getDisplayName(), Toast.LENGTH_SHORT);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_log_in){
            this.signIn();
        } else {
            this.signOut();
        }
        return super.onOptionsItemSelected(item);
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

    @OnClick(R.id.fab)
    protected void addPet() {
        Intent editorIntent = new Intent(this, PetCreatorActivity.class);
        this.startActivity(editorIntent);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient);
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(PetsActivity.this, "Signed Out",
                Toast.LENGTH_SHORT).show();
        this.signIn.setVisible(true);
        this.signOut.setVisible(false);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = PetsActivity.this.firebaseAuth.getCurrentUser();
                            PetsActivity.this.updateUI(user);

                        } else {
                            Toast.makeText(PetsActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Toast.makeText(PetsActivity.this, "Hello " + user.getDisplayName(),
                Toast.LENGTH_SHORT).show();
        this.signIn.setVisible(false);
        this.signOut.setVisible(true);
    }

    private void initToolbar() {
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setLogo(R.drawable.ic_pets_white_24dp);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
