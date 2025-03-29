import { useRef, useState } from "react";
import "./App.css";
import Controller from "./components/Controller";
import TodoList from "./components/TodoList";
import Header from "./components/header";
import TodoData from "./data/TodoData";

function App() {
  const [todoList, setTodoList] = useState(TodoData);
  const idRef = useRef(3);

  const createTodo = (content) => {
    setTodoList([
      ...todoList,
      {
        id: idRef.current++,
        content: content,
        date: new Date(),
        isDone: false,
      },
    ]);
  };

  const onDelete = (id) => {
    setTodoList(todoList.filter((target) => target.id !== id));
  };

  const onUpdate = (id) => {
    todoList.map((target) =>
      target.id === id ? (target.isDone = !target.isDone) : target
    );
  };

  const onSearch = () => {
    console.log("onSearch");
  };

  return (
    <div className="App">
      <Header />
      <Controller createTodo={createTodo} />
      <TodoList
        todoList={todoList}
        onDelete={onDelete}
        onUpdate={onUpdate}
        onSearch={onSearch}
      />
    </div>
  );
}

export default App;
