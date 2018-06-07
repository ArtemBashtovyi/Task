package com.bashtovyi.artem.task.ui.presenter;


import com.bashtovyi.artem.task.ui.MainView;

// TODO : add onDestroy clearing view reference for handle rotate
public class MainPresenterImpl implements MainPresenter {
    private Integer currentItem = 0;
    private MainView view;

    public MainPresenterImpl(MainView mainView) {
        this.view = mainView;
    }


    @Override
    public void onDeleteFabClick() {
        if (currentItem != 0) {
            view.deleteFragment(currentItem);
            moveToFragment(currentItem);
        }
    }

    @Override
    public void onAddFabClick() {
        view.addFragment();
    }

    @Override
    public void moveToFragment(int position) {
        if (position >= 0) {
            view.showFragment(position);
        }
    }

    @Override
    public void changeCurrentItem(int item) {
        currentItem = item;
    }

    @Override
    public void changeNumber(int number) {
        view.showNumber(String.valueOf(number));
    }
}
