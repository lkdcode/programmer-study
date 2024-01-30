package gof.state.step1;

class Application {
    public static void main(String[] args) {
        final Context context = new ContextImpl();

        context.setClock(9);
        context.doUse();
        context.doAlarm();
        context.doPhone();

        System.out.println("----------------");

        context.setClock(21);
        context.doUse();
        context.doAlarm();
        context.doPhone();
    }
}
