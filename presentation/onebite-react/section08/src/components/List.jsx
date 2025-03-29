import { useState } from "react";
import "../style/List.css";
import TodoItem from "./TodoItem";

const List = ({ todos, onUpdate, onDelete }) => {
  const [search, setSearch] = useState("");

  const onChangeSearch = (e) => {
    setSearch(e.target.value);
  };

  const getFilteredData = () => {
    if (search === "") {
      return todos;
    }

    return todos.filter((todo) => {
      return todo.content.toLowerCase().includes(search.toLowerCase());
    });
  };

  const filtertedTodos = getFilteredData();

  return (
    <div className="List">
      <h4>Todo List 🌱</h4>
      <input
        value={search}
        onChange={onChangeSearch}
        placeholder="검색어를 입력해주세요."
      />
      <div className="todos_wrapper">
        {filtertedTodos.map((todo) => {
          return (
            <TodoItem
              key={todo.id}
              todo={todo}
              onUpdate={onUpdate}
              onDelete={onDelete}
            />
          );
        })}
      </div>
    </div>
  );
};

export default List;
