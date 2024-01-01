package chap08.step01.problem3;

import java.time.LocalDate;
import java.util.NoSuchElementException;

class UserPointCalculator {
    private SubscriptionDao subscriptionDao;
    private ProductDao productDao;

    public UserPointCalculator(SubscriptionDao subscriptionDao, ProductDao productDao) {
        this.subscriptionDao = subscriptionDao;
        this.productDao = productDao;
    }

    public int calculatePoint(User u) {
        final Subscription s = subscriptionDao.selectByUser(u.getId());
        if (s == null) throw new NoSuchElementException();
        Product p = productDao.selectById(s.getProductId());
        final LocalDate now = LocalDate.now();
        int point = 0;
        if (s.isFinished(now)) {
            point += p.getDefaultPoint();
        } else {
            point += p.getDefaultPoint() + 10;
        }

        if (s.getGrade() == Grade.GOLD) {
            point += 100;
        }

        return point;
    }
}
