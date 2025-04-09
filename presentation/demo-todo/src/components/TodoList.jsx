import "../styles/TodoList.css";
import TodoItem from "./TodoItem";

const TodoList = ({ onUpdate, onDelete, datas }) => {
  return (
    <div className="todoList_section">
      <h4>Todo List 🎯</h4>
      <input className="search_box" placeholder="검색어를 입력해주세요." />
      <div>
        {datas.map((item) => {
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
