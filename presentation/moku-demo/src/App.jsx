import { useState } from "react";
import "./App.css";
import ContentContainer from "./components/ContentContainer";
import MatchingContainer from "./containers/MatchingContainer";
import MokuPage from "./pages/MokuPage";

const MATCH_STATE = {
  NONE: "none",
  MATCHING: "MATCHING",
};

function App() {
  const [matching, setMatching] = useState(MATCH_STATE.NONE);

  const toggleMathcing = () => {
    setMatching(matching === "none" ? MATCH_STATE.MATCHING : MATCH_STATE.NONE);
  };

  const cancelMatching = () => {
    setMatching(MATCH_STATE.NONE);
  };

  return (
    <div className="App">
      {/* <ContentContainer matchSubmit={toggleMathcing} />
      {matching === "MATCHING" ? (
        <MatchingContainer cancle={cancelMatching} />
      ) : null} */}
      <MokuPage />
    </div>
  );
}

export default App;
