import navHome from '../../assets/nav/navHome.svg';
import navHomeActive from '../../assets/nav/navHomeActive.svg';
import navBookmark from '../../assets/nav/navBookmark.svg';
import navBookmarkActive from '../../assets/nav/navBookmarkActive.svg';
import navProject from '../../assets/nav/navProject.svg'; 
import navProjectActive from '../../assets/nav/navProjectActive.svg';
import navMypage from '../../assets/nav/navMypage.svg';
import navMypageActive from '../../assets/nav/navMypageActive.svg';
import navAdd from '../../assets/nav/plus.svg';

export const addIcon = navAdd;
export const addPath = '/add';

export const navItems = [
  { to: '/home', label: '홈', icon: navHome, activeIcon: navHomeActive },
  { to: '/bookmark', label: '보관함', icon: navBookmark, activeIcon: navBookmarkActive },
  { to: '/project', label: '프로젝트', icon: navProject, activeIcon: navProjectActive },
  { to: '/mypage', label: '마이', icon: navMypage, activeIcon: navMypageActive },
];