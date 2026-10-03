import { NavLink, Link } from 'react-router-dom';
import { navItems, addPath, addIcon } from './navConfig.js';
import './BottomNav.scss';

function NavItem({ item }) {
  return (
    <NavLink
      to={item.to}
      className={({ isActive }) =>
        `bottom-nav-item${isActive ? ' bottom-nav-item-active' : ''}`
      }
    >
      {({ isActive }) => (
        <>
          <img
            className="bottom-nav-icon"
            src={isActive ? item.activeIcon : item.icon}
            alt={item.label}
          />
          <span className="bottom-nav-label">{item.label}</span>
        </>
      )}
    </NavLink>
  );
}

function BottomNav() {
  const left = navItems.slice(0, 2);
  const right = navItems.slice(2);

  return (
    <nav className="bottom-nav">
      {left.map((item) => (
        <NavItem key={item.to} item={item} />
      ))}

      <Link to={addPath} className="bottom-nav-add">
        <img src={addIcon} alt="" className="bottom-nav-add-icon" />
      </Link>

      {right.map((item) => (
        <NavItem key={item.to} item={item} />
      ))}
    </nav>
  );
}

export default BottomNav;