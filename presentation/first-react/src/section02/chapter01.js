let f1 = undefined;
let f2 = null;
let f3 = 0;
let f4 = -0;
let f5 = NaN;
let f6 = "";
let f7 = 0n;
// let f8 = None;

if (!f7) {
  console.log(f7);
}

function printName(person) {
  if (!person) {
    console.log("hihi");
  }

  console.log(person.name);
}

let person = { name: "hihi" };
printName(person);
