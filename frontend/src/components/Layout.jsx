import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';
import {
    LayoutDashboard,
    UtensilsCrossed,
    History,
    Package,
    Settings,
    LogOut,
    Sun,
    Moon
} from 'lucide-react';

const Layout = ({ children }) => {
    const { user, logout } = useAuth();
    const { theme, toggleTheme } = useTheme();
    const location = useLocation();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const isActive = (path) => location.pathname === path ? 'active' : '';

    return (
        <div className="layout">
            <aside className="sidebar">
                <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--primary-color)', marginBottom: '40px', paddingLeft: '10px' }}>
                    RestoFlow
                </div>

                <nav style={{ flex: 1 }}>
                    <Link to="/" className={`nav-item ${isActive('/')}`}>
                        <UtensilsCrossed size={20} /> Menu
                    </Link>

                    <Link to="/history" className={`nav-item ${isActive('/history')}`}>
                        <History size={20} /> My Orders
                    </Link>

                    {user?.role === 'RESTAURANT_ADMIN' && (
                        <Link to="/admin" className={`nav-item ${isActive('/admin')}`}>
                            <LayoutDashboard size={20} /> Kitchen Dashboard
                        </Link>
                    )}

                    {user?.role === 'INVENTORY_ADMIN' && (
                        <Link to="/inventory" className={`nav-item ${isActive('/inventory')}`}>
                            <Package size={20} /> Inventory
                        </Link>
                    )}
                </nav>

                <div style={{ borderTop: '1px solid var(--glass-border)', paddingTop: '20px' }}>
                    <button onClick={toggleTheme} className="nav-item" style={{ background: 'none', border: 'none', width: '100%', cursor: 'pointer' }}>
                        {theme === 'dark' ? <Sun size={20} /> : <Moon size={20} />}
                        {theme === 'dark' ? ' Light Mode' : ' Dark Mode'}
                    </button>

                    <button onClick={handleLogout} className="nav-item" style={{ background: 'none', border: 'none', width: '100%', cursor: 'pointer', color: '#ff4444' }}>
                        <LogOut size={20} /> Logout
                    </button>
                </div>
            </aside>

            <main className="main-content">
                {children}
            </main>
        </div>
    );
};

export default Layout;
