import "../styles/TodoItem.css";

const TodoItem = ({ todo, onDelete, onUpdate }) => {
  return (
    <div className="TodoItem">
      <input
        type="checkbox"
        onClick={() => {
          onUpdate(todo.id);
        }}
      />
      <div className="Todo_Title">{todo.content}</div>
      <div className="Todo_Date">
        {new Date(todo.date).toLocaleDateString()}
      </div>
      <button
        onClick={() => {
          onDelete(todo.id);
        }}
      >
        삭제
      </button>
    </div>
  );
};

export default TodoItem;
