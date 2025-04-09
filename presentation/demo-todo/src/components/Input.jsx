import { useState } from "react";
import "../styles/Input.css";

const Input = ({ onCreate }) => {
  const [input, setInput] = useState("");

  const onInput = (e) => {
    setInput(e.target.value);
  };

  const onEnterKeyDown = (e) => {
    if (e.key === "Enter" && e.nativeEvent.isComposing === false) {
      onCreateButton();
    }
  };

  const onCreateButton = () => {
    onCreate(input);
    setInput("");
  };

  return (
    <div className="input_section">
      <input
        onChange={onInput}
        onKeyDown={onEnterKeyDown}
        value={input}
        className="input_box"
        placeholder="새로운 Todo..."
      />
      <button onClick={onCreateButton} className="add_button">
        추가
      </button>
    </div>
  );
};

export default Input;
