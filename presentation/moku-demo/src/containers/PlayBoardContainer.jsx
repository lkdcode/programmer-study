import "../styles/PlayBoardContainer.css";
import Stone from "../components/Stone";
import { useEffect, useRef, useState } from "react";
import "../styles/MatchingContainer.css";
import { useUserStore } from "../hooks/userStore";
import { useNavigate } from "react-router-dom";

const PlayBoardContainer = () => {
  const client = useUserStore((state) => state.client);
  const [resultFlag, setResultFlag] = useState(false);
  const resultRef = useRef(false);

  const [moku, setMoku] = useState(
    Array(15)
      .fill()
      .map(() => Array(15).fill(null))
  );

  useEffect(() => {
    const subscription = client.subscribe(
      `/topic/room.${localStorage.getItem("roomId")}`,
      (msg) => {
        const chat = JSON.parse(msg.body);
        if (chat.type !== "PLAY") {
          return;
        }

        const response = JSON.parse(chat.content);

        console.log(`RESULT : ${response.result}`);
        console.log(`BOARD : ${response.board}`);

        setMoku(response.board);
        // if (response.result === "VICTORY") {
        //   console.log(`RESPONSE RESULT: ${response.result}`);
        //   useRef.current = true;
        //   setResultFlag(true);
        // }
        if (response.result === "VICTORY") {
          setTimeout(() => {
            const id = localStorage.getItem("id");

            if (response.player === id) {
              console.log(`${id}!!!!!!!!!!!!!!!!! 승 리 !!!!!!!!!!!!!!!!`);
              alert(`!!!!!!!!!!!!!!!!! 승 리 !!!!!!!!!!!!!!!!`);
            } else {
              alert(`!!!!!!!!!!!!!!!!! 패 배 !!!!!!!!!!!!!!!!`);
            }

            client.deactivate(() => {
              console.log("종료");
            });

            window.location.href = "/";
          }, 300);
        }
      }
    );

    client.subscribe("/user/queue/errors", (message) => {
      const msg = JSON.parse(message.body);
      alert(msg.message);
    });

    return () => subscription.unsubscribe();
  }, [client]);

  useEffect(() => {
    console.log(`USE EFFECT !!: ${resultRef.current}`);
    console.log(`USE EFFECT !!: ${resultFlag}`);
    if (resultFlag) {
      alert("VICTORY!!!!!!!!!!");
      resultRef.current = false;
      setResultFlag(false);
    }
  }, [resultFlag]);

  useEffect(() => {}, [moku]);

  const onClickMoku = (row, col) => {
    if (moku[row][col] !== null) return;

    console.log(`ROW: ${row}, COL: ${col}`);

    client.publish({
      destination: `/app/room.${localStorage.getItem("roomId")}`,
      body: JSON.stringify({
        type: `PLAY`,
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
                    moku[row][col] === null ? "playBoardBack_cell_point" : ""
                  }
                >
                  {moku[row][col] && <Stone variant={moku[row][col]} />}
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
