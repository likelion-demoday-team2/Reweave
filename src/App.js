import { Routes, Route, Navigate } from 'react-router-dom';
import BottomNav from './components/BottomNav/BottomNav.jsx';

// 아직 페이지가 없으니 임시 페이지로 연결 확인용
const Home = () => <div className="page">홈</div>;
const Bookmark = () => <div className="page">보관함</div>;
const Project = () => <div className="page">프로젝트</div>;
const Mypage = () => <div className="page">마이</div>;
const Add = () => <div className="page">추가 (준비 중)</div>;

function App() {
  return (
    <div className="App">
      <main className="app-content">
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />} />
          <Route path="/home" element={<Home />} />
          <Route path="/bookmark" element={<Bookmark />} />
          <Route path="/project" element={<Project />} />
          <Route path="/mypage" element={<Mypage />} />
          <Route path="/add" element={<Add />} />
        </Routes>
      </main>
      <BottomNav />
    </div>
  );
}

export default App;