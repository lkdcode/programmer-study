import "../styles/UnderBarInput.css";

const UnderBarInput = ({
  type = "text",
  title,
  placeHolder,
  variant,
  outline = "none",
}) => {
  return (
    <div className="underBarInputWrapper">
      <div className="underBarInputTitle">{title}</div>
      <input
        type={type}
        className={`underBarInput ${variant} ${outline}`}
        placeholder={placeHolder}
      />
    </div>
  );
};

export default UnderBarInput;
