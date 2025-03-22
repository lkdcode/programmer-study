import { useState, useRef } from "react";

// name
// birth
// age
// country
// bio

const User = () => {
  const [info, setInfo] = useState({
    name: "",
    birth: "",
    country: "",
    bio: "",
    age: 20,
  });

  const nameRef = useRef();
  const birthRef = useRef();
  const countryRef = useRef();
  const bioRef = useRef();
  const ageRef = useRef();

  const onChangeInfo = (e) => {
    setInfo({ ...info, [e.target.name]: e.target.value });
  };

  const onChangeAgeIncrement = (e) => {
    console.log("hhh1");
    setInfo({ ...info, age: info.age + 1 });
  };

  const onChangeAgeDecrement = (e) => {
    console.log("hhh2");
    setInfo({ ...info, age: info.age - 1 });
  };

  const onClickSubmit = (e) => {
    if (info.name === "") {
      nameRef.current.focus();
      return;
    }

    if (info.birth === "") {
      birthRef.current.focus();
      return;
    }

    if (info.country === "") {
      countryRef.current.focus();
      return;
    }

    if (info.bio === "") {
      bioRef.current.focus();
      return;
    }

    alert("제출 완료 !!");
  };

  return (
    <>
      <div>
        <input
          ref={nameRef}
          name="name"
          onChange={onChangeInfo}
          placeholder="이름"
        />
        <div>{info.name}</div>
      </div>

      <div>
        <input
          ref={birthRef}
          name="birth"
          type="date"
          onChange={onChangeInfo}
        />
        <div>{info.birth}</div>
      </div>

      <div>
        <select ref={countryRef} name="country" onChange={onChangeInfo}>
          <option></option>
          <option>한국</option>
          <option>미국</option>
          <option>영국</option>
        </select>
        <div>{info.country}</div>
      </div>

      <div>
        <textarea
          ref={bioRef}
          name="bio"
          onChange={onChangeInfo}
          placeholder="자기소개"
        />
        <div>{info.bio}</div>
      </div>

      <div>
        <button onClick={onChangeAgeIncrement}>Up</button>
        <span>나이: {info.age}</span>
        <button onClick={onChangeAgeDecrement}>Down</button>
      </div>

      <button onClick={onClickSubmit}>Submit</button>
    </>
  );
};

export default User;
