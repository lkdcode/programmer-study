package com.go_stop.hwatoo;

import static com.go_stop.hwatoo.CombinationType.*;
import static com.go_stop.hwatoo.Type.*;

public enum HwaToo {
    송학광(January, 광), 송학띠(January, 홍단), 송학피1(January, 피), 송학피2(January, 피),
    매화열(February, 고도리), 매화띠(February, 홍단), 매화피1(February, 피), 매화피2(February, 피),
    벚꽃광(March, 광), 벚꽃띠(March, 홍단), 벚꽃피1(March, 피), 벚꽃피2(March, 피),
    등꽃열(April, 고도리), 등꽃띠(April, 초단), 등꽃피1(April, 피), 등꽃피2(April, 피),
    난초열(May, 끗_열), 난초띠(May, 초단), 난초피1(May, 피), 난초피2(May, 피),
    모란열(June, 끗_열), 모란띠(June, 청단), 모란피1(June, 피), 모란피2(June, 피),
    흑싸리열(July, 끗_열), 흑싸리띠(July, 초단), 흑싸리피1(July, 피), 흑싸리피2(July, 피),
    공산광(August, 광), 공산열(August, 고도리), 공산피1(August, 피), 공산피2(August, 피),
    국화열(September, 끗_열_쌍피), 국화띠(September, 청단), 국화피1(September, 피), 국화피2(September, 피),
    단풍열(October, 끗_열), 단풍띠(October, 청단), 단풍피1(October, 피), 단풍피2(October, 피),
    오동광(November, 광), 오동피1(November, 피), 오동피2(November, 피), 오동쌍피(November, 쌍피),
    비광(December, 광), 비열(December, 끗_열), 비띠(December, 초단), 비쌍피(December, 쌍피),
    조커1(Joker, 쌍피), 조커2(Joker, 쌍피),
    ;
    private final Type type;

    private final CombinationType combinationType;

    HwaToo(Type type, CombinationType combinationType) {
        this.type = type;
        this.combinationType = combinationType;
    }

    public Type getType() {
        return type;
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

}
