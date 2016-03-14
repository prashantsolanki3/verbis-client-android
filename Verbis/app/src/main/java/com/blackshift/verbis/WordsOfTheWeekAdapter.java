package com.blackshift.verbis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Devika on 11-03-2016.
 */
public class WordsOfTheWeekAdapter extends FragmentStatePagerAdapter {

    public WordsOfTheWeekAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return WordOfTheDayFragment.newInstance("word" + position, "meaning" + position, "pronunciation"  + position);
    }

    @Override
    public int getCount() {
        return 7;
    }
}
