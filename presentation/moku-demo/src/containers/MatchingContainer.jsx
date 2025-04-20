import CircleButton from "../components/CircleButton";
import "../styles/MatchingContainer.css";
import logo from "./../assets/logo/logo.png";

const MatchingContainer = ({ cancle }) => {
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
