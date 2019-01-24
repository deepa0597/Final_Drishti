package com.example.aakash.drishti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class RequestFragment extends Fragment
{
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mUsersDatabase;
    private  View mView;
    TextToSpeech tts;
    boolean twice;

    @Override
    public void onStart()
    {
        super.onStart();

        Toast.makeText(getActivity(), "All Users", Toast.LENGTH_SHORT).show();
        FirebaseRecyclerAdapter<Users , UserViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UserViewHolder>(
                Users.class,
                R.layout.recycle_list_single_user,
                UserViewHolder.class,
                mUsersDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, final Users users, int position) {
                viewHolder.setName(users.getName());
                viewHolder.setStatus(users.getStatus());
                viewHolder.setImage(users.getThumbImage(),getActivity());
                final String user_id=getRef(position).getKey();
                // final String user_name=getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(twice == true){
                            Intent chatIntent=new Intent(getActivity(),ChatActivity.class);

                            chatIntent.putExtra("user_id",user_id);
                            chatIntent.putExtra("user_name",users.getName());
                            startActivity(chatIntent);
                        }

                        else
                        {
                            twice = true;
                            tts.speak(users.getName() , TextToSpeech.QUEUE_FLUSH, null);
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                twice = false;

                            }
                        },2000);


                    }
                });


                tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i!=TextToSpeech.ERROR)
                            tts.setLanguage(Locale.UK);
                    }
                });
            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }



    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView userNameView=(TextView)mView.findViewById(R.id.textViewSingleListName);
            userNameView.setText(name);
        }


        public void setStatus(String status) {
            TextView userStatusView=(TextView)mView.findViewById(R.id.textViewSingleListStatus);
            userStatusView.setText(status);
        }

        public void setImage(String thumb_image,Context ctx) {
            CircleImageView userImageView = (CircleImageView)mView.findViewById(R.id.circleImageViewUserImage);
            //Log.e("thumb URL is--- ",thumb_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.ic_users).into(userImageView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_user, container, false);
        mUsersList=(RecyclerView)mView.findViewById(R.id.recyclerViewUsersList);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsersDatabaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        mUsersDatabaseReference.keepSynced(true);



        return mView;
    }
}
