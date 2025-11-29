import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    if (!user) return null;

    return (
        <nav className="navbar glass-panel" style={{ borderRadius: '0 0 16px 16px', marginTop: 0 }}>
            <div className="logo" style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--primary-color)' }}>
                RestoFlow
            </div>
            <div className="nav-links">
                <Link to="/">Menu</Link>
                {user.role === 'RESTAURANT_ADMIN' && <Link to="/admin">Admin</Link>}
                {user.role === 'INVENTORY_ADMIN' && <Link to="/inventory">Inventory</Link>}
                <button onClick={handleLogout} className="glass-btn" style={{ marginLeft: '20px', padding: '5px 15px', fontSize: '0.8rem' }}>
                    Logout
                </button>
            </div>
        </nav>
    );
};

export default Navbar;
