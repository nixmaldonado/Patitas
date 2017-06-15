package com.example.patitas.peteditor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.patitas.R;
import com.example.patitas.data.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static dagger.internal.Preconditions.checkNotNull;

public class PetEditorFragment extends Fragment implements PetEditorContract.View {

    PetEditorContract.Presenter presenter;
    private static final int RC_PHOTO_PICKER = 1;

    @BindView(R.id.name_input)
    EditText nameEditText;

    @BindView(R.id.image_input)
    ImageView imagePreview;

    @BindView(R.id.gallery_input)
    LinearLayout galleryButton;

    @BindView(R.id.edit_image)
    ImageButton editImage;

    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.pet_editor_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void setPresenter(PetEditorContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showPetEditor(Pet pet) {

    }

    public static PetEditorFragment newInstance(){
        return new PetEditorFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null){
            this.imageUri = data.getData();

            Glide.with(this).load(this.imageUri).into(this.imagePreview);

            this.galleryButton.setVisibility(View.GONE);
            this.editImage.setVisibility(View.VISIBLE);
        }

    }

    public boolean hasRequiredInput() {
        if (this.nameEditText.getText().toString().isEmpty() || !this.hasImageUri()) {
            Toast.makeText(this.getContext(),
                    R.string.provide_fields, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void setPresenter() {

    }

    private boolean hasImageUri() {
        return this.imageUri != null;
    }

    public void createNewPet() {
        Pet pet = new Pet(nameEditText.getText().toString().trim(), imageUri.toString());
        presenter.savePet(pet);
    }
}
