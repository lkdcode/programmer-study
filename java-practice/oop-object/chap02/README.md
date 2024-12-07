```mermaid
classDiagram
    
    class Reservation
    class Screening
    class Movie
    class DiscountPolicy
    class AmountDiscountPolicy
    class PercentDiscountPolicy
    class DiscountCondition
    class SequenceCondition
    class PeriodCondition
    
    Reservation -- Screening
    Screening -- Movie
    Movie -- DiscountPolicy
    
    DiscountPolicy -- AmountDiscountPolicy
    DiscountPolicy -- PercentDiscountPolicy
    
    DiscountPolicy -- DiscountCondition
    
    DiscountCondition -- SequenceCondition
    DiscountCondition -- PeriodCondition
```