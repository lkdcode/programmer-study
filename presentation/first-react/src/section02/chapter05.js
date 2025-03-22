let arr = [1, 2, 3];

for (let i = 0; i < arr.length; i++) {
  console.log(arr[i]);
}
console.log("-------------------------");

let arr2 = [4, 5, 6, 7, 8];

for (let i = 0; i < arr2.length; i++) {
  console.log(arr2[i]);
}
console.log("-------------------------");

for (let item of arr2) {
  console.log(item);
}
console.log("-------------------------");

let person = {
  name: "hihi",
  age: 99,
  hobby: "value",
};

// let keys = Object.keys(person);
// console.log(keys);

for (key of Object.keys(person)) {
  console.log(key);
}
console.log("-------------------------");

for (value of Object.values(person)) {
  console.log(value);
}
console.log("-------------------------");

for (let key1 in person) {
  console.log(key1);
}

