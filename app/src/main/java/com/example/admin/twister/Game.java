package com.example.admin.twister;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private int usersCount;
    private GameActivity gameActivity;
    private User current, winner;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> failed = new ArrayList<>();
    private Random generator = new Random();

    public Game(int usersCount, GameActivity activity) {
        this.usersCount = usersCount;
        this.gameActivity = activity;
        for (int i = 1; i <= usersCount; i++)
            users.add(new User(i));
    }

    public void nextAction() {
        getNewCurrent();
        current.addMove();
        // TODO animation for @current
        gameActivity.doAnimation();
    }

    public boolean failAction() {
        current.delMove();
        current.setActive(false);
        failed.add(current);
        usersCount--;

        // Если все еще есть кому играть
        if (getUsersCount() >= 2) {
            return true;
        }
        else {
            getNewCurrent();
            //current.addMove();
            winner = current;
            return false;
        }
    }

    public void getNewCurrent() {
        do {
            if (current == null || users.indexOf(current) == users.size() - 1)
                current = users.get(0);
            else {
                int i = users.indexOf(current);
                current = users.get(i + 1);
            }
        } while (!current.isActive());
    }

    public int getRandom() {
        return generator.nextInt(4);
    }

    public int getUsersCount() {
        return usersCount;
    }

    public ArrayList<User> getLosers() {
        return failed;
    }

    public User getCurrent() {
        return current;
    }

    public User getWinner() { return winner; }
}
