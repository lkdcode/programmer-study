import { useState } from "react";
import "../style/Editor.css";

const Editor = ({ addTodo }) => {
  const handleSubmit = () => {
    if (inputTodo === "") {
      alert("Todo를 입력해주세요.");
      return;
    }

    const now = new Date().toDateString();
    const item = {
      title: inputTodo,
      date: now,
    };

    addTodo(item);
    setInputTodo("");
  };

  const [inputTodo, setInputTodo] = useState("");

  return (
    <div className="Editor">
      <input
        value={inputTodo}
        placeholder="새로운 Todo..."
        onChange={(e) => {
          setInputTodo(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            handleSubmit();
          }
        }}
      />
      <button
        onClick={() => {
          handleSubmit();
        }}
      >
        추가
      </button>
    </div>
  );
};

export default Editor;
