import { useRef, useState } from "react";
import "../styles/BoardPiece.css";

const BoardPiece = ({ row, col }) => {
  const [onStart, setOnStart] = useState(false);
  const mokuRef = useRef("Moku-black");
  const onOnStart = () => {
    setOnStart(true);
    mokuRef.current =
      mokuRef.current === "Moku-black" ? "Moku-white" : mokuRef.current;
  };

  return (
    <button className="BoardPiece" onClick={onOnStart}>
      <div className={`Moku-start ${onStart ? mokuRef.current : ""}`}></div>
    </button>
  );
};

export default BoardPiece;
