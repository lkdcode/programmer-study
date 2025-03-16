console.log("chapter06======================");

function funcA() {
  console.log("hihihi");
}

const my = funcA;
my();

// 호이스팅 불가
const hihi = function funcB() {
  console.log("FuncB");
};

hihi();

const hhhh = () => console.log("hihihih");

console.log("==================");
hhhh();

const ii = (a, b) => a + b;
