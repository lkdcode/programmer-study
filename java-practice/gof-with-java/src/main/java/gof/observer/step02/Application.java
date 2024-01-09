package gof.observer.step02;

class Application {

    public static void main(String[] args) {
        Calculator calculator = new IntegerCalculator(152, 687);

        calculator.addFormula((a, b) -> {
            int result = a + b;
            System.out.println("a + b = " + result);
            return result;
        });

        calculator.addFormula((a, b) -> {
            int result = a * b;
            System.out.println("a * b = " + result);
            return result;
        });

        calculator.addFormula((a, b) -> {
            int result = a - b;
            System.out.println("a - b = " + result);
            return result;
        });

        calculator.addFormula((a, b) -> {
            int result = a / b;
            System.out.println("a / b = " + result);
            return result;
        });

        calculator.execute();
    }

}
