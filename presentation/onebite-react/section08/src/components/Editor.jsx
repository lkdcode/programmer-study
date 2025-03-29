import { useRef, useState } from "react";
import "../style/Editor.css";

const Editor = ({ onCreate }) => {
  const [content, setContent] = useState("");

  const contentRef = useRef();
  const onChangeContent = (e) => {
    setContent(e.target.value);
  };

  const onSubmit = () => {
    if (content === "") {
      alert("Todo를 입력해주세요 !!");
      contentRef.current.focus();
      return;
    }

    onCreate(content);
    setContent("");
  };

  const onSubmitEnter = (e) => {
    if (e.keyCode === 13) {
      onSubmit();
    }
  };

  return (
    <div className="Editor">
      <input
        ref={contentRef}
        value={content}
        onChange={onChangeContent}
        onKeyDown={onSubmitEnter}
        placeholder="새로운 Todo..."
      />
      <button onClick={onSubmit}>추가</button>
    </div>
  );
};

export default Editor;
