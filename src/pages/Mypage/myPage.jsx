import React, { useState } from 'react';
import './mypage.scss';
import arrow from '../../assets/mypage/arrow.svg';

function MyPage() {
    const [isLogoutOpen, setIsLogoutOpen] = useState(false);

    const handleLogout = () => {
        // 여기에 실제 로그아웃 처리 (토큰 삭제, 페이지 이동 등)
        setIsLogoutOpen(false);
    };

    return (
        <div className="mypage-wrap">
            <div className="mypage-title">
                <h1>마이페이지</h1>
            </div>
            <h1 className="mypage-nickname">닉네임</h1>

            <div className="info-card">
                <div className="info-row">
                    <span className="info-label">이메일</span>
                    <span className="info-value">20240906@sungshin.ac.kr</span>
                </div>

                <div className="mypage-divider" />

                <div className="info-row">
                    <span className="info-label">로그아웃</span>
                    <button className="info-arrow" onClick={() => setIsLogoutOpen(true)}>
                        <img src={arrow} alt="로그아웃" />
                    </button>
                </div>
            </div>

            {isLogoutOpen && (
                <div className="modal-overlay" onClick={() => setIsLogoutOpen(false)}>
                    <div className="modal" onClick={(e) => e.stopPropagation()}>
                        <p className="modal-text">로그아웃 하시겠습니까?</p>
                        <div className="modal-buttons">
                            <button className="modal-cancel" onClick={() => setIsLogoutOpen(false)}>
                                취소
                            </button>
                            <button className="modal-confirm" onClick={handleLogout}>
                                로그아웃
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default MyPage;