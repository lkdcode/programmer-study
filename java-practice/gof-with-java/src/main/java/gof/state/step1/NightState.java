package gof.state.step1;

public class NightState implements State {
    private static NightState singleton = new NightState();

    private NightState() {
    }

    public static State getInstance() {
        return singleton;
    }

    @Override
    public void doClock(Context context, int hour) {
        if (9 <= hour && hour < 17) {
            context.changeState(DayState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        System.out.println("비상: 야간 금고 사용!");
    }

    @Override
    public void doAlarm(Context context) {
        System.out.println("비상벨 (야간)");
    }

    @Override
    public void doPhone(Context context) {
        System.out.println("야간 통화 녹음");
    }
}
