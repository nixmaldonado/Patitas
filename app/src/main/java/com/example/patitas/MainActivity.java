package com.example.patitas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.patitas.auth.AuthActivity;
import com.example.patitas.petcreator.PetCreatorActivity;
import com.example.patitas.pets.FragmentPager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.patitas.R.id.fab;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPager viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        FragmentPager fragmentAdapter = new FragmentPager(this.getSupportFragmentManager(), this);
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.sliding_tabs);

        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.pets_toolbar);
        this.setSupportActionBar(toolbar);
    }

    @OnClick(fab)
    protected void addPet() {
        if (AuthActivity.isUserSignedIn()) {
            Intent editorIntent = new Intent(this, PetCreatorActivity.class);
            this.startActivityForResult(editorIntent, 1);
        } else {
            this.signInToast();
        }
    }

    public void signInToast() {
        Toast.makeText(this, "Sign In to add a Pet", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_pets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_in) {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

}
