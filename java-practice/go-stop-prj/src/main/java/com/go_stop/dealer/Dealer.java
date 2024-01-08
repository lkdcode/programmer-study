package com.go_stop.dealer;

import com.go_stop.card_list.FloorCardList;
import com.go_stop.card_list.UserCardList;
import com.go_stop.deck.GoStopHwaTooDeck;

public class Dealer {

    private GoStopHwaTooDeck goStopHwaTooDeck;
    private FloorCardList floorCardList;
    private UserCardList firstUserHwaTooList;
    private UserCardList secondUserHwaTooList;

    public Dealer() {
        this.goStopHwaTooDeck = new GoStopHwaTooDeck();
        this.floorCardList = new FloorCardList();
        this.firstUserHwaTooList = new UserCardList();
        this.secondUserHwaTooList = new UserCardList();
        resetGoStopHwaTooDeck();
    }

    public void resetGoStopHwaTooDeck() {
        dealHwaToo();
    }

    public FloorCardList getFloorCardList() {
        return this.floorCardList;
    }

    public UserCardList getFirstUserHwaTooList() {
        return this.firstUserHwaTooList;
    }

    public UserCardList getSecondUserHwaTooList() {
        return this.secondUserHwaTooList;
    }

    private void dealHwaToo() {
        for (int i = 0; i < 2; i++) {
            makeFloorCardList();
            makeFirstUserHwaTooList();
            makeSecondUserHwaTooList();
        }
    }

    private void makeFirstUserHwaTooList() {
        for (int j = 0; j < 5; j++) {
            if (this.goStopHwaTooDeck.hasNextHwaToo()) {
                this.firstUserHwaTooList.addHwaToo(this.goStopHwaTooDeck.getNextHwaToo());
            }
        }
    }

    private void makeSecondUserHwaTooList() {
        for (int j = 0; j < 5; j++) {
            if (this.goStopHwaTooDeck.hasNextHwaToo()) {
                this.secondUserHwaTooList.addHwaToo(this.goStopHwaTooDeck.getNextHwaToo());
            }
        }
    }

    private void makeFloorCardList() {
        for (int j = 0; j < 4; j++) {
            if (this.goStopHwaTooDeck.hasNextHwaToo()) {
                this.floorCardList.addHwaToo(this.goStopHwaTooDeck.getNextHwaToo());
            }
        }
    }

}
