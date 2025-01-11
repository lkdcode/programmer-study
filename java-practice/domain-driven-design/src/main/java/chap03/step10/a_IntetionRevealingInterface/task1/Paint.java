package chap03.step10.a_IntetionRevealingInterface.task1;

class Paint {
    double v;
    int r;
    int y;
    int b;

    public Paint(double v, int r, int y, int b) {
        this.v = v;
        this.r = r;
        this.y = y;
        this.b = b;
    }

    void paint(Paint paint) {
        v = v + paint.v;
        r = r + paint.r;
        y = y + paint.y;
        b = b + paint.b;
    }
}