import "../style/Controller.css";

const Controller = ({ onClicnCount }) => {
  return (
    <>
      <section className="Controller">
        <button
          onClick={() => {
            onClicnCount(-1);
          }}
        >
          -1
        </button>
        <button
          onClick={() => {
            onClicnCount(-10);
          }}
        >
          -10
        </button>
        <button
          onClick={() => {
            onClicnCount(-100);
          }}
        >
          -100
        </button>
        <button
          onClick={() => {
            onClicnCount(+100);
          }}
        >
          +100
        </button>
        <button
          onClick={() => {
            onClicnCount(+10);
          }}
        >
          +10
        </button>
        <button
          onClick={() => {
            onClicnCount(+1);
          }}
        >
          +1
        </button>
      </section>
    </>
  );
};

export default Controller;
