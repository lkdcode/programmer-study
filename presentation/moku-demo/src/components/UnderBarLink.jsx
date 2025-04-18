import "../styles/UnderBarLink.css";

const UnderBarLink = ({ title, variant }) => {
  return (
    <div className="underBarWrapper">
      <div className={`underBarLink ${variant}`}>{title}</div>
    </div>
  );
};

export default UnderBarLink;
