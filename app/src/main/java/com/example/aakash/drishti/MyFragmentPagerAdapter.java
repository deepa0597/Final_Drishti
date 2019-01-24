package com.example.aakash.drishti;

import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    TextToSpeech t1;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RequestFragment requestFragment=new RequestFragment();
                return requestFragment;
            case 1:
                ChatFragment chatFragment=new ChatFragment();
                return chatFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2 ;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position){
            case 0:
                return "USERS";
            case 1:
                return "CHATS";
            default:
                return null;
        }
    }

}
