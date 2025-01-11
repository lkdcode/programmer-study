package chap03.step10.b_SideEffectFreeFunction.task1;

class Paint {
    double v;
    PigmentColor pigmentColor;

    public Paint(double v, PigmentColor pigmentColor) {
        this.v = v;
        this.pigmentColor = pigmentColor;
    }

    void mixIn(Paint paint) {
        v = v + paint.v;
        var mixRed = pigmentColor.red + paint.pigmentColor.red;
        var mixYellow = pigmentColor.yellow + paint.pigmentColor.yellow;
        var mixBlue = pigmentColor.blue + paint.pigmentColor.blue;
        pigmentColor = new PigmentColor(mixRed, mixYellow, mixBlue);
    }
}