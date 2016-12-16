package com.example.admin.twister;

import java.io.Serializable;

class User implements Serializable{
    private int id;
    private int moves;
    private boolean isActive;

    public User(int id) {
        this.id = id;
        isActive = true;
    }

    public int getId() {
        return id;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addMove() {
        moves++;
    }

    public void delMove() { moves--; }
}
