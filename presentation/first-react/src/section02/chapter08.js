let arr1 = [
  { name: "hihi1", hobby: "gogo1" },
  { name: "hihi2", hobby: "hhhh" },
  { name: "hihi3", hobby: "gogo1" },
];

const result = arr1.filter((item) => (item.hobby = "gogo1"));
console.log(result);

console.log("--------------------");
const result2 = arr1.map((item, idex, arr) => {
  console.log(item);
  return name + name;
});

console.log(result2);

const result3 = arr1.map((item) => item.hobby);
console.log(result3);

let arr3 = [1, 2, 3, 4, 5, 1, 23, 13, 4, 12, 1, 4, 523];
arr3.sort((a, b) => {
  if (a > b) {
    return -1;
  } else if (a < b) {
    return 1;
  }

  return 0;
});

console.log(arr3);

console.log("--------------------");

const hihi = arr3.toSorted();
console.log(hihi);

console.log("--------------------");

const arr6 = ["hello", "world", "react"];

const result6 = arr6.join(" ");
console.log(result6);
