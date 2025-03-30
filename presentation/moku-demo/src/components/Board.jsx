import "../styles/Board.css";
import BoardPiece from "./BoardPiece";

const Board = ({ size }) => {
  return (
    <div className="Board">
      {Array.from({ length: size }).map((_, row) => (
        <div key={row} className="row">
          {Array.from({ length: size }).map((_, col) => (
            <BoardPiece key={`${row}-${col}`} row={row} col={col} />
          ))}
        </div>
      ))}
    </div>
  );
};

export default Board;
