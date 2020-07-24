package com.example.moneymanager.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewExpensesFragment extends Fragment {
    private View myFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public ViewExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_view_expenses, container, false);
        viewPager = myFragment.findViewById(R.id.expenses_view_pager);
        tabLayout = myFragment.findViewById(R.id.expenses_tab_layout);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                sendPosition(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // getting tab position
    private int tabPosition=0;
    public void sendPosition(int position) {
        this.tabPosition = position;
    }
    // for food expenses fragment
    public int getTabPosition(){
        return this.tabPosition;
    }

    private void setupTabIcons() {
            if (tabLayout!=null){
                if (tabLayout.getTabAt(0)!=null)
                    tabLayout.getTabAt(0).setIcon(R.drawable.fooding);
                if (tabLayout.getTabAt(1)!=null)
                    tabLayout.getTabAt(1).setIcon(R.drawable.recharges);
                if (tabLayout.getTabAt(2)!=null)
                    tabLayout.getTabAt(2).setIcon(R.drawable.shopping);
                if (tabLayout.getTabAt(3)!=null)
                    tabLayout.getTabAt(3).setIcon(R.drawable.transport);
                if (tabLayout.getTabAt(4)!=null)
                    tabLayout.getTabAt(4).setIcon(R.drawable.others);
            }
        }


    private void setUpViewPager(ViewPager viewPager) {

        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FoodExpensesFragment(),"Food");
        adapter.addFragment(new RechargeExpensesFragment(),"Recharge");
        adapter.addFragment(new ShoppingExpensesFragment(),"Shopping");
        adapter.addFragment(new TransportExpensesFragment(),"Transport");
        adapter.addFragment(new OthersExpensesFragment(),"Others");

        viewPager.setAdapter(adapter);
    }
}
