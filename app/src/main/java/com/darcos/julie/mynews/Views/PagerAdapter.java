package com.darcos.julie.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.darcos.julie.mynews.Fragments.MostPopularFragment;
import com.darcos.julie.mynews.Fragments.SportsFragment;
import com.darcos.julie.mynews.Fragments.TopStoriesFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    //page of viewPager
    private final String tabTitles[] = new String[]{"Top Stories", "Most popular", "Sport"};
    private final int PAGE_COUNT = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    /**
     * @param position
     * @return fragment with list of article
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {

            return new TopStoriesFragment();

        }
        if (position == 1) {
            return new MostPopularFragment();
        }
        if (position == 2) {
            return new SportsFragment();
        } else {

            return new MostPopularFragment();

        }
    }


    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
