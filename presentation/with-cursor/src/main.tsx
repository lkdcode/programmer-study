import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider, Navigate } from 'react-router-dom'
import './index.css'
import LandingRoute from './routes/LandingRoute.tsx'
import EditorRoute from './routes/EditorRoute.tsx'

const router = createBrowserRouter([
  // 기본 URL은 랜딩 페이지로
  { path: '/', element: <LandingRoute /> },
  // /project 에서는 에디터(프롬프트) 노출
  { path: '/project', element: <EditorRoute /> },
  // 기타 경로는 랜딩으로 리다이렉트
  { path: '*', element: <Navigate to="/" replace /> },
])

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
