import './CommunityCard.css'

// CommunityCard 컴포넌트: 다른 사람들이 작성한 캘리그래피를 보여주는 카드
interface CommunityCardProps {
  id: number
  text: string
  style: string
  author: string // 작성자 이름
  authorAvatar?: string // 작성자 아바타 (선택적)
  likes?: number // 좋아요 수 (선택적)
}

function CommunityCard({ text, style, author, authorAvatar, likes = 0 }: CommunityCardProps) {
  return (
    <div className="community-card">
      {/* 작성자 정보 */}
      <div className="card-author">
        <div className="author-avatar">
          {authorAvatar ? (
            <img src={authorAvatar} alt={author} />
          ) : (
            <span>{author.charAt(0)}</span>
          )}
        </div>
        <div className="author-info">
          <span className="author-name">{author}</span>
          <span className="card-style">{style}</span>
        </div>
      </div>
      
      {/* 캘리그래피 텍스트 */}
      <div className="card-content">
        <p className="calligraphy-text" style={{ fontFamily: getFontFamily(style) }}>
          {text}
        </p>
      </div>
      
      {/* 카드 액션 (좋아요, 공유 등) */}
      <div className="card-actions">
        <button className="action-btn like-btn">
          <span className="action-icon">❤️</span>
          <span className="action-count">{likes}</span>
        </button>
        <button className="action-btn share-btn">
          <span className="action-icon">📤</span>
          <span>공유</span>
        </button>
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

export default CommunityCard

