// async
// 함수를 Promise

async function getData() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({
        name: "hh",
        age: 44,
      });
    }, 1000);
  });
  // return {
  //   name: "hh",
  //   age: 44,
  // };
}

console.log(getData());

// await
// async 함수 내부에서만 사용이 가능한 키워드
// 비동기 함수가 다 처리되기를 기다리는 역할

async function printData() {
  const data = await getData();
  console.log(data);
}

printData();
