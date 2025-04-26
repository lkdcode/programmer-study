import MatchPlayerCard from "../components/MatchPlayerCard";
import "../styles/MatchUpContainer.css";

const MatchUpContainer = () => {
  return (
    <div className="matchUpContainer">
      <MatchPlayerCard turn={true} stoneColor="STONE_BLACK" />
      <MatchPlayerCard turn={false} stoneColor="STONE_WHITE" />
    </div>
  );
};

export default MatchUpContainer;
