package org.game.domains.moku.domain.specification;

import org.game.domains.moku.domain.entity.MokuPlayerCount;

public final class MokuPlayerCountSpecification {

    public void spec(final MokuPlayerCount mokuPlayerCount) {
        if (mokuPlayerCount.getValue() <= 1) {
            throw new IllegalArgumentException("플레이어는 최소 2명 이상이어야 합니다.");
        }
    }
}