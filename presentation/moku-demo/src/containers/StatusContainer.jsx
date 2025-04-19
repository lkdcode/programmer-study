import "../styles/StatusContainer.css";

import Button from "../components/Button";
import CustomActiveShapePieChart from "../components/CustomActiveShapePieChart";

const StatusContainer = ({ logoutSubmit }) => {
  return (
    <div className="statusContainer">
      <div className="statusWrapper">
        <div className="titleWrapper">
          <h1>홍길동</h1>
          <p>오목 한 판 어때?!</p>
          <p>온라인에서 오목을 즐겨봐요!</p>
        </div>
      </div>
      <div className="scoreWrapper">
        <div className="scoreGraph">
          <CustomActiveShapePieChart />
        </div>
        <div className="score">37전 32승 5패</div>
      </div>
      <div className="buttonWrapper">
        <Button name="시작하기" variant={"PRIMARY_BUTTON"} />
        <Button name="로그아웃" event={logoutSubmit} variant={"DARK_BUTTON"} />
      </div>
    </div>
  );
};

export default StatusContainer;
