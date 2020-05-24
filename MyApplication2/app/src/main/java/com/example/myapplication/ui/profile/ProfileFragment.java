package com.example.myapplication.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DataContent;
import com.example.myapplication.Database;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private static final int GALLERY_REQUEST_CODE = 1 ;

    ImageView image;
    TextView changeImage, username, save;
    Database db;
    DataContent userDataContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        db = new Database(getContext());
        userDataContent = com.example.myapplication.DataContent.getInstance();

        image = (ImageView) root.findViewById(R.id.profileImg);
        changeImage = (TextView) root.findViewById(R.id.changeImage);
        save = (TextView) root.findViewById(R.id.saveImage);
        username = (TextView) root.findViewById(R.id.username);

        String email = userDataContent.getEmail();
        Bitmap bitmap = db.getUserImage(email);
        if (bitmap == null){
            image.setImageResource(R.drawable.ic_profile);
        }else {
            image.setImageBitmap(bitmap);
        }

        String userName = db.getUsername(email);
        username.setText(userName);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setEnabled(true);
                changeImage.setEnabled(false);
                selectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = userDataContent.getUserId();

                BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                final byte[] sUserImage = getBitmapAsByteArray(bitmap);
                String base64Image = Base64.encodeToString(sUserImage, Base64.DEFAULT);
                db.updateUserImage(id, base64Image);
                changeImage.setEnabled(true);
                save.setEnabled(false);
            }
        });

        return root;
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    image.setImageURI(selectedImage);
                    break;
            }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}