package com.example.patitas.petcreator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.patitas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static dagger.internal.Preconditions.checkNotNull;

public class PetCreatorFragment extends Fragment implements PetCreatorContract.View {

    private static final int RC_PHOTO_PICKER = 1;

    private PetCreatorContract.Presenter presenter;

    private Uri imageUri;

    @BindView(R.id.name_input)
    protected EditText nameEditText;

    @BindView(R.id.gallery_input)
    protected Button galleryButton;

    @BindView(R.id.image_input)
    protected ImageView imagePreview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_editor_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.done_creating)
    public void done(){
        if (this.hasRequiredInput()){
            this.presenter.createPet(this.getName(), this.getImageUri());
            this.getActivity().finish();
        }else{
            this.triggerToast(R.string.provide_fields);
        }

    }

    @Override
    public void setPresenter(PetCreatorContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    public static PetCreatorFragment newInstance() {
        return new PetCreatorFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.start();
    }

    @OnClick({R.id.gallery_input})
    protected void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/jpeg")
                .putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        this.startActivityForResult(
                Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null) {
            this.imageUri = data.getData();

            Glide.with(this).load(this.imageUri).into(this.imagePreview);

            this.imagePreview.setVisibility(View.VISIBLE);
        }

    }

    public void triggerToast(int message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public String getName(){ return this.nameEditText.getText().toString().trim(); }

    public String getImageUri(){
        return this.imageUri.toString();
    }

    private boolean hasRequiredInput() {
        return (!this.nameEditText.getText().toString().isEmpty() && this.imageUri != null);
    }

}
