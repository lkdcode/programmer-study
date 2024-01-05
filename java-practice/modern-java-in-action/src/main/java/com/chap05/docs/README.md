# Chap 05

| 연산        | 형식                 | 반환 형식             | 사용된 함수형 인터페이스 형식                   | 함수 디스크립터             |
|-----------|--------------------|-------------------|------------------------------------|----------------------|
| filter    | 중간 연산              | Stream&lt;T&gt;   | Predicate&lt;T&gt;                 | T -> boolean         |
| distinct  | 중간 연산 (상태 있는 언바운드) | Stream&lt;T&gt;   ||                                    |
| takeWhile | 중간 연산              | Stream&lt;T&gt;   | Predicate&lt;T&gt;                 | T -> boolean         |
| dropWhile | 중간 연산              | Stream&lt;T&gt;   | Predicate&lt;T&gt;                 | T -> boolean         |
| skip      | 중간 연산 (상태 있는 바운드)  | Stream&lt;T&gt;   | long                               |                      |
| limit     | 중간 연산 (상태 있는 바운드)  | Stream&lt;T&gt;   | long                               |                      |
| map       | 중간 연산              | Stream&lt;R&gt;   | Function&lt;T, R&gt;               | T -> R               |
| flatMap   | 중간 연산              | Stream&lt;R&gt;   | Function&lt;T, Stream&lt;R&gt;&gt; | T -> Stream&lt;R&gt; |
| sorted    | 중간 연산 (상태 있는 언바운드) | Stream&lt;T&gt;   | Comparator&lt;T&gt;                | (T, T) -> int        |
| anyMatch  | 최종 연산              | boolean           | Predicate&lt;T&gt;                 | T -> boolean         |
| noneMatch | 최종 연산              | boolean           | Predicate&lt;T&gt;                 | T -> boolean         |
| allMatch  | 최종 연산              | boolean           | Predicate&lt;T&gt;                 | T -> boolean         |
| findAny   | 최종 연산              | Optional&lt;T&gt; |                                    |                      |
| findFirst | 최종 연산              | Optional&lt;T&gt; |                                    |                      |
| forEach   | 최종 연산              | void              | Consumer&lt;T&gt;                  | T -> void            |
| collect   | 최종 연산              | R                 | Collector&lt;T, A, R&gt;           |                      |
| reduce    | 최종 연산 (상태 있는 바운드)  | Optional&lt;T&gt; | BinaryOperator&lt;T&gt;            | (T, T) -> T          |
| count     | 최종 연산              | long              ||                                    |

