package com.example.patitas;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {
    public PetAdapter(Context context, int resource, List<Pet> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.pet_item, parent, false);
        }
        ImageView petImage = (ImageView) convertView.findViewById(R.id.pet_image);
        TextView petName = (TextView) convertView.findViewById(R.id.pet_name);

        Pet pet = getItem(position);

        petName.setText(pet.getName());
        Glide.with(petImage.getContext())
                .load(pet.getPhotoUrl())
                .into(petImage);
        return convertView;
    }
}
