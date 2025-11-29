import React, { useState, useEffect } from 'react';
import axios from 'axios';

const InventoryDashboard = () => {
    const [inventory, setInventory] = useState([]);
    const [alerts, setAlerts] = useState([]);
    const [restockForm, setRestockForm] = useState({ id: '', quantity: '', costPerUnit: '' });

    useEffect(() => {
        fetchInventory();
        fetchAlerts();
    }, []);

    const fetchInventory = async () => {
        try {
            const res = await axios.get('/api/inventory');
            setInventory(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchAlerts = async () => {
        try {
            const res = await axios.get('/api/inventory/alerts');
            setAlerts(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleRestock = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`/api/inventory/restock?id=${restockForm.id}&quantity=${restockForm.quantity}&costPerUnit=${restockForm.costPerUnit}`);
            fetchInventory();
            fetchAlerts();
            setRestockForm({ id: '', quantity: '', costPerUnit: '' });
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div>
            <h1>Inventory Management</h1>

            {alerts.length > 0 && (
                <div className="glass-panel" style={{ marginBottom: '30px', border: '1px solid #ff4444' }}>
                    <h3 style={{ color: '#ff4444' }}>⚠️ Low Stock Alerts</h3>
                    <ul>
                        {alerts.map(item => (
                            <li key={item.id}>{item.name} is low ({item.quantity} {item.unit} remaining)</li>
                        ))}
                    </ul>
                </div>
            )}

            <div style={{ display: 'flex', gap: '30px', flexWrap: 'wrap' }}>
                <div style={{ flex: 2 }}>
                    <div className="glass-panel">
                        <h3>Current Stock</h3>
                        <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '15px' }}>
                            <thead>
                                <tr style={{ textAlign: 'left', borderBottom: '1px solid rgba(255,255,255,0.2)' }}>
                                    <th style={{ padding: '10px' }}>ID</th>
                                    <th style={{ padding: '10px' }}>Item</th>
                                    <th style={{ padding: '10px' }}>Quantity</th>
                                    <th style={{ padding: '10px' }}>Unit</th>
                                    <th style={{ padding: '10px' }}>Cost/Unit</th>
                                </tr>
                            </thead>
                            <tbody>
                                {inventory.map(item => (
                                    <tr key={item.id} style={{ borderBottom: '1px solid rgba(255,255,255,0.05)' }}>
                                        <td style={{ padding: '10px' }}>{item.id}</td>
                                        <td style={{ padding: '10px' }}>{item.name}</td>
                                        <td style={{ padding: '10px', color: item.quantity < 10 ? '#ff4444' : 'white' }}>{item.quantity}</td>
                                        <td style={{ padding: '10px' }}>{item.unit}</td>
                                        <td style={{ padding: '10px' }}>${item.costPerUnit}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div style={{ flex: 1, minWidth: '300px' }}>
                    <div className="glass-panel">
                        <h3>Restock Item</h3>
                        <form onSubmit={handleRestock} style={{ marginTop: '20px' }}>
                            <div style={{ marginBottom: '15px' }}>
                                <label style={{ display: 'block', marginBottom: '5px' }}>Item ID</label>
                                <input
                                    type="number"
                                    className="glass-input"
                                    value={restockForm.id}
                                    onChange={e => setRestockForm({ ...restockForm, id: e.target.value })}
                                    required
                                />
                            </div>
                            <div style={{ marginBottom: '15px' }}>
                                <label style={{ display: 'block', marginBottom: '5px' }}>Quantity to Add</label>
                                <input
                                    type="number"
                                    className="glass-input"
                                    value={restockForm.quantity}
                                    onChange={e => setRestockForm({ ...restockForm, quantity: e.target.value })}
                                    required
                                />
                            </div>
                            <div style={{ marginBottom: '20px' }}>
                                <label style={{ display: 'block', marginBottom: '5px' }}>New Cost Per Unit</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className="glass-input"
                                    value={restockForm.costPerUnit}
                                    onChange={e => setRestockForm({ ...restockForm, costPerUnit: e.target.value })}
                                    required
                                />
                            </div>
                            <button type="submit" className="glass-btn" style={{ width: '100%' }}>Restock</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default InventoryDashboard;
