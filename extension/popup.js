// 확장 프로그램 밖(파일로 직접 열기)에서도 미리볼 수 있게 chrome API 대체
const hasChrome = typeof chrome !== 'undefined' && chrome.tabs && chrome.storage;
const ext = hasChrome ? chrome : {
  tabs: { query: (_q, cb) => cb([{ url: location.href }]), create: ({ url }) => window.open(url) },
  storage: { local: { get: (_k, cb) => cb({}), set: () => {} } },
};

// 화면 전환 헬퍼
function showScreen(id) {
  document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
  document.getElementById(id).classList.add('active');
}

// 팝업 닫기 버튼 전부
document.querySelectorAll('[data-close]').forEach(btn => {
  btn.addEventListener('click', () => window.close());
});

// 현재 탭 URL 가져오기 (link save 화면용)
ext.tabs.query({ active: true, currentWindow: true }, (tabs) => {
  const urlBox = document.getElementById('current-url');
  if (tabs[0] && urlBox) {
    urlBox.textContent = tabs[0].url;
  }
});

// TODO: 실제로는 저장된 토큰 유무로 로그인 여부 판단해야 함 (지금은 임시로 바로 저장 화면부터 시작)
ext.storage.local.get(['isLoggedIn'], (result) => {
  if (result.isLoggedIn) {
    showScreen('screen-save');
  }
});

// 로그인 버튼 (TODO: 실제 백엔드 로그인 API 연동 필요)
document.getElementById('btn-login').addEventListener('click', () => {
  // 임시: 로그인 성공했다고 가정
  ext.storage.local.set({ isLoggedIn: true });
  showScreen('screen-save');
});

// 회원가입 -> 웹뷰(새 탭)로 이동
document.getElementById('btn-goto-signup').addEventListener('click', () => {
  ext.tabs.create({ url: 'https://reweave.app/signup' });
});

// 카테고리 칩 선택
document.querySelectorAll('.chip').forEach(chip => {
  chip.addEventListener('click', () => {
    document.querySelectorAll('.chip').forEach(c => c.classList.remove('chip-selected'));
    chip.classList.add('chip-selected');
  });
});

// '+' 버튼 -> 카테고리 생성 화면으로
document.getElementById('btn-add-category').addEventListener('click', () => {
  showScreen('screen-category');
});

// 카테고리 생성 화면 뒤로가기
document.getElementById('btn-back-to-save').addEventListener('click', () => {
  showScreen('screen-save');
});

// 카테고리 만들기 (TODO: 백엔드 API로 카테고리 생성 요청 필요)
document.getElementById('btn-create-category').addEventListener('click', () => {
  const title = document.getElementById('category-title').value;
  if (!title.trim()) return;

  const chipRow = document.getElementById('category-chips');
  const newChip = document.createElement('button');
  newChip.className = 'chip chip-selected';
  newChip.dataset.category = title;
  newChip.textContent = title;
  document.querySelectorAll('.chip').forEach(c => c.classList.remove('chip-selected'));
  chipRow.appendChild(newChip);

  showScreen('screen-save');
});

// 저장하기 (TODO: 백엔드 저장 API 연동 필요 - URL, 카테고리, 메모 전송)
document.getElementById('btn-save').addEventListener('click', () => {
  // const url = document.getElementById('current-url').textContent;
  // const category = document.querySelector('.chip-selected')?.dataset.category;
  // const memo = document.getElementById('memo-input').value;
  // -> 여기서 fetch()로 백엔드 API 호출 예정

  showScreen('screen-complete');

  // 3초 후 자동 종료 (디자인 명시사항 반영)
  setTimeout(() => window.close(), 3000);
});

// 저장완료 화면의 '보러가기' -> 웹 대시보드로 이동
document.getElementById('btn-complete-action').addEventListener('click', () => {
  ext.tabs.create({ url: 'https://reweave.app/dashboard' });
});

// 미리보기용: popup.html#save / #category / #complete 로 해당 화면 바로 열기
const previewScreen = location.hash.slice(1);
if (previewScreen && document.getElementById('screen-' + previewScreen)) {
  showScreen('screen-' + previewScreen);
}
