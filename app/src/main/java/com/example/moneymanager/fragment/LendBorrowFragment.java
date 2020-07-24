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
import com.example.moneymanager.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendBorrowFragment extends Fragment implements View.OnClickListener {

    private View myFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
//    private ImageButton prevImageButton;

    public LendBorrowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_lend_borrow, container, false);

        viewPager = myFragment.findViewById(R.id.view_pager);
        tabLayout = myFragment.findViewById(R.id.tab_layout);
        floatingActionButton = myFragment.findViewById(R.id.float_button);
        floatingActionButton.setOnClickListener(this);
        return myFragment;
    }

    // call onActivity created method
    @Override
    public void onActivityCreated(@Nullable Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcons();
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

    private void setUpIcons() {
    if(tabLayout!=null){
        if(tabLayout.getTabAt(0)!= null){
            tabLayout.getTabAt(0).setIcon(R.drawable.lend_money);
        }
        if(tabLayout.getTabAt(1)!= null){
            tabLayout.getTabAt(1).setIcon(R.drawable.borrow_money);
        }
    }
    }

    // getting tab position
    private int tabPosition;
    private void sendPosition(int position) {
        tabPosition = position;
    }

    private void setUpViewPager(ViewPager viewPager){

        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new LendFragment(),"Lend");
        adapter.addFragment(new BorrowFragment(),"Borrow");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        if(tabPosition == 0){
            LendBorrowDialogFragment dialogFragment = new LendBorrowDialogFragment();
            Bundle fBundle = new Bundle();
            fBundle.putString("lend", Constants.addLend);
            dialogFragment.setArguments(fBundle);
            dialogFragment.show(getChildFragmentManager(),"Exchange");
        }
        if(tabPosition ==1){
            LendBorrowDialogFragment dialogFragment = new LendBorrowDialogFragment();
            Bundle fBundle = new Bundle();
            fBundle.putString("lend", Constants.addBorrow);
            dialogFragment.setArguments(fBundle);
            dialogFragment.show(getChildFragmentManager(),"Exchange");
        }

    }
}
