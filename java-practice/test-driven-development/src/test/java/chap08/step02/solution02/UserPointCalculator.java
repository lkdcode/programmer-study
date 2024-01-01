package chap08.step02.solution02;

import java.time.LocalDate;
import java.util.NoSuchElementException;

class UserPointCalculator {
    private SubscriptionDao subscriptionDao;
    private ProductDao productDao;
    private PointRule pointRule;

    public UserPointCalculator(SubscriptionDao subscriptionDao, ProductDao productDao, PointRule pointRule) {
        this.subscriptionDao = subscriptionDao;
        this.productDao = productDao;
        this.pointRule = pointRule;
    }

    public int calculatePoint(User u) {
        final Subscription s = subscriptionDao.selectByUser(u.getId());
        if (s == null) throw new NoSuchElementException();

        final Product p = productDao.selectById(s.getProductId());
        final LocalDate now = LocalDate.now();

        return pointRule.calculate(s, p, now);
    }
}
