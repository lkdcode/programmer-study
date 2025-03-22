function printName(person) {
  const name = person && person.name;
  console.log(name || "persone의 값이 없음");
}

printName();
printName({ name: "hello" });
