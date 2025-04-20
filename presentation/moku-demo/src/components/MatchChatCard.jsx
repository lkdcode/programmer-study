import "../styles/MatchChatCard.css";
import ChatBubble from "./ChatBubble";
import UnderBarInput from "../components/UnderBarInput";

const MatchChatCard = () => {
  return (
    <div className="matchChatCard">
      <div className="messageWrapper">
        <div className="messageBox">
          <ChatBubble />
          <div className="bubble">chat1</div>
          <div className="bubble">chat2</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
          <div className="bubble">chat3</div>
        </div>
      </div>
      <div className="messageInput">
        {/* <UnderBarInput
          placeHolder={"메세지를 입력하세요."}
          variant={"PRIMARY_INPUT"}
          outline="OUTLINE_NONE"
        /> */}
        <textarea placeholder="메세지를 입력하세요." className="messageArea" />
      </div>
    </div>
  );
};

export default MatchChatCard;
