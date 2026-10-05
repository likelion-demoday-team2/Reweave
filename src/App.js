import { useState } from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Login from './pages/Login/Login.jsx';
import Signup from './pages/Signup/Signup.jsx';
import Home from './pages/Home/Home.jsx';
import BottomNav from './components/BottomNav/BottomNav.jsx';
import SaveLinkModal from './components/SaveLinkModal/SaveLinkModal.jsx';
import './App.scss';

// 임시 페이지들
const Bookmark = () => <div className="page">보관함</div>;
const Project = () => <div className="page">프로젝트</div>;
const Mypage = () => <div className="page">마이</div>;

function App() {
  const location = useLocation();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const hideBottomNavPaths = ['/login', '/signup'];
  const showBottomNav = !hideBottomNavPaths.includes(location.pathname);

  const handleSaveLink = (linkData) => {
    console.log('저장된 링크 데이터:', linkData);
    // TODO: 저장 API 연동 또는 상태 업데이트
  };

  return (
    <div className="App">
      <main className="app-content">
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/home" element={<Home />} />
          <Route path="/bookmark" element={<Bookmark />} />
          <Route path="/project" element={<Project />} />
          <Route path="/mypage" element={<Mypage />} />
        </Routes>
      </main>

      {/* 하단 바의 + 버튼 클릭 시 모달 열기 */}
      {showBottomNav && (
        <BottomNav onAddClick={() => setIsModalOpen(true)} />
      )}

      {/* 전역 링크 저장 모달 */}
      <SaveLinkModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveLink}
      />
    </div>
  );
}

export default App;