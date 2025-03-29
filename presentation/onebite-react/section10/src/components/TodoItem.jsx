import { memo } from "react";
import "../style/TodoItem.css";

const TodoItem = ({ todo, onUpdate, onDelete }) => {
  const onChangeCheckbox = () => {
    onUpdate(todo.id);
  };

  const onChangeDelete = () => {
    onDelete(todo.id);
  };

  return (
    <div className="TodoItem">
      <input
        onChange={onChangeCheckbox}
        checked={todo.isDone}
        type="checkbox"
      />
      <div className="content">{todo.content}</div>
      <div className="date">{new Date(todo.date).toLocaleDateString()}</div>
      <button onClick={onChangeDelete}>삭제</button>
    </div>
  );
};

export default memo(TodoItem);
