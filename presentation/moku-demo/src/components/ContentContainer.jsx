import { useState } from "react";
import "../styles/ContentContainer.css";
import LeftContainer from "./LeftContainer";
import LoginContainer from "../containers/LoginContainer";
import SignUpContainer from "../containers/SignUpContainer";
import StatusContainer from "../containers/StatusContainer";

const PAGE_STATE = {
  LOGIN: "login",
  SIGNUP: "signup",
  SUCCESS: "success",
};

const ContentContainer = () => {
  const [pageState, setComponent] = useState(PAGE_STATE.LOGIN);

  const changeSignUp = () => {
    setComponent("signup");
  };

  const changeLogin = () => {
    setComponent("login");
  };

  const successLogin = () => {
    setComponent("success");
  };

  return (
    <div className="contentContainer">
      <LeftContainer />

      <div className="rightContent">
        {pageState === "success" ? (
          <StatusContainer logoutSubmit={changeLogin} />
        ) : pageState === "signup" ? (
          <SignUpContainer link={changeLogin} />
        ) : (
          <LoginContainer link={changeSignUp} loginSubmit={successLogin} />
        )}
      </div>
    </div>
  );
};

export default ContentContainer;
