package com.example.patitas.peteditor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.patitas.R;
import com.example.patitas.data.Pet;
import com.example.patitas.pets.PetsRepository;
import com.example.patitas.data.FirebasePetsRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RC_PHOTO_PICKER       = 1;

    @BindView(R.id.gallery_input)
    LinearLayout galleryButton;

    @BindView(R.id.image_input)
    ImageView imagePreview;

    @BindView(R.id.name_input)
    EditText nameEditText;

    @BindView(R.id.edit_image)
    ImageButton editImage;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    private Uri imageUri;

    protected PetsRepository petsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        this.petsRepository = new FirebasePetsRepository();

        this.setSupportActionBar(this.toolbar);
    }

    @OnClick({R.id.gallery_input, R.id.edit_image})
    protected void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/jpeg")
                .putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        this.startActivityForResult(
                Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_editor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                this.handleSaveAction();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_IMAGE_CAPTURE || resultCode != RESULT_OK) return;

        this.imageUri = data.getData();

        Glide.with(this).load(this.imageUri).into(this.imagePreview);

        this.galleryButton.setVisibility(View.GONE);
        this.editImage.setVisibility(View.VISIBLE);
    }

    private void handleSaveAction() {
        if (!this.hasRequiredInput()) return;

        this.petsRepository.save(new Pet(this.nameEditText.getText().toString(),
                                         this.imageUri.toString()));

        this.finish();
    }

    private boolean hasRequiredInput() {
        if (this.nameEditText.getText().toString().isEmpty() || !this.hasImageUri()) {
            Toast.makeText(EditorActivity.this,
                           R.string.provide_fields, Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private boolean hasImageUri() {
        return this.imageUri != null;
    }
}