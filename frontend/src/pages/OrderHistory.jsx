import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const OrderHistory = () => {
    const [orders, setOrders] = useState([]);
    const { user } = useAuth();

    useEffect(() => {
        fetchHistory();
    }, []);

    const fetchHistory = async () => {
        try {
            // Pass username in header as per backend requirement
            const res = await axios.get('/api/orders/my-orders', {
                headers: { 'X-User-Name': user.username }
            });
            setOrders(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div>
            <h1>My Order History</h1>
            <div className="grid">
                {orders.map(order => (
                    <div key={order.id} className="glass-panel">
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <h3>Order #{order.id}</h3>
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
                            {new Date(order.orderDate).toLocaleString()}
                        </p>

                        <div style={{ margin: '15px 0', borderTop: '1px solid var(--glass-border)', paddingTop: '10px' }}>
                            {order.items.map(item => (
                                <div key={item.id} style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px' }}>
                                    <span>{item.quantity}x {item.product.name}</span>
                                    <span>${item.calculatedPrice.toFixed(2)}</span>
                                </div>
                            ))}
                        </div>

                        <div style={{ textAlign: 'right', fontWeight: 'bold', marginTop: '10px' }}>
                            Total: ${order.totalPrice.toFixed(2)}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default OrderHistory;
