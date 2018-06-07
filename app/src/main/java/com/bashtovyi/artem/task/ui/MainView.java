package com.bashtovyi.artem.task.ui;

/**
 *
 */


public interface MainView {

    void showNumber(String number);

    void showFragment(int position);

    void deleteFragment(int position);

    void addFragment();
}
