package chap03.step10.b_SideEffectFreeFunction.task2;

class PigmentColor {
    int red;
    int yellow;
    int blue;

    public PigmentColor(int red, int yellow, int blue) {
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
    }

    PigmentColor mixedWith(PigmentColor other, double ratio) {
        int mixRed = (int) Math.round(red * (1 - ratio) + other.red * ratio);
        int mixYellow = (int) Math.round(yellow * (1 - ratio) + other.yellow * ratio);
        int mixBlue = (int) Math.round(blue * (1 - ratio) + other.blue * ratio);

        return new PigmentColor(mixRed, mixYellow, mixBlue);
    }
}