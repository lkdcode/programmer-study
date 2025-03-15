import React, { useState, useEffect } from "react";



function Counter(props) {
  const [count, setCount] = useState(0);

  useEffect(() => {
    document.title = `You clikced ${count} times`;
  })

  return (
    <div>
      <p>총 {count}번 클릭함</p>
      <button onClick={() => setCount(count + 1)}>
        click!
      </button>
    </div>
  );
}

export default Counter;