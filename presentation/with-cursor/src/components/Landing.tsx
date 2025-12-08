import './Landing.css'

interface LandingProps {
  onStart: () => void
}

// 히어로 영역에 배치될 카드들 (Whisk 스타일로 기울어진 카드)
const heroCards = [
  { id: 1, label: '생동감 있는 잉크 스트로크', rotate: -6 },
  { id: 2, label: '우아한 서체로 변환', rotate: 5 },
  { id: 3, label: '따뜻한 손글씨 질감', rotate: -3 },
  { id: 4, label: '특별한 문구를 원한다면', rotate: 4 },
]

function Landing({ onStart }: LandingProps) {
  return (
    <div className="landing">
      <header className="landing-header">
        <div className="brand">
          {/* <span className="brand-icon">🚀</span> */}
          <span className="brand-text">Calligraphy Studio</span>
          <span className="brand-badge">EXPERIMENT</span>
        </div>
        <div className="header-actions">
          <button className="ghost-btn">갤러리</button>
          <button className="ghost-btn">문의</button>
          <button className="ghost-btn">로그인</button>
          <button className="ghost-btn">?</button>
        </div>
      </header>

      <main className="landing-hero">
        <div className="hero-content">
          {/* <div className="hero-icon">🖋️</div> */}
          <h1 className="hero-title">지금 바로 캘리그래피를 만들어보세요</h1>
          <p className="hero-subtitle">문구를 입력하면 곧바로 감성적인 캘리그래피로 변환됩니다.</p>
          <div className="cta-row">
            <button className="cta-btn" onClick={onStart}>
              도구 열기 →
            </button>
          </div>
        </div>

        <div className="hero-cards">
          {heroCards.map((card) => (
            <div
              key={card.id}
              className="hero-card"
              style={{ transform: `rotate(${card.rotate}deg)` }}
            >
              <span className="hero-card-label">Ai x 캘리그래피</span>
              <p className="hero-card-text">{card.label}</p>
            </div>
          ))}
        </div>
      </main>
    </div>
  )
}

export default Landing
