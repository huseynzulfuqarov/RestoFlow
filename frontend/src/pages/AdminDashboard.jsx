import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Settings, TrendingUp, AlertTriangle } from 'lucide-react';

const AdminDashboard = () => {
    const [orders, setOrders] = useState([]);
    const [settings, setSettings] = useState({ laborCostPerMinute: 0, utilityCostPerMinute: 0, maxConcurrentOrders: 0 });
    const [predictions, setPredictions] = useState([]);

    useEffect(() => {
        fetchOrders();
        fetchSettings();
        fetchPredictions();
        const interval = setInterval(fetchOrders, 5000);
        return () => clearInterval(interval);
    }, []);

    const fetchOrders = async () => {
        try {
            const res = await axios.get('/api/admin/orders');
            setOrders(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchSettings = async () => {
        try {
            const res = await axios.get('/api/admin/settings');
            setSettings(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchPredictions = async () => {
        try {
            const res = await axios.get('/api/admin/restock-predictions');
            setPredictions(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const updateStatus = async (id, status) => {
        try {
            await axios.put(`/api/admin/orders/${id}/status?status=${status}`);
            fetchOrders();
        } catch (err) {
            console.error(err);
        }
    };

    const handleSettingsUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.put('/api/admin/settings', settings);
            alert('Settings updated! Menu prices will adjust automatically.');
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div>
            <h1>Kitchen Dashboard</h1>

            <div style={{ display: 'flex', gap: '20px', marginBottom: '30px', flexWrap: 'wrap' }}>
                {/* Operational Settings */}
                <div className="glass-panel" style={{ flex: 1, minWidth: '300px' }}>
                    <h3><Settings size={18} /> Operational Settings</h3>
                    <form onSubmit={handleSettingsUpdate} style={{ marginTop: '15px' }}>
                        <div style={{ marginBottom: '10px' }}>
                            <label style={{ display: 'block', fontSize: '0.8rem', marginBottom: '5px' }}>Labor Cost ($/min)</label>
                            <input
                                type="number" step="0.01" className="glass-input"
                                value={settings.laborCostPerMinute}
                                onChange={e => setSettings({ ...settings, laborCostPerMinute: parseFloat(e.target.value) })}
                            />
                        </div>
                        <div style={{ marginBottom: '10px' }}>
                            <label style={{ display: 'block', fontSize: '0.8rem', marginBottom: '5px' }}>Utility Cost ($/min)</label>
                            <input
                                type="number" step="0.01" className="glass-input"
                                value={settings.utilityCostPerMinute}
                                onChange={e => setSettings({ ...settings, utilityCostPerMinute: parseFloat(e.target.value) })}
                            />
                        </div>
                        <div style={{ marginBottom: '15px' }}>
                            <label style={{ display: 'block', fontSize: '0.8rem', marginBottom: '5px' }}>Max Concurrent Orders</label>
                            <input
                                type="number" className="glass-input"
                                value={settings.maxConcurrentOrders}
                                onChange={e => setSettings({ ...settings, maxConcurrentOrders: parseInt(e.target.value) })}
                            />
                        </div>
                        <button type="submit" className="glass-btn" style={{ width: '100%' }}>Update Settings</button>
                    </form>
                </div>

                {/* Smart Restock */}
                <div className="glass-panel" style={{ flex: 1, minWidth: '300px' }}>
                    <h3><TrendingUp size={18} /> Smart Restock Predictions</h3>
                    <p style={{ fontSize: '0.8rem', opacity: 0.7 }}>Based on current stock levels</p>
                    <ul style={{ listStyle: 'none', padding: 0, marginTop: '15px' }}>
                        {predictions.length === 0 && <li style={{ opacity: 0.5 }}>No immediate restock needed.</li>}
                        {predictions.map(item => (
                            <li key={item.id} style={{ display: 'flex', justifyContent: 'space-between', padding: '8px 0', borderBottom: '1px solid var(--glass-border)' }}>
                                <span>{item.name}</span>
                                <span style={{ color: '#ff4444' }}>{item.quantity} {item.unit} left</span>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <h3>Active Orders</h3>
            <div className="grid">
                {orders.map(order => (
                    <div key={order.id} className="glass-panel">
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <h3>#{order.id}</h3>
                            <span style={{
                                padding: '5px 10px',
                                borderRadius: '10px',
                                background: order.status === 'PENDING' ? 'orange' : order.status === 'READY' ? 'green' : 'gray',
                                fontSize: '0.8rem',
                                color: 'white'
                            }}>
                                {order.status}
                            </span>
                        </div>
                        <p style={{ fontSize: '0.9rem', opacity: 0.7 }}>
                            {new Date(order.orderDate).toLocaleTimeString()}
                        </p>

                        <div style={{ margin: '15px 0', borderTop: '1px solid var(--glass-border)', paddingTop: '10px' }}>
                            {order.items.map(item => (
                                <div key={item.id} style={{ marginBottom: '5px' }}>
                                    {item.quantity}x {item.product.name}
                                </div>
                            ))}
                        </div>

                        <div style={{ display: 'flex', gap: '10px', marginTop: '15px' }}>
                            {order.status === 'PENDING' && (
                                <button className="glass-btn" onClick={() => updateStatus(order.id, 'PREPARING')}>Start Cooking</button>
                            )}
                            {order.status === 'PREPARING' && (
                                <button className="glass-btn" onClick={() => updateStatus(order.id, 'READY')}>Mark Ready</button>
                            )}
                            {order.status === 'READY' && (
                                <button className="glass-btn" onClick={() => updateStatus(order.id, 'DELIVERED')}>Deliver</button>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AdminDashboard;
