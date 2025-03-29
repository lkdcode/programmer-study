import "../style/Viewer.css";

const Viewer = ({ count }) => {
  return (
    <>
      <section className="Viewer">
        <div>현재 카운트 : </div>
        <h2>{count}</h2>
      </section>
    </>
  );
};

export default Viewer;
