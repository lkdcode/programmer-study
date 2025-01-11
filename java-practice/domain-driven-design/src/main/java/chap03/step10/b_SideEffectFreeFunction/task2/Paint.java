package chap03.step10.b_SideEffectFreeFunction.task2;

class Paint {
    double volume;
    PigmentColor pigmentColor;

    public Paint(double volume, PigmentColor pigmentColor) {
        this.volume = volume;
        this.pigmentColor = pigmentColor;
    }

    void mixIn(Paint other) {
        volume = volume + other.volume;
        var ratio = other.volume / volume;
        pigmentColor = pigmentColor.mixedWith(other.pigmentColor, ratio);
    }
}