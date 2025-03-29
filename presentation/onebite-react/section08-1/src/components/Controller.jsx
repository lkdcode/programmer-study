import { useState } from "react";
import "../styles/Controller.css";

const Controller = ({ createTodo }) => {
  const [input, setInput] = useState("");
  const onInput = (e) => {
    setInput(e.target.value);
  };

  const onSubmit = () => {
    createTodo(input);
    setInput("");
  };

  const onEnter = (e) => {
    if (e.key === "Enter") {
      onSubmit();
    }
  };

  return (
    <div className="Controller">
      <input
        value={input}
        onKeyDown={onEnter}
        onChange={onInput}
        placeholder="새로운 Todo..."
      />
      <button onClick={onSubmit}>추가</button>
    </div>
  );
};

export default Controller;
