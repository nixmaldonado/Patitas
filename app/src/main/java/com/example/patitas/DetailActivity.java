package com.example.patitas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = this.getIntent().getExtras();
        if(b!= null){
            Pet selectedPet = b.getParcelable("pet");
            setView(selectedPet);
        }
    }

    public void setView(Pet pet) {
        ImageView petImage = (ImageView) findViewById(R.id.detail_image);
        Glide.with(petImage.getContext()).load(pet.getPhotoUrl()).into(petImage);
        TextView petName = (TextView) findViewById(R.id.detail_name);
        petName.setText(pet.getName());
    }
}
