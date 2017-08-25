package com.example.patitas.pets;

import android.content.Context;
import android.support.annotation.NonNull;
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

import static dagger.internal.Preconditions.checkNotNull;

public class PetsAdapter extends BaseAdapter {

    private static final PetsAdapter INSTANCE = new PetsAdapter();
    private static List pets = new ArrayList<>();

    private PetsAdapter() {}

    public static PetsAdapter getInstance(){
        return INSTANCE;
    }

    public void replaceData(List<Pet> pets) {
        PetsAdapter.pets = checkNotNull(pets);
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

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.pet_item, parent, false);
        }

        Pet pet = this.getItem(position);

        TextView petName = (TextView) view.findViewById(R.id.pet_name);
        petName.setText(pet.getPetName());

        TextView userName = (TextView) view.findViewById(R.id.list_posted_by);
        userName.setText(context.getString(R.string.posted_by,
                " " + pet.getPetUserName()));

        ImageView petImage = (ImageView) view.findViewById(R.id.pet_image);
        Glide.with(petImage.getContext())
                .load(pet.getRemoteImageUri())
                .into(petImage);

        return view;
    }
}