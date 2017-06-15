package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patitas.R;
import com.example.patitas.data.Pet;
import com.example.patitas.petdetail.PetDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static dagger.internal.Preconditions.checkNotNull;

public class PetsFragment extends Fragment implements PetsContract.View {

    public static final String EXTRA_PET_ID = "petId";

    private PetsContract.Presenter presenter;

    private PetsAdapter petsAdapter;

    @BindView(R.id.pets_list)
    ListView petsList;

    @BindView(R.id.no_pets)
    LinearLayout noPetsLayout;

    /*@BindView(R.id.no_pets_icon)
    ImageView noPetsIcon;*/

    public PetsFragment() {
    }

    public static PetsFragment newInstance() {
        return new PetsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.petsAdapter = new PetsAdapter(new ArrayList<Pet>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pets_fragment, container, false);
        ButterKnife.bind(this, root);

        this.petsList.setAdapter(this.petsAdapter);

        this.setHasOptionsMenu(true);

        return root;
    }

    @OnItemClick(R.id.pets_list)
    public void showPet(int position) {
        this.presenter.openPetDetail(this.petsAdapter.getItem(position));
    }

    @Override
    public void showPetDetails(String petId) {
        Intent intent = new Intent(this.getActivity(), PetDetailActivity.class);

        intent.putExtra(EXTRA_PET_ID, petId);

        this.startActivity(intent);
    }

    @Override
    public void setPresenter(PetsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showPets(List<Pet> pets) {
        this.noPetsLayout.setVisibility(View.INVISIBLE);
        this.petsAdapter.replaceData(pets);
    }

    @Override
    public void showNoPets() {
        this.noPetsLayout.setVisibility(View.VISIBLE);
    }

    private class PetsAdapter extends BaseAdapter {

        private List<Pet> pets;

        PetsAdapter(List<Pet> pets) {this.pets = pets;}

        void replaceData(List<Pet> pets) {
            this.setPets(pets);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.pets.size();
        }

        @Override
        public Pet getItem(int position) {
            return this.pets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                rowView = inflater.inflate(R.layout.pet_item, parent, false);
            }

            Pet pet = this.getItem(position);

            TextView petName = (TextView) rowView.findViewById(R.id.pet_name);
            petName.setText(pet.getName());

            ImageView petImage = (ImageView) rowView.findViewById(R.id.pet_image);
            Glide.with(petImage.getContext())
                    .load(pet.getRemoteImageUri())
                    .into(petImage);

            return rowView;
        }

        private void setPets(List<Pet> pets) {
            this.pets = checkNotNull(pets);
        }
    }
}
