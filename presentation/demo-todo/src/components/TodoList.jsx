import { useState } from "react";
import "../styles/TodoList.css";
import TodoItem from "./TodoItem";

const TodoList = ({ onUpdate, onDelete, datas }) => {
  const [search, setSearch] = useState("");
  const onChangeSearch = (e) => {
    setSearch(e.target.value);
  };

  const getFilteredDatas = () => {
    return search === ""
      ? datas
      : datas.filter((item) =>
          item.content.toLowerCase().includes(search.toLowerCase())
        );
  };

  const filteredDatas = getFilteredDatas();

  return (
    <div className="todoList_section">
      <h4>Todo List 🎯</h4>
      <input
        value={search}
        onChange={onChangeSearch}
        className="search_box"
        placeholder="검색어를 입력해주세요."
      />
      <div>
        {filteredDatas.map((item) => {
          return (
            <TodoItem
              key={item.id}
              id={item.id}
              content={item.content}
              date={item.date}
              isDone={item.isDone}
              onDelete={onDelete}
              onUpdate={onUpdate}
            />
          );
        })}
      </div>
    </div>
  );
};

export default TodoList;
