# chap03 - 첫 번째 REST API

## 모놀리식

- 도메인과 도메인의 경계가 모호할 때
- 제공된 기능이 긴밀하게 결합됐으며, 모듈 간 상호작용에서 유연함보다 성능이 절대적으로 더 중요할 때
- 관련된 모든 기능의 애플리케이션 확장 요구사항이 알려져 있고 일관적일 때
- 기능이 변동성이 없을 때, 즉 변화가 느리거나 변화 범위가 제한적일 때와 둘 다일 때

## IETF 문서의 'HTTP 상태 코드'

- GET : 특정 '상태 코드' 지정 x
- POST, DELETE : '상태 코드' 사용 권장
- PUT : '상태 코드' 필수

## HTTP CLI

[HTTPie](https://httpie.io/docs/cli/installation)