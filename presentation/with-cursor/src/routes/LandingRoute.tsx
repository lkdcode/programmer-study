import { useNavigate } from 'react-router-dom'
import Landing from '../components/Landing'

function LandingRoute() {
  const navigate = useNavigate()
  return <Landing onStart={() => navigate('/project')} />
}

export default LandingRoute

