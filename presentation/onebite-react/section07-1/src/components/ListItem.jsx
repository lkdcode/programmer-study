import "../style/ListItem.css";

const ListItem = ({ title, date }) => {
  return (
    <div className="ListItem">
      <input type="checkbox" />
      <div className="TodoTitle">{title}</div>
      <div className="TodoDate">{date}</div>
      <button>삭제</button>
    </div>
  );
};

export default ListItem;
