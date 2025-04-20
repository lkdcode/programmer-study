import MatchPlayerCard from "../components/MatchPlayerCard";
import "../styles/MatchUpContainer.css";

const MatchUpContainer = () => {
  return (
    <div className="matchUpContainer">
      <MatchPlayerCard />
      <MatchPlayerCard />
    </div>
  );
};

export default MatchUpContainer;
