import { useState } from "react";
import "../styles/TodoList.css";
import TodoItem from "./TodoItem";

const TodoList = ({ todoList, onDelete, onUpdate }) => {
  const [inputSearch, setInputSearch] = useState("");

  const onInputSearch = (e) => {
    setInputSearch(e.target.value);
  };

  const getFilterd = () => {
    if (inputSearch === "") return todoList;

    return todoList.filter((todo) =>
      todo.content.toLowerCase().includes(inputSearch.toLowerCase())
    );
  };

  const filteredList = getFilterd();

  return (
    <div className="TodoList">
      <h4>Todo List 👸🏼</h4>
      <input
        value={inputSearch}
        onChange={onInputSearch}
        placeholder="검색어를 입력해주세요."
      />

      {filteredList.map((todo) => {
        return (
          <TodoItem
            key={todo.id}
            todo={todo}
            onDelete={onDelete}
            onUpdate={onUpdate}
          />
        );
      })}
    </div>
  );
};
export default TodoList;
