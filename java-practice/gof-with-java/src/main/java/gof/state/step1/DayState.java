package gof.state.step1;

import java.net.DatagramSocket;

class DayState implements State {
    private static DayState singleton = new DayState();

    private DayState() {
    }

    public static State getInstance() {
        return singleton;
    }

    @Override
    public void doClock(Context context, int hour) {
        if (hour < 9 || 17 <= hour) {
            context.changeState(NightState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        System.out.println("금고 사용 (주간)");
    }

    @Override
    public void doAlarm(Context context) {
        System.out.println("비상벨 (주간)");
    }

    @Override
    public void doPhone(Context context) {
        System.out.println("일반 통화 (주간)");
    }
}
