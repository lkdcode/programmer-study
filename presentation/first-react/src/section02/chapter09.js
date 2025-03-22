// 1. Date 객체
console.log(new Date());
console.log(new Date("2024-01-01"));

// 2. 타임 스탬프
const ts1 = new Date().getTime();
console.log(ts1);

const newTime1 = new Date(ts1);
console.log(newTime1);

// 3. 시간 요소들을 추출
let year = new Date().getFullYear();
let month = new Date().getMonth() + 1;
let day = new Date().getDate();

console.log(year);
console.log(month);
console.log(day);
