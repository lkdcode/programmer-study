package com.level7.examples;

class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final Point point = (Point) obj;

        return this.x == point.x || this.y == point.y;
    }

    @Override
    public int hashCode() {

        return super.hashCode();
    }
}
