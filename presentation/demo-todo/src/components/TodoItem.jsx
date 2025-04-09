import { useState } from "react";
import "../styles/TodoItem.css";

const TodoItem = ({ id, content, date, isDone, onDelete, onUpdate }) => {
  const [done, setDone] = useState(isDone);

  const onClickDelete = () => {
    onDelete(id);
  };

  const onClickUpdate = () => {
    onUpdate(id);
  };

  return (
    <div className="item_section">
      <input onClick={onClickUpdate} className="item_check" type="checkbox" />
      <div className="item_content">{content}</div>
      <div className="item_date">
        {date.toLocaleDateString("ko-KR", {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
        })}
      </div>
      <button onClick={onClickDelete} className="item_button">
        삭제
      </button>
    </div>
  );
};

export default TodoItem;
