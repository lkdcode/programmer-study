import { useState, useMemo, useContext } from "react";
import "../style/List.css";
import TodoItem from "./TodoItem";
import { TodoStateContext } from "../App";

const List = () => {
  const { todos } = useContext(TodoStateContext);

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

  const { totalCount, doneCount, notDoneCount } = useMemo(() => {
    const totalCount = todos.length;
    const doneCount = todos.filter((todo) => todo.isDone).length;
    const notDoneCount = totalCount - doneCount;

    return {
      totalCount,
      doneCount,
      notDoneCount,
    };
  }, [todos]);

  return (
    <div className="List">
      <h4>Todo List 🌱</h4>
      <div>totalCount: {totalCount}</div>
      <div>doneCount: {doneCount}</div>
      <div>notDoneCount: {notDoneCount}</div>
      <input
        value={search}
        onChange={onChangeSearch}
        placeholder="검색어를 입력해주세요."
      />
      <div className="todos_wrapper">
        {filtertedTodos.map((todo) => {
          return <TodoItem key={todo.id} todo={todo} />;
        })}
      </div>
    </div>
  );
};

export default List;
