package com.example.patitas.petdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.patitas.R;
import com.example.patitas.data.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

public class PetDetailFragment extends Fragment implements PetDetailContract.View {

    private PetDetailContract.Presenter presenter;

    @BindView(R.id.pet_detail_name)
    TextView nameView;

    @BindView(R.id.pet_detail_image)
    ImageView imageView;

    @BindView(R.id.pet_detail_user)
    TextView userView;

    @BindView(R.id.pet_detail_progress_bar)
    ProgressBar progressBar;

    public PetDetailFragment() {
    }

    public static PetDetailFragment newInstance() {
        return new PetDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pet_detail_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.start();
    }

    @Override
    public void setPresenter(PetDetailContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showPetDetail(Pet pet) {
        Glide.with(this.getActivity())
                .load(pet.getRemoteImageUri())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        PetDetailFragment.this.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        PetDetailFragment.this.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(this.imageView);

        this.nameView.setText(pet.getPetName());
        this.userView.setText(String.format(getString(R.string.posted_by), pet.getPetUserName()));
    }
}
