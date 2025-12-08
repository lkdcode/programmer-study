import { useState } from 'react'
import type { KeyboardEvent } from 'react'
import './Editor.css'

// 캘리그래피 작품의 타입 정의
interface CalligraphyWork {
  id: number
  text: string
  style: string
}

function Editor() {
  // 사용자가 입력한 텍스트를 저장하는 상태
  const [inputText, setInputText] = useState<string>('')
  
  // 생성된 캘리그래피 작품들을 저장하는 배열
  const [works, setWorks] = useState<CalligraphyWork[]>([])

  // 캘리그래피를 생성하는 함수
  const handleCreate = () => {
    // 입력창이 비어있으면 생성하지 않습니다
    if (inputText.trim() === '') {
      alert('문구를 입력해주세요!')
      return
    }

    // 새로운 작품을 생성합니다
    const newWork: CalligraphyWork = {
      id: Date.now(),
      text: inputText,
      style: '클래식', // 기본 스타일
    }

    // 기존 작품 목록에 새로운 작품을 추가합니다
    setWorks([newWork, ...works])
    
    // 입력창을 비웁니다
    setInputText('')
  }

  // 작품을 삭제하는 함수
  const handleDelete = (id: number) => {
    setWorks(works.filter((work) => work.id !== id))
  }

  // Enter 키를 눌렀을 때도 생성하도록 합니다
  const handleKeyPress = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      handleCreate()
    }
  }

  return (
    <div className="editor-whisk">
      {/* 왼쪽 노란색 사이드바 (전체 높이) */}
      <aside className="yellow-sidebar">
        <button className="sidebar-icon-btn">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="currentColor"/>
          </svg>
        </button>
        <button className="sidebar-icon-btn">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
            <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z" fill="currentColor"/>
          </svg>
        </button>
        <button className="sidebar-icon-btn">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
            <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z" fill="currentColor"/>
          </svg>
        </button>
      </aside>

      {/* 오른쪽 영역 (헤더 + 메인 콘텐츠) */}
      <div className="editor-right">
        {/* 상단 헤더 */}
        <header className="editor-header">
          <div className="header-left">
            <div className="brand-section">
              <span className="brand-name">Calligraphy Studio</span>
              <button className="experiment-badge">EXPERIMENT</button>
            </div>
          </div>
          
          <div className="header-center">
            <button className="icon-btn active">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path d="M10 3v14M3 10h14" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
              </svg>
            </button>
            <button className="icon-btn">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <rect x="2" y="2" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
                <rect x="11" y="2" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
                <rect x="2" y="11" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
                <rect x="11" y="11" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
              </svg>
            </button>
            <button className="icon-btn">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path d="M10 17c-3.5 0-6.5-2-6.5-4.5 0-1.5 1.5-3 3.5-4l1.5-.5c.5-.2.8-.7.8-1.2v-1c0-.6.4-1 1-1s1 .4 1 1v1c0 .5.3 1 .8 1.2l1.5.5c2 1 3.5 2.5 3.5 4 0 2.5-3 4.5-6.5 4.5z" fill="currentColor"/>
              </svg>
            </button>
          </div>

          <div className="header-right">
            <button className="library-btn">내 라이브러리</button>
            <button className="icon-btn">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
                <circle cx="10" cy="10" r="8"/>
              </svg>
            </button>
            <button className="icon-btn">?</button>
            <button className="profile-btn">n</button>
          </div>
        </header>

        {/* 메인 콘텐츠 영역 */}
        <main className="editor-main">
          {/* 캔버스 영역 */}
          <div className="canvas-area">
            {works.length === 0 ? (
              <div className="empty-canvas">
                <p>문구를 입력하고 캘리그래피를 생성해보세요</p>
              </div>
            ) : (
              <div className="works-display">
                {works.map((work) => (
                  <div key={work.id} className="work-item">
                    <div className="work-content" style={{ fontFamily: getFontFamily(work.style) }}>
                      {work.text}
                    </div>
                    <button className="work-delete" onClick={() => handleDelete(work.id)}>×</button>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* 하단 입력 박스 */}
          <div className="input-box">
            <div className="input-content">
              <textarea
                value={inputText}
                onChange={(e) => setInputText(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="예: 행복한 하루 되세요"
                className="prompt-input"
                rows={2}
              />
              <div className="input-actions">
                <button className="add-image-btn">
                  <span>→</span> 이미지 추가
                </button>
                <div className="input-icons">
                  <button className="input-icon-btn">
                    <svg width="18" height="18" viewBox="0 0 20 20" fill="none">
                      <path d="M10 3v14M3 10h14" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
                    </svg>
                  </button>
                  <button className="input-icon-btn">
                    <svg width="18" height="18" viewBox="0 0 20 20" fill="none">
                      <rect x="2" y="2" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
                      <rect x="11" y="2" width="7" height="7" stroke="currentColor" strokeWidth="2"/>
                    </svg>
                  </button>
                  <button className="input-icon-btn">
                    <svg width="18" height="18" viewBox="0 0 20 20" fill="none">
                      <path d="M3 5h14M3 10h14M3 15h14" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
                    </svg>
                  </button>
                </div>
                <button className="submit-btn" onClick={handleCreate}>
                  <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                    <path d="M7 13l3 3 7-7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  </svg>
                </button>
              </div>
            </div>
          </div>

          {/* 하단 면책 조항 */}
          <div className="editor-footer">
            <p className="disclaimer">면책 조항: 이 도구는 실수를 할 수 있으니 다시 한번 확인하세요.</p>
            <div className="footer-links">
              <a href="#" className="footer-link">개인 정보 보호</a>
              <a href="#" className="footer-link">서비스 약관</a>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}

// 캘리그래피 스타일에 따라 폰트를 반환하는 함수
function getFontFamily(style: string): string {
  const fontMap: { [key: string]: string } = {
    '클래식': '"Brush Script MT", "Lucida Handwriting", cursive',
    '모던': '"Dancing Script", cursive',
    '엘레강트': '"Great Vibes", cursive',
    '손글씨': '"Kalam", cursive',
  }
  return fontMap[style] || 'cursive'
}

export default Editor
