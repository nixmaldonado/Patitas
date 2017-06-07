package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.patitas.peteditor.PetEditorActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

public class PetsFragment extends Fragment implements PetsContract.View {

    private PetsContract.Presenter presenter;
    private PetsAdapter petsAdapter;

    @BindView(R.id.pets_list) ListView petsList;
    @BindView(R.id.no_pets) LinearLayout noPetsLayout;
    @BindView(R.id.no_pets_icon) ImageView noPetsIcon;

    public PetsFragment() {
    }

    public PetsFragment newInstance() {
        return new PetsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pets_fragment, container, false);
        ButterKnife.bind(this, root);

        petsList.setAdapter(petsAdapter);

        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

        swipeRefreshLayout.setScrollUpChild(petsList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadPets(false);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setPresenter(PetsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout swipeRefresh =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(active);
            }
        });
    }

    @Override
    public void showPets(List<Pet> pets) {
        this.petsAdapter.replaceData(pets);
    }

    @Override
    public void showAddPets() {
        Intent addPetIntent = new Intent(getContext(), PetEditorActivity.class);
        startActivityForResult(addPetIntent, PetEditorActivity.REQUEST_ADD_PET);
    }

    @Override
    public void showPetDetailsUi(String petId) {
        Intent detailIntent = new Intent(getContext(), PetDetailActivity.class);
        detailIntent.putExtra(PetDetailActivity.EXTRA_PET_ID, petId);
        startActivity(detailIntent);
    }

    @Override
    public void showLoadingPetsError() {
        showMessage(getString(R.string.error_loading_pets));
    }

    @Override
    public void showNoPets() {
        showNoPetsViews(
                getResources().getString(R.string.no_pets),
                R.drawable.ic_pets_black_24dp,
                false
        );
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_message));
    }

    @Override
    public boolean isActive() {
        return this.isAdded();
    }

    PetItemListener petItemListener = new PetItemListener() {
        @Override
        public void onPetClick(Pet clickedPet) {
            presenter.openPetDetails(clickedPet);
        }
    };

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void showNoPetsViews(String mainText, int icon, boolean showAddView) {

    }

    private static class PetsAdapter extends BaseAdapter {

        private List<Pet> pets;

        private PetItemListener petItemListener;

        public PetsAdapter(List<Pet> pets, PetItemListener petItemlistener) {
            this.setList(pets);
            this.petItemListener = petItemlistener;
        }

        public void replaceData(List<Pet> pets) {
            this.setList(pets);
            this.notifyDataSetChanged();
        }

        private void setList(List<Pet> pets) {
            this.pets = checkNotNull(pets);
        }

        @Override
        public int getCount() {
            return pets.size();
        }

        @Override
        public Pet getItem(int position) {
            return pets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.pet_item, viewGroup, false);
            }

            final Pet pet = getItem(i);

            TextView name = (TextView) rowView.findViewById(R.id.pet_name);
            name.setText(pet.getName());

            ImageView petImage = (ImageView) rowView.findViewById(R.id.pet_image);
            Glide.with(petImage.getContext())
                    .load(pet.getRemoteImageUri())
                    .into(petImage);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    petItemListener.onPetClick(pet);
                }
            });

            return null;
        }
    }

    public interface PetItemListener {
        void onPetClick(Pet clickedPet);
    }
}
