package gof.observer.step02;

@FunctionalInterface
interface Formula {
    int execute(int a, int b);
}