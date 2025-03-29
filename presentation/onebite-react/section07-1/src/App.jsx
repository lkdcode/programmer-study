import "./App.css";
import { useEffect, useState } from "react";
import Editor from "./components/Editor";
import Header from "./components/Header";
import List from "./components/List";

function App() {
  const [todoList, setTodoList] = useState([]);

  const addTodo = (e) => {
    setTodoList((prev) => [...prev, e]);
  };

  return (
    <div className="App">
      <Header />
      <Editor addTodo={addTodo} />
      <List todoList={todoList} />
    </div>
  );
}

export default App;
