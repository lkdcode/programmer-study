package com.hexagonal.chap01.framework.adapter.input;

import com.hexagonal.chap01.application.port.input.RouterViewInputPort;
import com.hexagonal.chap01.application.usecase.RouterViewUseCase;

public class RouterViewCLIAdapter {
    RouterViewUseCase routerViewUseCase;

    public RouterViewCLIAdapter() {
        setAdapters();
    }

    private void setAdapters() {
        this.routerViewUseCase
                = new RouterViewInputPort(RouterViewFileAdapter.getInstance());
    }
}
