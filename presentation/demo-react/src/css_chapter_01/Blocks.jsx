import styled from "styled-components";

const Wrapper = styled.div`
  padding: 1rem;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: flex-start;
  background-color: lightgrey;
`;

const Block = styled.div`
  padding: ${p => p.padding};
  border: 1px solid black;
  border-radius: 1rem;
  background-color: ${p => p.backgroundColor};
  color: white;
  font-size: 2rem;
  font-weight: bold;
  text-align: center;
`;

const blockItems = [
  {
    label: "1",
    padding: "1rem",
    backgroundColor: "red",
  },
  {
    label: "2",
    padding: "3rem",
    backgroundColor: "green",
  },
  {
    label: "3",
    padding: "2rem",
    backgroundColor: "blue",
  },
];

function Blocks(props) {
  return (
    <Wrapper>
      {blockItems.map(item => {
        return (
          <Block
            padding={item.padding}
            backgroundColor={item.backgroundColor}
          >
            {item.label}
          </Block>
        )
      })}
    </Wrapper>
  )
}

export default Blocks;