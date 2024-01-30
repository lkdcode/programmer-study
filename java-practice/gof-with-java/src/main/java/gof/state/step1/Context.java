package gof.state.step1;

interface Context {
    void setClock(int hour);

    void changeState(State state);

    void callSecurityCenter(String msg);

    void recordLog(String msg);

    void doUse();

    void doAlarm();

    void doPhone();
}
