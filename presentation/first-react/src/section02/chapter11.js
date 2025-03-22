const promise = new Promise((resolve, reject) => {
  // 비동기 작업
  // executor

  setTimeout(() => {
    const num = null;

    if (typeof num === "number") {
      resolve("20");
    } else {
      reject("...num?");
    }
  }, 2000);
});

promise
  .then((value) => {
    console.log(value);
  })
  .catch((value) => {
    console.log(value);
  });
