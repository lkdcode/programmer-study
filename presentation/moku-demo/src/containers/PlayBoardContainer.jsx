import "../styles/PlayBoardContainer.css";
import Stone from "../components/Stone";

const boardSize = Array.from({ length: 15 });

const PlayBoardContainer = () => {
  const e = (row, col) => {
    console.log(`${row} + ${col}`);
  };

  return (
    <div className="PlayBoardContainer">
      <div className="playBoardWrapper">
        <div className="playBoardBack">
          {Array.from({ length: 15 }).map((_, row) =>
            Array.from({ length: 15 }).map((_, col) => (
              <div className="playBoardBack_cell" key={`${row}-${col}`}>
                <div
                  onClick={() => {
                    e(row, col);
                  }}
                  key={`${row}-${col}`}
                  className="playBoardBack_cell_point"
                >
                  {Math.random() < 0.5 ? (
                    <Stone
                      variant={
                        Math.random() < 0.5 ? "STONE_WHITE" : "STONE_BLACK"
                      }
                    />
                  ) : null}
                </div>
              </div>
            ))
          )}
        </div>
        <div className="playBoardFront">
          {Array.from({ length: 14 }).map((_, row) =>
            Array.from({ length: 14 }).map((_, col) => (
              <div className="playBoardFront_cell" key={`${row}-${col}`}></div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default PlayBoardContainer;
