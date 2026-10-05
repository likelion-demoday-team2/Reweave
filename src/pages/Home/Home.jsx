import React, { useEffect, useRef, useState } from 'react';
import './Home.scss';

import BottomNav from '../../components/BottomNav/BottomNav';
import SaveLinkModal from '../../components/SaveLinkModal/SaveLinkModal';

// Assets
import logoIcon from '../../assets/logo.svg';
import arrowRightIcon from '../../assets/arrowbotton.svg';
import defaultIcon from '../../assets/Icon.svg';

const Home = ({ nickname = '사용자' }) => {
  const sliderRef = useRef(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const todayLinks = [
    {
      id: 1,
      tag: '일본어 공부',
      title: '일반인도 따라하는\n일본어 회화 기초',
    },
    {
      id: 2,
      tag: 'UIUX 포트폴리오 만들기',
      title: '합격하는 UI·UX\n포트폴리오 프로젝트 구성법',
    },
    {
      id: 3,
      tag: '프론트엔드 개발',
      title: '개발자를 위한\nReact 성능 최적화 팁',
    },
  ];

  const projects = [
    { id: 1, title: '일본 여행 계획', tags: ['#여행', '#쇼핑', '#맛집'] },
    { id: 2, title: 'UIUX 포트폴리오 만...', tags: ['#취업', '#디자인'] },
  ];

  const recentLinks = [
    {
      id: 1,
      title: '삐삐 | 하루 한 번, 가족에게 보내는 생존신고\n서비스 Family Widget Service',
      tag: '#디자인',
      imageUrl: null,
    },
    {
      id: 2,
      title: '삐삐 | 하루 한 번, 가족에게 보내는 생존신고\n서비스 Family Widget Service',
      tag: '#디자인',
      imageUrl: null,
    },
  ];

  useEffect(() => {
    if (sliderRef.current) {
      const container = sliderRef.current;
      const cards = container.querySelectorAll('.today-card');
      if (cards.length >= 2) {
        const secondCard = cards[1];
        const scrollPosition =
          secondCard.offsetLeft - container.clientWidth / 2 + secondCard.clientWidth / 2;
        container.scrollLeft = scrollPosition;
      }
    }
  }, []);

  const handleSaveLink = (linkData) => {
    console.log('저장된 링크 데이터:', linkData);
  };

  return (
    <div className="home-container">
      <header className="home-header">
        <img src={logoIcon} alt="Reweave" className="logo" />
      </header>

      <main className="home-content">

        <section className="section today-section">
          <div className="section-title">
            <h2>{nickname}님을 위한</h2>
            <h3>오늘의 링크 다시보기</h3>
          </div>

          <div className="card-slider" ref={sliderRef}>
            {todayLinks.map((item) => (
              <div key={item.id} className="today-card">
                <div className="card-info">
                  <h4>{item.title}</h4>
                  <span className="card-tag">{item.tag}</span>
                </div>
              </div>
            ))}
          </div>
        </section>


        <section className="section project-section">
          <div className="section-header">
            <h2>진행 중인 프로젝트</h2>
          </div>
          <div className="project-grid">
            {projects.map((item) => (
              <div key={item.id} className="project-card">
                <div className="project-top">
                  <span className="project-title">{item.title}</span>
                  <img src={arrowRightIcon} alt="이동" className="arrow-icon" />
                </div>
                <div className="project-tags">
                  {item.tags.map((tag, idx) => (
                    <span key={idx} className="tag-chip">
                      {tag}
                    </span>
                  ))}
                </div>
              </div>
            ))}
          </div>
        </section>


        <section className="section recent-section">
          <div className="section-header">
            <h2>최근에 저장한 링크</h2>
          </div>
          <div className="recent-list">
            {recentLinks.map((item) => (
              <div key={item.id} className="recent-card">
                <div className="recent-thumb">
                  <img
                    src={item.imageUrl || defaultIcon}
                    alt="미리보기"
                    className={item.imageUrl ? 'thumb-img' : 'icon-img'}
                  />
                </div>
                <div className="recent-info">
                  <p className="recent-title">{item.title}</p>
                  <span className="recent-tag">{item.tag}</span>
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>


      <BottomNav onAddClick={() => setIsModalOpen(true)} />


      <SaveLinkModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveLink}
      />
    </div>
  );
};

export default Home;