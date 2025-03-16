console.log("===================777777777777777777777777===================");

function main(value) {
  console.log(1);
  console.log(2);
  value();
}

function print() {
  console.log("kkkk");
}

main(() => console.log("kkkk"));

console.log("===================777777777777777777777777===================");

function repaet(count) {
  for (let index = 1; index <= count; index++) {
    console.log(index);
  }
}

repaet(5);

function repaetDouble(count) {
  for (let index = 1; index <= count; index++) {
    console.log(index * 2);
  }
}

repaetDouble(5);

function repeat(count, callback) {
  for (let index = 1; index <= count; index++) {
    callback(index);
  }
}
console.log("**********************33**********");

repeat(3, (i) => console.log(i));
console.log("******************33**************");
repeat(3, (i) => console.log(i * 2));
console.log("**********************33**********");
repeat(3, (i) => console.log(i * 3));
