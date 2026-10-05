import React, { useState } from 'react';
import './SaveLinkModal.scss';
import plusIcon from '../../assets/plus.svg';

const SaveLinkModal = ({ isOpen, onClose, onSave }) => {
  const [url, setUrl] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('미분류');
  const [memo, setMemo] = useState('');

  const categories = ['미분류', '취업', '디자인', '여행', '쇼핑'];

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!url.trim()) return;

    onSave({
      url,
      category: selectedCategory,
      memo,
    });

    setUrl('');
    setMemo('');
    setSelectedCategory('미분류');
    onClose();
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <h2 className="modal-title">링크 저장</h2>

        <form onSubmit={handleSubmit}>
    
          <div className="form-group">
            <input
              type="text"
              className="modal-input"
              placeholder="링크 주소를 입력해주세요"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
            />
          </div>


          <div className="form-group">
            <div className="section-header">
              <label className="form-label">카테고리</label>
              <button type="button" className="category-add-btn">
                <img src={plusIcon} alt="카테고리 추가" />
              </button>
            </div>

            <div className="category-list">
              {categories.map((cat) => (
                <button
                  key={cat}
                  type="button"
                  className={`category-chip ${
                    selectedCategory === cat ? 'active' : ''
                  }`}
                  onClick={() => setSelectedCategory(cat)}
                >
                  {cat}
                </button>
              ))}
            </div>
          </div>


          <div className="form-group">
            <label className="form-label">메모</label>
            <textarea
              className="modal-textarea"
              placeholder="간단한 메모를 입력해주세요"
              value={memo}
              onChange={(e) => setMemo(e.target.value)}
            />
          </div>


          <button type="submit" className="save-submit-btn">
            저장하기
          </button>
        </form>
      </div>
    </div>
  );
};

export default SaveLinkModal;