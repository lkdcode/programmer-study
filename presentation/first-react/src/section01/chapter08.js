let obj1 = {};

let person = {
  name: "hihi",
  age: 99,
  hobby: "byebye",
};

console.log(person);

person.jar = "JDK";
console.log(person);

person.jar = "FAST";
console.log(person);

delete person.jar;
console.log(person);

let result1 = "name" in person;
console.log(result1);

person.sayHi = () => console.log("hihi");

// console.log(person);
person.sayHi();
person.sayHi = () => console.log("ㅍㅍㅍㅍㅍㅍㅍㅍㅍㅍ");
person.sayHi();
