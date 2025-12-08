import './Header.css'

// Header 컴포넌트: 사이트 상단의 헤더 영역
function Header() {
  return (
    <header className="header">
      <div className="header-content">
        {/* 로고 영역 */}
        <div className="logo">
          <h1>Calligraphy Studio</h1>
        </div>
        
        {/* 네비게이션 메뉴 */}
        <nav className="header-nav">
          <a href="#" className="nav-link">홈</a>
          <a href="#" className="nav-link">갤러리</a>
          <a href="#" className="nav-link">도움말</a>
        </nav>
      </div>
    </header>
  )
}

export default Header

