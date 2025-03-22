let arr4 = [1, 2, 3];

const findedIndex = arr4.findIndex((item) => {
  if (item % 9 == 0) return true;
});

console.log(findedIndex);
if (!findedIndex) console.log("???");
