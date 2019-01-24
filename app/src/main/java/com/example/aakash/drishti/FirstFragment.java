package com.example.aakash.drishti;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment
{
    private ViewPager viewPager;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public FirstFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;
        rootView=inflater.inflate(R.layout.fragment_first, container, false);
        viewPager=(ViewPager)rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FirstScreen(),"First Screen");
        adapter.addFragment(new SecondScreen(),"Second Screen");
        viewPager.setAdapter(adapter);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mfragment=new ArrayList<>();
        private final List<String> fmtitle=new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {  return mfragment.get(position);   }

        @Override
        public int getCount()
        {   return mfragment.size(); }

        public void addFragment(Fragment fragment, String title)
        {
            mfragment.add(fragment);
            fmtitle.add(title);
        }
        public CharSequence getPageTitle(int position) {
            return fmtitle.get(position);
        }
    }

}