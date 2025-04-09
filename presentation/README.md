# React

## React Hooks

- 함수 컴포넌트, 커스텀 훅 내부에서만 호출 가능
- 조건문, 반복분에서 호출 불가
- 커스텀 훅 생성 가능

```react
const [input, setInput] = useState("");
const onChange = (e) => {setInput(e.target.value)}

====>>>>>>>>

function useCustomInput() {
  const [input, setInput] = useState("");
  const onChange = (e) => {setInput(e.target.value)}

  return [input, onChange]
}
```

## useRef

```react
const ref = useRef("Hello");
console.log(ref.current)
```

- 컴포넌트 내부의 변수로 활용 가능.
- 리렌더링 X
- 컴포넌트 외부에 변수를 선언하면 공유되는 문제 발생

## useEffect

```react
// value 가 변경되면 콜백 함수 실행
useEffect(() => { console.log(`${value}`) }, [value])

// 마운트
// 마운트될 때 실행
useEffect(() => { console.log(`${value}`) }, [])

// 업데이트(리렌더)
// 마운트 될 때 실행, 리렌더링 때마다 실행
useEffect(() => { console.log(`${value}`) })

// 언마운트
useEffect(() => { return () => { console.log(`${value}`) }}, [])
```

- 컴포넌트의 사이드 이펙트 제어
- 컴포넌트 내부의 값 변경, 마운트, 업데이트(리렌더), 언마운트 발생시

## useReducer

```react
function reducer(state, action) {
  switch(action.type) {
    case "INCREASE": return state + action.data;
    case "DECREASE": return state - action.data;
    default: return state
  }
}

const Exam = () => {
  const [state, dispatch] = useReducer(reducer, 0);

  const onClickPlus = () => {
    // -> action 객체
    dispatch({
      type: "INCREASE",
      data: 1,
    })
  }

  const onClickMinus = () => {
    // -> action 객체
    dispatch({
      type: "DECREASE",
      data: 1,
    })
  }
}
```

- 컴포넌트 내부에 새로운 State 생성
- 모든 useState -> useReducer 로 대체 가능
- 상태 관리 코드를 컴포넌트 외부로 분리할 수 있음
- 컴포넌트 내부를 비교적 간결하게 작성 가능

## useMemo

```react
const memo = useMemo(() => {
  // 메모이제이션 하고 싶은 연산
}, [])
```

## React.memo

```react
const MemoizedComponent = memo(Component)
const MemoizedComponent = memo(Component, (prevProps, nextProps) => {
  // 반환값에 따라 리렌더링 여부 결정.
  // True = 리렌더링, False = X

  return
    prevProps.value1 === nextProps.value1
    && prevProps.value2 === nextProps.value2
    && prevProps.value3 === nextProps.value3
    && prevProps.value4 === nextProps.value4;
})
```

- 부모 컴포넌트가 리렌더링되더라도 Props 를 기반으로 리렌더링
- 얕은 비교. props 로 핸들링
- 고차 컴포넌트(HOC)

## useCallback

```react
const func = useCallback(() => {}, [])
```

React.memo 보다 간결하게

- 의존성 배열을 기준으로 생성

## Context
