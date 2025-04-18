import "../styles/UnderBarInput.css";

const UnderBarInput = ({ title, placeHolder, variant }) => {
  return (
    <div className="underBarInputWrapper">
      <div className="underBarInputTitle">{title}</div>
      <input className={`underBarInput ${variant}`} placeholder={placeHolder} />
    </div>
  );
};

export default UnderBarInput;
