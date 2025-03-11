package org.game.domains.moku.domain.specification;

import org.game.domains.moku.domain.entity.MokuWinCount;

public final class MokuSizeSpecification {

    public void spec(final MokuWinCount target) {
        if (target.getValue() <= 2) {
            throw new IllegalArgumentException("최소 3목 이상 이어야 합니다!");
        }
    }
}