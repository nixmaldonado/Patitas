package com.example.patitas.petdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patitas.R;
import com.example.patitas.data.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detail_image)
    ImageView petImage;

    @BindView(R.id.detail_name)
    TextView  petName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.setView(bundle.<Pet>getParcelable("pet"));
        }
    }

    public void setView(final Pet pet) {
        Glide.with(this.petImage.getContext())
                .load(pet.getRemoteImageUri())
                .into(this.petImage);

        this.petName.setText(pet.getName());

        this.toolbarTitle.setText(pet.getName());
    }
}
