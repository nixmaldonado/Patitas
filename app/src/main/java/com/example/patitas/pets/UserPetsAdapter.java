package com.example.patitas.pets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patitas.R;
import com.example.patitas.data.Pet;

import java.util.ArrayList;
import java.util.List;

public class UserPetsAdapter extends BaseAdapter {

    private static final UserPetsAdapter INSTANCE = new UserPetsAdapter();
    private static List pets = new ArrayList<>();

    private UserPetsAdapter() {
    }

    public static UserPetsAdapter getInstance() {
        return INSTANCE;
    }

    public static List getPetList() {
        return pets;
    }

    public void replacePetsData(List pets) {
        UserPetsAdapter.pets = pets;
        this.notifyDataSetChanged();
    }

    public void clearPets() {
        pets.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pets.size();
    }

    @Override
    public Pet getItem(int position) {
        return (Pet) pets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.pet_item, parent, false);
        }

        Pet pet = this.getItem(position);

        TextView petName = (TextView) view.findViewById(R.id.pet_name);
        petName.setText(pet.getPetName());

        TextView userName = (TextView) view.findViewById(R.id.list_posted_by);
        userName.setText(parent.getContext().getString(R.string.posted_by, " " + pet.getPetUserName()));

        ImageView petImage = (ImageView) view.findViewById(R.id.pet_image);
        Glide.with(petImage.getContext())
                .load(pet.getRemoteImageUri())
                .into(petImage);

        return view;
    }
}
