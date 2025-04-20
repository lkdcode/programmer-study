import "../styles/CircleButton.css";

import cancleLogo from "./../assets/emoji/cancle.svg";

const CircleButton = ({ event }) => {
  return (
    <button
      onClick={() => {
        event();
      }}
      className="circleButton"
    >
      <img src={cancleLogo} />
    </button>
  );
};

export default CircleButton;
