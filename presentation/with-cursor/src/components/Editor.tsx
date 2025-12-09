import { useState, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import './Editor.css'

// 캘리그래피 작품의 타입 정의
interface CalligraphyWork {
  id: number
  text: string
  style: string
}

function Editor() {
  const navigate = useNavigate()
  
  // 사용자가 입력한 텍스트를 저장하는 상태
  const [inputText, setInputText] = useState<string>('')
  
  // 생성된 캘리그래피 작품들을 저장하는 배열
  const [works, setWorks] = useState<CalligraphyWork[]>([])
  
  // 로딩 상태
  const [isLoading, setIsLoading] = useState<boolean>(false)
  
  // contentEditable div의 ref
  const promptRef = useRef<HTMLDivElement>(null)
  
  // canvas-area의 ref (스크롤 제어용)
  const canvasAreaRef = useRef<HTMLDivElement>(null)
  
  // 스켈레톤 요소의 ref
  const skeletonRef = useRef<HTMLDivElement>(null)
  
  // 모달 상태 관리
  const [openModal, setOpenModal] = useState<string | null>(null)
  const [modalPosition, setModalPosition] = useState<{ top: number; left: number } | null>(null)
  
  // 시드 입력 상태
  const [seed, setSeed] = useState<string>('492565')
  const [isSeedLocked, setIsSeedLocked] = useState<boolean>(false)
  
  // 비율 선택 상태
  const [selectedRatio, setSelectedRatio] = useState<string>('1:1')
  
  // 속성 선택 상태
  const [selectedStyle, setSelectedStyle] = useState<string>('클래식')
  
  // 버튼 refs
  const cropBtnRef = useRef<HTMLButtonElement>(null)
  const zoomBtnRef = useRef<HTMLButtonElement>(null)
  const settingsBtnRef = useRef<HTMLButtonElement>(null)

  // 캘리그래피를 생성하는 함수
  const handleCreate = async () => {
    // 입력창이 비어있으면 생성하지 않습니다
    if (inputText.trim() === '') {
      alert('문구를 입력해주세요!')
      return
    }

    // 로딩 시작
    setIsLoading(true)

    // 서버 요청 시뮬레이션 (3초)
    await new Promise(resolve => setTimeout(resolve, 3000))

    // 새로운 작품을 생성합니다
    const newWork: CalligraphyWork = {
      id: Date.now(),
      text: inputText,
      style: '클래식', // 기본 스타일
    }

    // 기존 작품 목록에 새로운 작품을 추가합니다 (최신 것이 아래로)
    setWorks([...works, newWork])
    
    // 입력창은 유지합니다 (지우지 않음)
    
    // 로딩 종료
    setIsLoading(false)
  }

  // 작품을 삭제하는 함수
  const handleDelete = (id: number) => {
    setWorks(works.filter((work) => work.id !== id))
  }

  // Enter 키를 눌렀을 때도 생성하도록 합니다
  const handleKeyPress = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      handleCreate()
    }
  }

  // 입력 처리 함수
  const handleInput = (e: React.FormEvent<HTMLDivElement>) => {
    const text = e.currentTarget.textContent || ''
    setInputText(text)
  }

  // 모달 열기 함수
  const handleOpenModal = (modalType: string, buttonRef: React.RefObject<HTMLButtonElement | null>) => {
    if (buttonRef.current) {
      const rect = buttonRef.current.getBoundingClientRect()
      setModalPosition({
        top: rect.top - 8, // 버튼 위 8px
        left: rect.left
      })
      setOpenModal(modalType)
    }
  }

  // 모달 닫기 함수
  const handleCloseModal = () => {
    setOpenModal(null)
    setModalPosition(null)
  }

  // 시드 입력 핸들러
  const handleSeedChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSeed(e.target.value)
  }

  // 시드 잠금/해제 토글
  const handleToggleSeedLock = () => {
    setIsSeedLocked(!isSeedLocked)
  }

  // 시드 적용 핸들러
  const handleApplySeed = () => {
    // 시드 적용 로직 (추후 구현)
    console.log('시드 적용:', seed, '잠금:', isSeedLocked)
    handleCloseModal()
  }

  // 초기값 설정 및 외부 변경 시에만 업데이트
  useEffect(() => {
    if (promptRef.current) {
      const currentText = promptRef.current.textContent || ''
      // 외부에서 변경된 경우에만 업데이트 (예: handleCreate 후 초기화)
      if (currentText !== inputText) {
        promptRef.current.textContent = inputText
      }
    }
  }, [inputText])

  // 스켈레톤이 추가되면 해당 요소로 스크롤
  useEffect(() => {
    if (isLoading && skeletonRef.current) {
      // 스켈레톤이 DOM에 추가된 후 스크롤
      setTimeout(() => {
        if (skeletonRef.current) {
          skeletonRef.current.scrollIntoView({
            behavior: 'smooth',
            block: 'end'
          })
        }
      }, 100)
    }
  }, [isLoading])

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
              <span className="brand-name" style={{cursor:"pointer"}} onClick={() => navigate('/')}>Calligraphy Studio</span>
              <button className="experiment-badge" onClick={() => navigate('/')}>EXPERIMENT</button>
            </div>
          </div>
          
          {/* <div className="header-center">
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
          </div> */}

          <div className="header-right">
            <button className="library-btn">내 라이브러리</button>
            {/* <button className="icon-btn">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
                <circle cx="10" cy="10" r="8"/>
              </svg>
            </button> */}
            <button className="icon-btn">?</button>
            <button className="profile-btn">n</button>
          </div>
        </header>

        {/* 메인 콘텐츠 영역 */}
        <main className="editor-main">
          {/* 캔버스 영역 */}
          <div className="canvas-area" ref={canvasAreaRef}>
            {works.length === 0 && !isLoading ? (
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
                    
                    {/* 좌측 상단: 3개 메뉴 */}
                    <div className="work-buttons top-left">
                      {/* <button className="work-btn" title="공유">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                          <circle cx="18" cy="5" r="3"/>
                          <circle cx="6" cy="12" r="3"/>
                          <circle cx="18" cy="19" r="3"/>
                          <line x1="8.59" y1="13.51" x2="15.42" y2="17.49"/>
                          <line x1="15.41" y1="6.51" x2="8.59" y2="10.49"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="복사">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <rect x="9" y="9" width="13" height="13" rx="2" ry="2"/>
                          <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="다운로드">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                          <polyline points="7 10 12 15 17 10"/>
                          <line x1="12" y1="15" x2="12" y2="3"/>
                        </svg>
                      </button> */}
                    </div>
                    
                    {/* 우측 상단: 3개 메뉴 */}
                    <div className="work-buttons top-right">
                      {/* <button className="work-btn" title="편집">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="설정">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <circle cx="12" cy="12" r="3"/>
                          <path d="M12 1v6m0 6v6M5.64 5.64l4.24 4.24m4.24 4.24l4.24 4.24M1 12h6m6 0h6M5.64 18.36l4.24-4.24m4.24-4.24l4.24-4.24"/>
                        </svg>
                      </button> */}
                      <button className="work-btn" title="공유">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                          <circle cx="18" cy="5" r="3"/>
                          <circle cx="6" cy="12" r="3"/>
                          <circle cx="18" cy="19" r="3"/>
                          <line x1="8.59" y1="13.51" x2="15.42" y2="17.49"/>
                          <line x1="15.41" y1="6.51" x2="8.59" y2="10.49"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="복사">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <rect x="9" y="9" width="13" height="13" rx="2" ry="2"/>
                          <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="다운로드">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                          <polyline points="7 10 12 15 17 10"/>
                          <line x1="12" y1="15" x2="12" y2="3"/>
                        </svg>
                      </button>
                    </div>
                    
                    {/* 좌측 하단: 3개 메뉴 */}
                    <div className="work-buttons bottom-left">
                      <button className="work-btn" title="피드백">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                          <path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z"/>
                          <line x1="4" y1="22" x2="4" y2="15"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="좋아요">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
                        </svg>
                      </button>
                      <button className="work-btn" title="안좋아요">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                        </svg>
                      </button>
                    </div>
                    
                    {/* 우측 하단: 1개 메뉴 */}
                    <div className="work-buttons bottom-right">
                      <button className="work-btn work-delete" onClick={() => handleDelete(work.id)} title="삭제">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                          <polyline points="3 6 5 6 21 6"/>
                          <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                        </svg>
                      </button>
                    </div>
                  </div>
                ))}
                {/* 로딩 중일 때 스켈레톤 표시 (최신 작품 아래에) */}
                {isLoading && (
                  <div className="work-item skeleton" ref={skeletonRef}>
                    <div className="skeleton-content"></div>
                  </div>
                )}
              </div>
            )}
          </div>

          {/* 하단 입력 박스 (Sticky) */}
          <div className="input-box">
            <div className="input-content">
              <div 
                ref={promptRef}
                className="prompt-text"
                contentEditable
                suppressContentEditableWarning
                onInput={handleInput}
                onKeyPress={handleKeyPress}
                data-placeholder="예: 행복한 하루 되세요"
              />
              <div className="input-actions">
                <button className="add-image-btn">
                  <span>→</span> 이미지 추가
                </button>
                <div className="input-icons">
                  <button 
                    ref={zoomBtnRef}
                    className="input-icon-btn" 
                    title="속성"
                    onClick={() => handleOpenModal('zoom', zoomBtnRef)}
                  >
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      {/* 펜/브러시 아이콘 - 캘리그래피 속성 표현 */}
                      <path d="M12 19l7-7 3 3-7 7-3-3z"/>
                      <path d="M18 13l-1.5-7.5L2 2l3.5 14.5L13 18l5-5z"/>
                      <path d="M2 2l7.586 7.586"/>
                      <circle cx="11" cy="11" r="2"/>
                    </svg>
                  </button>
                  <button 
                    ref={cropBtnRef}
                    className="input-icon-btn" 
                    title="비율 선택"
                    onClick={() => handleOpenModal('crop', cropBtnRef)}
                  >
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <rect x="2" y="2" width="20" height="20" rx="2" fill="none"/>
                      <line x1="8" y1="2" x2="8" y2="22"/>
                      <line x1="16" y1="2" x2="16" y2="22"/>
                      <line x1="2" y1="8" x2="22" y2="8"/>
                      <line x1="2" y1="16" x2="22" y2="16"/>
                      <rect x="3" y="3" width="4" height="4" rx="0.5" fill="currentColor" opacity="0.3"/>
                      <rect x="9" y="3" width="6" height="4" rx="0.5" fill="currentColor" opacity="0.3"/>
                      <rect x="3" y="9" width="4" height="6" rx="0.5" fill="currentColor" opacity="0.3"/>
                    </svg>
                  </button>
                  <button 
                    ref={settingsBtnRef}
                    className="input-icon-btn" 
                    title="설정"
                    onClick={() => handleOpenModal('settings', settingsBtnRef)}
                  >
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                      <circle cx="12" cy="6" r="1.5" fill="currentColor"/>
                      <circle cx="12" cy="12" r="1.5" fill="currentColor"/>
                      <circle cx="12" cy="18" r="1.5" fill="currentColor"/>
                      <path d="M3 6h18M3 12h18M3 18h18" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round"/>
                    </svg>
                  </button>
                </div>
                <button 
                  className="submit-btn" 
                  onClick={handleCreate} 
                  title="생성"
                  disabled={isLoading}
                >
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <path 
                      d="M5 12h14M12 5l7 7-7 7" 
                      stroke="currentColor" 
                      strokeWidth="2.5" 
                      strokeLinecap="round" 
                      strokeLinejoin="round"
                    />
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
      
      {/* 모달 */}
      {openModal && modalPosition && (
        <>
          <div className="modal-overlay" onClick={handleCloseModal}></div>
          <div 
            className={`input-modal ${openModal === 'crop' ? 'crop-modal-wrapper' : openModal === 'settings' ? 'seed-modal-wrapper' : openModal === 'zoom' ? 'style-modal-wrapper' : ''}`}
            style={{
              top: `${modalPosition.top}px`,
              left: `${modalPosition.left}px`
            }}
          >
            <div className="modal-content">
              {openModal === 'settings' && (
                <div className="seed-modal">
                  {/* <h3 className="modal-title">설정ㅎㅎ</h3> */}
                  
                  <div className="seed-input-wrapper">
                    <label className="seed-label">시드</label>
                    <div className="seed-input-container">
                      <input
                        type="text"
                        className="seed-input"
                        value={seed}
                        onChange={handleSeedChange}
                        disabled={isSeedLocked}
                        maxLength={6}
                        onKeyPress={(e) => {
                          if (e.key === 'Enter') {
                            handleApplySeed()
                          }
                        }}
                      />
                      <button 
                        className="seed-lock-btn"
                        onClick={handleToggleSeedLock}
                        title={isSeedLocked ? '잠금 해제' : '잠금'}
                      >
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                          <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                          <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                        </svg>
                      </button>
                    </div>
                    <p className="seed-description">
                      {isSeedLocked 
                        ? '시드가 잠금되어 동일한 출력이 제공됩니다.' 
                        : '시드가 잠금 해제되어 더욱 다양한 출력이 제공됩니다.'}
                    </p>
                  </div>
                </div>
              )}
              {openModal === 'crop' && (
                <div className="crop-modal">
                  <h3 className="modal-title">가로세로 비율</h3>
                  
                  <div className="ratio-options">
                    <button
                      className={`ratio-option ${selectedRatio === '1:1' ? 'active' : ''}`}
                      onClick={() => setSelectedRatio('1:1')}
                    >
                      <div className="ratio-shape ratio-square">
                        <span className="ratio-text">1:1</span>
                      </div>
                      <span className="ratio-label">정사각형</span>
                    </button>
                    
                    <button
                      className={`ratio-option ${selectedRatio === '9:16' ? 'active' : ''}`}
                      onClick={() => setSelectedRatio('9:16')}
                    >
                      <div className="ratio-shape ratio-portrait">
                        <span className="ratio-text">9:16</span>
                      </div>
                      <span className="ratio-label">세로 모드</span>
                    </button>
                    
                    <button
                      className={`ratio-option ${selectedRatio === '16:9' ? 'active' : ''}`}
                      onClick={() => setSelectedRatio('16:9')}
                    >
                      <div className="ratio-shape ratio-landscape">
                        <span className="ratio-text">16:9</span>
                      </div>
                      <span className="ratio-label">가로 모드</span>
                    </button>
                  </div>
                </div>
              )}
              {openModal === 'zoom' && (
                <div className="style-modal">
                  <h3 className="modal-title">속성 선택</h3>
                  
                  <div className="style-options">
                  <button
                      className={`style-option ${selectedStyle === 'None' ? 'active' : ''}`}
                      onClick={() => setSelectedStyle('None')}
                    >
                      <span className="style-name">None</span>
                    </button>
                    <button
                      className={`style-option ${selectedStyle === '클래식' ? 'active' : ''}`}
                      onClick={() => setSelectedStyle('클래식')}
                    >
                      <span className="style-name">클래식</span>
                    </button>
                    
                    <button
                      className={`style-option ${selectedStyle === '모던' ? 'active' : ''}`}
                      onClick={() => setSelectedStyle('모던')}
                    >
                      <span className="style-name">모던</span>
                    </button>
                    
                    <button
                      className={`style-option ${selectedStyle === '엘레강트' ? 'active' : ''}`}
                      onClick={() => setSelectedStyle('엘레강트')}
                    >
                      <span className="style-name">엘레강트</span>
                    </button>
                    
                    <button
                      className={`style-option ${selectedStyle === '손글씨' ? 'active' : ''}`}
                      onClick={() => setSelectedStyle('손글씨')}
                    >
                      <span className="style-name">손글씨</span>
                    </button>
                  </div>
                </div>
              )}
            </div>
          </div>
        </>
      )}
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
