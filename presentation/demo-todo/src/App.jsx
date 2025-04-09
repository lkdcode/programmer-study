import { useRef, useState } from "react";
import "./App.css";
import Header from "./components/Header";
import Input from "./components/Input";
import TodoList from "./components/TodoList";

export const MockDatas = [
  {
    id: 1,
    content: "React-!",
    date: new Date(),
    isDone: false,
  },

  {
    id: 2,
    content: "Kotlin-!",
    date: new Date(),
    isDone: false,
  },

  {
    id: 3,
    content: "SQL-!",
    date: new Date(),
    isDone: false,
  },
];

function App() {
  const [datas, setDatas] = useState(MockDatas);
  const refId = useRef(4);

  const onCreate = (content) => {
    setDatas([
      ...datas,
      {
        id: refId.current++,
        content: content,
        date: new Date(),
        isDone: false,
      },
    ]);
  };

  const onUpdate = (id) => {
    setDatas(
      datas.map((item) =>
        item.id === id ? { ...item, isDone: !item.isDone } : item
      )
    );
  };

  const onDelete = (id) => {
    setDatas(datas.filter((item) => item.id !== id));
  };

  return (
    <div className="App">
      <Header />
      <Input onCreate={onCreate} />
      <TodoList onUpdate={onUpdate} onDelete={onDelete} datas={datas} />
    </div>
  );
}

export default App;
