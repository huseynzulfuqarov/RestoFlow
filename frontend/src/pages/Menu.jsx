import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { Search, ShoppingCart } from 'lucide-react';

const Menu = () => {
    const [dishes, setDishes] = useState([]);
    const [filteredDishes, setFilteredDishes] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedDish, setSelectedDish] = useState(null);
    const [recommendations, setRecommendations] = useState([]);
    const [cart, setCart] = useState([]);
    const [selectedOptions, setSelectedOptions] = useState([]);
    const [orderStatus, setOrderStatus] = useState(null);
    const { user } = useAuth();

    useEffect(() => {
        fetchDishes();
    }, []);

    useEffect(() => {
        setFilteredDishes(
            dishes.filter(dish => dish.name.toLowerCase().includes(searchTerm.toLowerCase()))
        );
    }, [searchTerm, dishes]);

    const fetchDishes = async () => {
        try {
            const res = await axios.get('/api/products/dishes');
            setDishes(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const openDish = async (id) => {
        try {
            const res = await axios.get(`/api/products/${id}`);
            setSelectedDish(res.data);
            setSelectedOptions([]);

            const recRes = await axios.get(`/api/products/${id}/recommendations`);
            setRecommendations(recRes.data);
        } catch (err) {
            console.error(err);
        }
    };

    const toggleOption = (optionId) => {
        if (selectedOptions.includes(optionId)) {
            setSelectedOptions(selectedOptions.filter(id => id !== optionId));
        } else {
            setSelectedOptions([...selectedOptions, optionId]);
        }
    };

    const addToCart = () => {
        const cartItem = {
            product: selectedDish,
            quantity: 1,
            selectedOptionIds: selectedOptions,
            totalPrice: calculatePrice(selectedDish, selectedOptions)
        };
        setCart([...cart, cartItem]);
        setSelectedDish(null);
        setRecommendations([]);
    };

    const calculatePrice = (dish, options) => {
        let price = dish.basePrice; // This is now dynamic from backend!
        if (dish.customizationGroups) {
            dish.customizationGroups.forEach(group => {
                group.options.forEach(opt => {
                    if (options.includes(opt.id)) {
                        price += opt.salesPrice;
                    }
                });
            });
        }
        return price;
    };

    const placeOrder = async () => {
        try {
            const orderItems = cart.map(item => ({
                productId: item.product.id,
                quantity: item.quantity,
                selectedOptionIds: item.selectedOptionIds
            }));

            const res = await axios.post('/api/orders', orderItems, {
                headers: { 'X-User-Name': user.username }
            });
            setOrderStatus(`Order Placed! ID: ${res.data.id}. Ready at: ${new Date(res.data.estimatedReadyTime).toLocaleTimeString()}`);
            setCart([]);
        } catch (err) {
            console.error(err);
            if (err.response && err.response.data && err.response.data.message) {
                setOrderStatus(`Error: ${err.response.data.message}`);
            } else {
                setOrderStatus('Failed to place order. Kitchen might be overloaded.');
            }
        }
    };

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' }}>
                <h1>Our Menu</h1>
                <div style={{ position: 'relative', width: '300px' }}>
                    <Search size={20} style={{ position: 'absolute', left: '10px', top: '10px', opacity: 0.5 }} />
                    <input
                        type="text"
                        placeholder="Search dishes..."
                        className="glass-input"
                        style={{ paddingLeft: '40px' }}
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
            </div>

            <div className="grid">
                {filteredDishes.map(dish => (
                    <div key={dish.id} className="glass-panel" style={{ cursor: 'pointer', transition: 'transform 0.2s', padding: 0, overflow: 'hidden' }} onClick={() => openDish(dish.id)}>
                        <div style={{ height: '150px', background: 'rgba(0,0,0,0.3)', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            {dish.imageUrl ? (
                                <img src={dish.imageUrl} alt={dish.name} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                            ) : (
                                <span style={{ fontSize: '3rem' }}>🍕</span>
                            )}
                        </div>
                        <div style={{ padding: '20px' }}>
                            <h3>{dish.name}</h3>
                            <p style={{ opacity: 0.7 }}>{dish.preparationTime} mins • ${dish.basePrice.toFixed(2)}</p>
                        </div>
                    </div>
                ))}
            </div>

            {/* Modal */}
            {selectedDish && (
                <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.8)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
                    <div className="glass-panel" style={{ width: '90%', maxWidth: '600px', maxHeight: '90vh', overflowY: 'auto', background: 'var(--bg-gradient-start)' }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                            <h2>{selectedDish.name}</h2>
                            <button onClick={() => setSelectedDish(null)} style={{ background: 'none', border: 'none', color: 'var(--text-color)', fontSize: '1.5rem', cursor: 'pointer' }}>×</button>
                        </div>

                        <p>Base Price: ${selectedDish.basePrice.toFixed(2)}</p>

                        {selectedDish.customizationGroups && selectedDish.customizationGroups.map(group => (
                            <div key={group.id} style={{ marginTop: '20px' }}>
                                <h4>{group.name} {group.required && <span style={{ color: 'var(--primary-color)', fontSize: '0.8rem' }}>(Required)</span>}</h4>
                                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
                                    {group.options.map(option => (
                                        <div
                                            key={option.id}
                                            onClick={() => toggleOption(option.id)}
                                            style={{
                                                padding: '8px 15px',
                                                borderRadius: '20px',
                                                border: '1px solid var(--glass-border)',
                                                background: selectedOptions.includes(option.id) ? 'var(--primary-color)' : 'transparent',
                                                cursor: 'pointer',
                                                fontSize: '0.9rem'
                                            }}
                                        >
                                            {option.name} (+${option.salesPrice})
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}

                        <div style={{ marginTop: '30px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                            <h3>Total: ${calculatePrice(selectedDish, selectedOptions).toFixed(2)}</h3>
                            <button className="glass-btn" onClick={addToCart}>Add to Cart</button>
                        </div>
                    </div>
                </div>
            )}

            {/* Cart */}
            {cart.length > 0 && (
                <div className="glass-panel" style={{ position: 'fixed', bottom: '20px', right: '20px', width: '300px', zIndex: 900 }}>
                    <h3><ShoppingCart size={18} /> Your Cart ({cart.length})</h3>
                    <ul style={{ listStyle: 'none', padding: 0, maxHeight: '200px', overflowY: 'auto' }}>
                        {cart.map((item, idx) => (
                            <li key={idx} style={{ borderBottom: '1px solid var(--glass-border)', padding: '10px 0' }}>
                                <div>{item.product.name}</div>
                                <div style={{ textAlign: 'right' }}>${item.totalPrice.toFixed(2)}</div>
                            </li>
                        ))}
                    </ul>
                    <button className="glass-btn" style={{ width: '100%', marginTop: '10px' }} onClick={placeOrder}>
                        Place Order
                    </button>
                </div>
            )}

            {orderStatus && (
                <div className="glass-panel" style={{ position: 'fixed', top: '20px', right: '20px', background: 'rgba(0, 255, 100, 0.2)', border: '1px solid rgba(0, 255, 100, 0.4)' }}>
                    {orderStatus}
                    <button onClick={() => setOrderStatus(null)} style={{ marginLeft: '10px', background: 'none', border: 'none', color: 'white', cursor: 'pointer' }}>×</button>
                </div>
            )}
        </div>
    );
};

export default Menu;
