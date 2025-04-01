import "../styles/Button.css";

const Button = ({ title, onClickFun }) => {
  return (
    <button className="Button" onClick={onClickFun}>
      {title}
    </button>
  );
};

export default Button;
