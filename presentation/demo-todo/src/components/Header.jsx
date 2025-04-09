import "../styles/Header.css";

const Header = () => {
  return (
    <div>
      <h4>오늘은 🚀</h4>
      <h1 className="todo_date">{new Date().toDateString()}</h1>
    </div>
  );
};

export default Header;
