import logo from './logo.svg';
import './App.css';
import MainPage from './component/page/MainPage';
import PostWritePage from './component/page/PostWritePage';
import PostViewPage from './component/page/PostViewPage';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import styled from 'styled-components';

const MainTitleText = styled.p`
  font-size: 24px;
  font-weight: bold;
  text-align: center;
`;

function App() {
  return (
     <BrowserRouter>
        <MainTitleText>Mini Blog</MainTitleText>
        <Routes>
          <Route index element={<MainPage />}/>
          <Route path="post-write" element={<PostWritePage />}/>
          <Route path="post/:postId" element={<PostViewPage />}/>
        </Routes>
     </BrowserRouter>
  );
}

export default App;
