import "../styles/MatchPlayerCard.css";
import Stone from "./Stone";
import player from "../assets/emoji/player.svg";

const MatchPlayerCard = ({ turn }) => {
  return (
    <div className="matchPlayerCard">
      <div className="turnWrapper">
        <Stone variant={"STONE_WHITE"} />
        <div>02:24</div>
      </div>
      <div className="playerImgWrapper">
        <img src={player} className="playerImg" />
      </div>
      <div className="recordWrapper">
        <div>홍길동</div>
        <div>32전 3승 29패</div>
        <div>9.37%</div>
      </div>
    </div>
  );
};

export default MatchPlayerCard;
