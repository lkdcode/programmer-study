import useInput from "../hooks/useInput";

const HookEaxm = () => {
  const [input1, onChangeInput1] = useInput();
  const [input2, onChangeInput2] = useInput();

  return (
    <>
      <div>
        <input onChange={onChangeInput1} />
        <div>{input1}</div>
      </div>
      <div>
        <input onChange={onChangeInput2} />
        <div>{input2}</div>
      </div>
    </>
  );
};

export default HookEaxm;
