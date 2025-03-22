// function add(a, b, callback) {
//   setTimeout(() => {
//     const sum = a + b;
//     console.log(sum);
//     callback(sum);
//   }, 3000);
// }

// add(1, 2, (value) => {
//   console.log(`callback result: ${value}`);
// });

//--------------------------------------------

function orderFood(callback) {
  setTimeout(() => {
    const food = "hello";
    callback(food);
  }, 3000);
}

orderFood((food) => {
  console.log(food);
});

function cooldwonFood(food, callback) {
  setTimeout(() => {
    const ho = "hihi";
    callback(cooldwonFood);
  }, 2000);
}

orderFood((f) => {
  console.log(f);
  cooldwonFood(f, (a) => {
    console.log(a);
  });
});

function freezeFood(food) {
  setTimeout(() => {
    const freezedFood = `hh${food}`;;

    
  });
}
