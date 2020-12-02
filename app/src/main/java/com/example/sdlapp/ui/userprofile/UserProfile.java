package com.example.sdlapp.ui.userprofile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.sdlapp.ImageLoadTask;
import com.example.sdlapp.Login;
import com.example.sdlapp.R;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UserProfile extends Fragment {

    private static final int SELECT_PHOTO = 1;
    private UserProfileViewModel mViewModel;
    com.google.android.material.textfield.TextInputLayout textInputLayout,textInputLayout1;
    ImageView circleImageView;
    EditText editText,editText1;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    Bitmap bitmap;
    ProgressBar progressBar;
    com.google.android.material.button.MaterialButton button;


    public static UserProfile newInstance() {
        return new UserProfile();
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.userprofile_fragment, container, false);
       circleImageView=root.findViewById(R.id.profile_image);
       editText=root.findViewById(R.id.editText);
       editText1=root.findViewById(R.id.email_et);
       progressBar=root.findViewById(R.id.progress_bar);
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseStorage=FirebaseStorage.getInstance();
       storageReference=firebaseStorage.getReference();
       firestore=FirebaseFirestore.getInstance();
       button=root.findViewById(R.id.done_button);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressBar.setVisibility(View.VISIBLE);
               StorageReference storageReference1=storageReference.child("profilePic").child(firebaseAuth.getUid());
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
               byte[] data = baos.toByteArray();

               UploadTask uploadTask = storageReference1.putBytes(data);
               uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               DocumentReference documentReference=firestore.collection("users").document(firebaseAuth.getUid());
                               firestore.collection("users").document(firebaseAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                   @Override
                                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                                       Map<String,Object> mapp=documentSnapshot.getData();
                                       mapp.put("profileUrl",uri.toString());
                                       documentReference.update(mapp);
                                       Toast.makeText(getContext(),"Profile Picture Changed",Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.INVISIBLE);
                                   }
                               });
                           }
                       });
                   }
               });
           }
       });
//       firestore.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//           @Override
//           public void onSuccess(DocumentSnapshot documentSnapshot) {
//               editText.setText((CharSequence) documentSnapshot.get("fname"));
//           }
//       });
        firestore.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fName=(String) documentSnapshot.get("fName");
                String profilePic=(String) documentSnapshot.get("profileUrl");
                if(profilePic!=null)
                new ImageLoadTask(profilePic, circleImageView).execute();
                Log.d("abc",fName);
                editText.setText((CharSequence) documentSnapshot.get("fName"));
                editText1.setText((CharSequence) documentSnapshot.get("email"));
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(Intent.ACTION_PICK);
              intent.setType("image/*");
              startActivityForResult(intent,SELECT_PHOTO);
            }
        });
        return root;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PHOTO && resultCode== RESULT_OK && data!=null && data.getData()!=null){
            Uri uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
               circleImageView.setImageBitmap(bitmap);
               button.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getContext(), Login.class);
            startActivity(intent);
            getActivity().getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.userprofilemenu,menu);
    }


}