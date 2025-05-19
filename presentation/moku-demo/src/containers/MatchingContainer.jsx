import { useEffect, useRef } from "react";
import CircleButton from "../components/CircleButton";
import "../styles/MatchingContainer.css";
import logo from "./../assets/logo/logo.png";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { HTTP_API } from "../service/Api";
import { useUserStore } from "../hooks/userStore";

const MatchingContainer = ({ cancle, setJoin, setMatching }) => {
  const client = useRef(null);
  const { setClient } = useUserStore.getState();

  const matchJoin = () => {
    if (client.current) return;

    client.current = new Client({
      webSocketFactory: () => new SockJS(`${HTTP_API}/ws`),
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("⭐️ 소켓 연결 완료 ⭐️");

        client.current.subscribe("/user/queue/matched", (msg) => {
          const matchingResult = JSON.parse(msg.body);
          console.log(`msg.body: ${msg.body}`);

          localStorage.removeItem("roomId");
          localStorage.setItem("roomId", matchingResult.roomId);

          localStorage.removeItem("id");
          localStorage.setItem("id", matchingResult.id);

          setTimeout(() => {
            setJoin(true);
            setMatching(true);
          }, 3000);
        });

        client.current.publish({
          destination: "/app/ready",
        });
      },

      onStompError: (frame) => {
        console.error("STOMP error:", frame.headers["message"], frame.body);
      },
    });

    client.current.activate();
    setClient(client.current);
  };

  useEffect(() => {
    matchJoin();
  }, []);

  return (
    <div className="matchingContainer">
      <div className="matchingWrapper">
        <div className="matchingLogo">
          <img src={logo} />
          <div className="cancleButton">
            <CircleButton event={cancle} />
          </div>
        </div>
        <div className="matchingContent">
          <p>...매칭 중입니다.</p>
          <h2>[TIP] 3x3을 허용하고 있어요.</h2>
        </div>
      </div>
    </div>
  );
};

export default MatchingContainer;
