import "../styles/MatchChatCard.css";
import ChatBubble from "./ChatBubble";
import UnderBarInput from "../components/UnderBarInput";

const MatchChatCard = () => {
  return (
    <div className="matchChatCard">
      <div className="messageWrapper">
        <div className="messageBox">
          <ChatBubble message={"삼삼 \n하지마라 \n매너해라"} type="RECEIVE" />
          <ChatBubble message={"빨리둬라"} type="SEND" />
          <ChatBubble
            message={`내가 삼삼 하지말랬잖아 매너하라고 삼삼은 매너
              내가 삼삼 하지말랬잖아 매너하라고 삼삼은 매너
              내가 삼삼 하지말랬잖아 매너하라고 삼삼은 매너`}
            type="RECEIVE"
          />
          <ChatBubble message={"빨리둬라"} type="SEND" />
          <ChatBubble message={"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"} type="SEND" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble
            message={
              "ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ"
            }
            type="SEND"
          />

          <ChatBubble message={"외통입니다"} type="SEND" />
          <ChatBubble message={"외통입니다"} type="SEND" />
          <ChatBubble
            message={`외통입니다ㅋㅋㅋㅋ\n
            ㅋㅋ
            `}
            type="SEND"
          />
          <ChatBubble message={"외통입니다"} type="SEND" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble message={"삼삼 하지마라!!!!!!!!!!"} type="RECEIVE" />
          <ChatBubble
            message={
              "33법률안에 이의가 있을 때에는 대통령은 제1항의 기간내에 이의서를 붙여 국회로 환부하고, 그 재의를 요구할 수 있다. 국회의 폐회중에도 또한 같다. 헌법재판소는 법률에 저촉되지 아니하는 범위안에서 심판에 관한 절차, 내부규율과 사무처리에 관한 규칙을 제정할 수 있다. 국군은 국가의 안전보장과 국토방위의 신성한 의무를 수행함을 사명으로 하며, 그 정치적 중립성은 준수된다. 모든 국민은 통신의 비밀을 침해받지 아니한다."
            }
            type="RECEIVE"
          />
          <ChatBubble
            message={`44zz
              법률안에 이의가 있을 때에는 대통령은 제1항의 기간내에 이의서를 붙여 국회로 환부하고, 그 재의를 요구할 수 있다. 국회의 폐회중에도 또한 같다. 헌법재판소는 법률에 저촉되지 아니하는 범위안에서 심판에 관한 절차, 내부규율과 사무처리에 관한 규칙을 제정할 수 있다. 국군은 국가의 안전보장과 국토방위의 신성한 의무를 수행함을 사명으로 하며, 그 정치적 중립성은 준수된다. 모든 국민은 통신의 비밀을 침해받지 아니한다.
              `}
            type="SEND"
          />
        </div>
      </div>
      <div className="messageInput">
        <textarea placeholder="Message" className="messageArea" />
      </div>
    </div>
  );
};

export default MatchChatCard;
