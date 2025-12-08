import { useState } from 'react'
import './Sidebar.css'

// Sidebar 컴포넌트: 왼쪽 사이드 메뉴
function Sidebar() {
  // 현재 선택된 메뉴 항목을 추적하는 상태
  const [activeMenu, setActiveMenu] = useState('create')

  // 메뉴 항목들
  const menuItems = [
    { id: 'create', label: '캘리그래피 만들기', icon: '✍️' },
    { id: 'gallery', label: '내 작품', icon: '🖼️' },
    { id: 'templates', label: '템플릿', icon: '📝' },
    { id: 'settings', label: '설정', icon: '⚙️' },
  ]

  return (
    <aside className="sidebar">
      <nav className="sidebar-nav">
        {menuItems.map((item) => (
          <button
            key={item.id}
            className={`sidebar-item ${activeMenu === item.id ? 'active' : ''}`}
            onClick={() => setActiveMenu(item.id)}
          >
            <span className="sidebar-icon">{item.icon}</span>
            <span className="sidebar-label">{item.label}</span>
          </button>
        ))}
      </nav>
    </aside>
  )
}

export default Sidebar

