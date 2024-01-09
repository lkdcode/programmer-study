package gof.observer.step01;

class GraphObserver implements Observer {
    @Override
    public void update(NumberGenerator numberGenerator) {
        System.out.print("GraphObserver: ");
        final int count = numberGenerator.getNumber();
        for (int i = 0; i < count; i++) {
            System.out.print("*");
        }

        System.out.println();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }
}
