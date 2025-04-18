import "../styles/UnderBarInput.css";

const UnderBarInput = ({ type = "text", title, placeHolder, variant }) => {
  return (
    <div className="underBarInputWrapper">
      <div className="underBarInputTitle">{title}</div>
      <input
        type={type}
        className={`underBarInput ${variant}`}
        placeholder={placeHolder}
      />
    </div>
  );
};

export default UnderBarInput;
