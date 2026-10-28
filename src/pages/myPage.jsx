import React from 'react'
import '../assets/mypage/mypage.scss';
import arrow from '../assets/mypage/arrow.svg';

function myPage() {
    return (
        <div className="mypage-wrap">
            <div className="mypage-statusBar"></div>
            <div className="mypage-title">
                <h1>마이페이지</h1>
            </div>
            <h1 className='mypage-nickname'>닉네임</h1>
            <div className="info-card">
                <div className="info-row">
                    <span className="info-label">이메일</span>
                    <span className="info-value">20240906@sungshin.ac.kr</span>
                </div>

                <div className="mypage-divider" />

                <div className="info-row">
                    <span className="info-label">로그아웃</span>
                    <button className='info-arrow'>{arrow}</button>
                </div>

            </div>
        </div>
    )
}

export default myPage
