import "../styles/Button.css";

const Button = ({ name, variant }) => {
  return <button className={`button ${variant}`}>{name}</button>;
};

export default Button;
