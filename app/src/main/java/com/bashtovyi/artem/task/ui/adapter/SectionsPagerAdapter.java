package com.bashtovyi.artem.task.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.bashtovyi.artem.task.ui.fragment.NotificationFragment;

import java.util.ArrayList;
import java.util.Random;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "SectionsPagerAdapter";

    // part of android framework UI components
    private ArrayList<NotificationFragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<NotificationFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    /*// TODO : probably move to Activity,but it would be God object
    // returns position of added fragment
    public int addFragment() {
        int lastNumber = fragments.get(fragments.size()-1).getNumber();
        fragments.add(NotificationFragment.newInstance(++lastNumber,
                new Random().nextInt(50) + 1));
        notifyDataSetChanged();
        return fragments.size();
    }

    public int delete(int position) {
        NotificationFragment fragment = fragments.get(position);
        fragment.destroyAllNotifications();
        fragments.remove(fragment);
        notifyDataSetChanged();
        return position - 1;
    }


    public int getPositionByNumber(int numberOfFragment) {
        int counter = 0;
        for (NotificationFragment fragment : fragments) {
            if (fragment.getNumber() == numberOfFragment) return counter;
            counter++;
        }
        return -1;
    }*/

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
