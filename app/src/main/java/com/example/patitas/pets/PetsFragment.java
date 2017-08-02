package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.patitas.R;
import com.example.patitas.petdetail.PetDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static dagger.internal.Preconditions.checkNotNull;

public class PetsFragment extends Fragment implements PetsContract.View {

    public static final String EXTRA_PET_ID = "petId";

    private static PetsContract.Presenter presenter;

    @BindView(R.id.pets_list)
    ListView petsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("value", 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_pets_fragment, container, false);
        ButterKnife.bind(this, root);


        this.petsList.setAdapter(PetsAdapter.getInstance());

        this.setHasOptionsMenu(true);

        return root;
    }

    @OnItemClick(R.id.pets_list)
    public void showPet(int position) {
        presenter.openPetDetail(PetsAdapter.getInstance().getItem(position));
    }

    @Override
    public void showPetDetails(String petId) {
        Intent intent = new Intent(this.getActivity(), PetDetailActivity.class);

        intent.putExtra(EXTRA_PET_ID, petId);

        this.startActivity(intent);
    }

    @Override
    public void setPresenter(PetsContract.Presenter presenter) {
        PetsFragment.presenter = checkNotNull(presenter);
    }

}
