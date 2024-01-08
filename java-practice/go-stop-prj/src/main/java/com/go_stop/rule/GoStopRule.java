package com.go_stop.rule;

import com.go_stop.hwatoo.HwaToo;

import java.util.List;

import static com.go_stop.hwatoo.CombinationType.*;
import static com.go_stop.hwatoo.HwaToo.비광;
import static com.go_stop.hwatoo.HwaToo.비띠;

public class GoStopRule {

    private int point;

    public GoStopRule() {
        this.point = 0;
    }

    public GoStopRule calculateLight(List<HwaToo> list) {
        int lightCount = 0;
        boolean isDecemberLight = false;

        for (HwaToo hwaToo : list) {
            if (hwaToo.getCombinationType() == 광) lightCount++;
            if (hwaToo == 비광) isDecemberLight = true;
        }

        if (lightCount == 3 && isDecemberLight) this.point += 2;
        if (lightCount == 3 && !isDecemberLight) this.point += 3;
        if (lightCount == 4) this.point += 4;
        if (lightCount == 5) this.point += 15;

        return this;
    }

    public GoStopRule calculateNormalCards(final List<HwaToo> list) {
        int normalCardPoint = 0;

        for (HwaToo hwaToo : list) {
            if (hwaToo.getCombinationType() == 피) normalCardPoint += 1;
            if (hwaToo.getCombinationType() == 쌍피) normalCardPoint += 2;
        }

        this.point += Math.max((normalCardPoint - 9), 0);

        return this;
    }

    public GoStopRule calculateLineCards(final List<HwaToo> list) {
        int redLineCount = 0;
        int blueLineCount = 0;
        int normalLineCount = 0;

        boolean isDecemberNormalLine = false;

        for (HwaToo hwaToo : list) {
            if (hwaToo == 비띠) {
                isDecemberNormalLine = true;
                continue;
            }
            if (hwaToo.getCombinationType() == 홍단) redLineCount++;
            if (hwaToo.getCombinationType() == 청단) blueLineCount++;
            if (hwaToo.getCombinationType() == 초단) normalLineCount++;
        }

        int totalCount = redLineCount + blueLineCount + normalLineCount;

        if (redLineCount == 3) this.point += 3;
        if (blueLineCount == 3) this.point += 3;
        if (normalLineCount == 3) this.point += 3;

        if (isDecemberNormalLine) totalCount++;

        if (totalCount >= 5) {
            this.point += totalCount - 4;
        }

        return this;
    }

    public GoStopRule calculateAnimalCard(final List<HwaToo> list) {
        int point = 0;
        int birdCount = 0;
        int animalCount = 0;

        for (HwaToo hwaToo : list) {
            if (hwaToo.getCombinationType() == 끗_열) {
                animalCount++;
            }

            if (hwaToo.getCombinationType() == 고도리) {
                birdCount++;
                animalCount++;
            }
        }

        if (birdCount == 3) this.point += 5;
        if (animalCount >= 5) this.point += animalCount - 4;

        return this;
    }

    public int process() {
        return this.point;
    }

}
