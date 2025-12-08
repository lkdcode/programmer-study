import './CalligraphyCard.css'

// CalligraphyCard 컴포넌트: 캘리그래피 결과를 보여주는 카드
interface CalligraphyCardProps {
  id: number
  text: string
  style: string // 캘리그래피 스타일 이름
  onDelete?: (id: number) => void // 삭제 함수 (선택적)
}

function CalligraphyCard({ id, text, style, onDelete }: CalligraphyCardProps) {
  return (
    <div className="calligraphy-card">
      {/* 카드 헤더 */}
      <div className="card-header">
        <span className="card-style-badge">{style}</span>
        {onDelete && (
          <button 
            className="card-delete-btn"
            onClick={() => onDelete(id)}
            aria-label="삭제"
          >
            ✕
          </button>
        )}
      </div>
      
      {/* 캘리그래피 텍스트 */}
      <div className="card-content">
        <p className="calligraphy-text" style={{ fontFamily: getFontFamily(style) }}>
          {text}
        </p>
      </div>
      
      {/* 카드 푸터 (액션 버튼들) */}
      <div className="card-footer">
        <button className="card-action-btn">다운로드</button>
        <button className="card-action-btn">공유</button>
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

export default CalligraphyCard

