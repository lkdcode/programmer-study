let arr = [99, 88, 77];

let [one = 1, two = 2, three = 3, four = 4] = arr;

console.log(one);
console.log(two);
console.log(three);
console.log(four);

let person = {
  name: "hello",
  age: 123,
  hobby: "go",
};

let { name, age, hobby } = person;
console.log(name, age, hobby);
