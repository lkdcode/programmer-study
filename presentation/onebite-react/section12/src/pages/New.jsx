import Button from "../components/Button";
import Header from "../components/Header";
import Editor from "../components/Editor";
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { DiaryDispatchContext } from "../App";

const New = () => {
  const nav = useNavigate();
  const { onCreate } = useContext(DiaryDispatchContext);

  const onSubmit = (input) => {
    console.log("=================");
    console.log(input.emotionId);
    console.log("=================");
    onCreate(input.createdDate.getTime(), input.emotionId, input.content);
    nav("/", { replace: true });
  };

  return (
    <div>
      <Header
        leftChild={<Button onClick={() => nav(-1)} text={"< 뒤로가기"} />}
        title={"새 일기 쓰기"}
      />
      <Editor onSubmit={onSubmit} />
    </div>
  );
};

export default New;
