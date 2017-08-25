package com.example.patitas.pets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.patitas.R;
import com.example.patitas.auth.AuthActivity;
import com.example.patitas.data.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPetsFragment extends Fragment implements PetsContract.View {

    @BindView(R.id.user_pets_list)
    ListView userPetsList;

    @BindView(R.id.please_sign_in_text)
    TextView signInText;

    PetsContract.Presenter presenter;

    public UserPetsFragment() {
        new UserPetsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.user_pets_fragment, container, false);
        ButterKnife.bind(this, root);

        this.userPetsList.setAdapter(UserPetsAdapter.getInstance());
        this.presenter.start();
        return root;
    }

    @Override
    public void onResume() {
        this.refreshView();
        super.onResume();
    }

    @Override
    public void setPresenter(PetsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPetDetails(Pet pet) {

    }

    @Override
    public void refreshView() {
        if (AuthActivity.isUserSignedIn()) {
//            this.presenter.start();
            this.clearAuthMessage();
        } else {
            UserPetsAdapter.getInstance().clearPets();
            this.setAuthMessage();
        }
    }

    private void setAuthMessage() {
        this.signInText.setVisibility(View.VISIBLE);
    }

    private void clearAuthMessage() {
        this.signInText.setVisibility(View.INVISIBLE);
    }
}
