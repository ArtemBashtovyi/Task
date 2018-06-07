package com.bashtovyi.artem.task.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bashtovyi.artem.task.R;
import com.bashtovyi.artem.task.ui.adapter.SectionsPagerAdapter;
import com.bashtovyi.artem.task.ui.fragment.NotificationFragment;
import com.bashtovyi.artem.task.ui.presenter.MainPresenter;
import com.bashtovyi.artem.task.ui.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainView {
    private final static  String TAG = "MainActivity";

    private MainPresenter presenter;
    // list is here, presenter don't need to know about Android Framework items
    private ArrayList<NotificationFragment> fragments = new ArrayList<>();
    //private int currentItem = 0;

    private SectionsPagerAdapter adapter;
    private ViewPager viewPager;
    private TextView textView;
    private FloatingActionButton addFab;
    private FloatingActionButton deleteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this);

        addFab = findViewById(R.id.add_fab);
        deleteFab = findViewById(R.id.delete_fab);
        viewPager = findViewById(R.id.container);
        textView = findViewById(R.id.fragment_number_text_view);

        // add First Fragment
        fragments.add(NotificationFragment
                .newInstance(fragments.size() + 1,
                        // FIXME : use any of hash function, random is terrible solution
                        new Random().nextInt(500) + 1));

        // Set up the ViewPager with the sections adapter.
        adapter = new SectionsPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        initListeners();
        // hide delete fab by start
        hideDeleteFab();
    }

    private void initListeners() {
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddFabClick();
            }
        });

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDeleteFabClick();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    hideDeleteFab();
                } else {
                    deleteFab.show();
                }

                presenter.changeCurrentItem(position);
                presenter.changeNumber(fragments.get(position).getNumber());

                //textView.setText(String.valueOf(fragments.get(position).getNumber()));
                // show current position of Fragment
                //currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // call back for receive intent from the notification
    @Override
    protected void onNewIntent(Intent intent) {
        int number = intent.getIntExtra(StringConstant.KEY_NOTIFICATION,0);
        // get current position of fragment
        int position = getPositionByNumber(number);
        presenter.moveToFragment(position);
    }

    private void hideDeleteFab(){
        deleteFab.hide();
    }

    @Override
    public void showNumber(String number) {
        textView.setText(number);
    }

    @Override
    public void showFragment(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void deleteFragment(int position) {
       // int deletedPosition = adapter.delete(position);
        NotificationFragment fragment = fragments.get(position);
        // before delete destroy all notifications
        fragment.destroyAllNotifications();
        fragments.remove(fragment);
        adapter.notifyDataSetChanged();

        // move to previous fragment
        presenter.moveToFragment(position - 1);
    }

    @Override
    public void addFragment() {
        int lastNumber = fragments.get(fragments.size()-1).getNumber();
        lastNumber++;
        // create fragment with number bigger than previous by one
        fragments.add(NotificationFragment.newInstance(lastNumber,
                new Random().nextInt(50) + 1));
        adapter.notifyDataSetChanged();
        presenter.moveToFragment(fragments.size());
    }


    private int getPositionByNumber(int id) {
        int counter = 0;
        for (NotificationFragment fragment : fragments) {
            if (fragment.getNumber() == id) return counter;
            counter++;
        }
        return -1;
    }
}
