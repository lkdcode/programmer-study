package gof.state.step1;

public class ContextImpl implements Context {
    private State state = DayState.getInstance();

    @Override
    public void setClock(int hour) {
        state.doClock(this, hour);
    }

    @Override
    public void changeState(State state) {
        System.out.println(
                this.state.getClass().getSimpleName()
                        + " 에서 " +
                        state.getClass().getSimpleName()
                        + " 으로 상태가 변경되었습니다."
        );
        this.state = state;
    }

    @Override
    public void callSecurityCenter(String msg) {
        System.out.println("call: " + msg);
    }

    @Override
    public void recordLog(String msg) {
        System.out.println("record: " + msg);
    }

    @Override
    public void doUse() {
        state.doUse(this);
    }

    @Override
    public void doAlarm() {
        state.doAlarm(this);
    }

    @Override
    public void doPhone() {
        state.doPhone(this);
    }
}
