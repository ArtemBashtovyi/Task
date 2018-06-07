package com.bashtovyi.artem.task.ui.presenter;

public interface MainPresenter {

    void onDeleteFabClick();

    void onAddFabClick();

    void moveToFragment(int position);

    void changeCurrentItem(int item);

    void changeNumber(int number);
}
