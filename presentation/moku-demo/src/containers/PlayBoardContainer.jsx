import "../styles/PlayBoardContainer.css";
import Stone from "../components/Stone";
import { useEffect, useRef, useState } from "react";
import "../styles/MatchingContainer.css";
import { useUserStore } from "../hooks/userStore";

const PlayBoardContainer = () => {
  const client = useUserStore((state) => state.client);
  const [moku, setMoku] = useState(
    Array(15)
      .fill()
      .map(() => Array(15).fill({ turn: null, row: 0, col: 0 }))
  );

  useEffect(() => {
    client.subscribe(`/topic/room.${localStorage.getItem("roomId")}`, (msg) => {
      const chat = JSON.parse(msg.body);
      if (chat.type !== "MOKU") {
        return;
      }

      const result = JSON.parse(chat.content);
      setMoku(result);
    });
  }, [client]);

  useEffect(() => {}, [moku]);

  const onClickMoku = (row, col) => {
    if (moku[row][col].turn !== null) return;

    client.publish({
      destination: `/app/room.${localStorage.getItem("roomId")}`,
      body: JSON.stringify({
        type: `MOKU`,
        content: `${row},${col}`,
      }),
    });
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
                    onClickMoku(row, col);
                  }}
                  key={`${row}-${col}`}
                  className={
                    moku[row]?.[col]?.turn === null
                      ? "playBoardBack_cell_point"
                      : ""
                  }
                >
                  {moku[row]?.[col]?.turn !== null ? (
                    <Stone variant={moku[row][col].turn} />
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
