import { useState } from "react";
import "../styles/ContentContainer.css";
import LeftContainer from "./LeftContainer";
import LoginContainer from "../containers/LoginContainer";
import SignUpContainer from "../containers/SignUpContainer";

const ContentContainer = () => {
  const [pageState, setComponent] = useState("login");

  const changeSignUp = () => {
    setComponent("signUp");
  };

  const changeLogin = () => {
    setComponent("login");
  };

  return (
    <div className="contentContainer">
      <LeftContainer />

      <div className="rightContent">
        {pageState === "login" ? (
          <LoginContainer link={changeSignUp} />
        ) : (
          <SignUpContainer link={changeLogin} />
        )}
      </div>
    </div>
  );
};

export default ContentContainer;
