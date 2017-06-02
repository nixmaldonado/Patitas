package com.example.patitas;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.patitas.domain.pet.Pet;

import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {

    public PetAdapter(Context context, int resource, List<Pet> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = ((Activity) this.getContext()).getLayoutInflater()
                    .inflate(R.layout.pet_item, parent, false);
        }

        Pet pet = this.getItem(position);

        TextView petName = (TextView) view.findViewById(R.id.pet_name);
        petName.setText(pet.getName());

        ImageView petImage = (ImageView) view.findViewById(R.id.pet_image);
        Glide.with(petImage.getContext())
                .load(pet.getRemoteImageUri())
                .into(petImage);

        return view;
    }
}
