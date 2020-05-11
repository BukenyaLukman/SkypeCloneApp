package com.example.skypeclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CallingActivity extends AppCompatActivity {

    private TextView nameContact;
    private ImageView profileImage;
    private ImageView CancelCallBtn, makeCallBtn;

    private String receiverUserID = "",receiverUserImage = "",receiverUserName = "";
    private String SenderUserID = "",SenderUserImage = "",SenderUserName = "";
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);


        SenderUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        nameContact = findViewById(R.id.name_calling);
        profileImage = findViewById(R.id.profile_image_calling);
        CancelCallBtn = findViewById(R.id.cancel_call);
        makeCallBtn = findViewById(R.id.make_call);

        getAndSetUserProfileInfo();
    }

    private void getAndSetUserProfileInfo() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(receiverUserID).exists()){
                    receiverUserImage = dataSnapshot.child(receiverUserID).child("image")
                            .getValue().toString();
                    receiverUserName = dataSnapshot.child(receiverUserID).child("name")
                            .getValue().toString();
                    nameContact.setText(receiverUserName);
                    Picasso.get().load(receiverUserImage).placeholder(R.drawable.profile_image).into(profileImage);



                }
                if(dataSnapshot.child(SenderUserID).exists()){
                    SenderUserImage = dataSnapshot.child(SenderUserID).child("image")
                            .getValue().toString();
                    SenderUserName = dataSnapshot.child(SenderUserID).child("name")
                            .getValue().toString();
                    nameContact.setText(receiverUserName);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
