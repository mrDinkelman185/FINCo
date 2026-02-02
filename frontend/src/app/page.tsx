'use client';

import { useState, useEffect } from 'react';
import { Order, Position, OrderRequest } from '@/types';
import { orderApi, positionApi } from '@/lib/api';

export default function Home() {
  const [orders, setOrders] = useState<Order[]>([]);
  const [positions, setPositions] = useState<Position[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const [newOrder, setNewOrder] = useState<OrderRequest>({
    accountId: 1,
    symbol: '',
    orderType: 'MARKET',
    side: 'BUY',
    quantity: 0,
    timeInForce: 'DAY',
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [ordersData, positionsData] = await Promise.all([
        orderApi.getAllOrders(),
        positionApi.getAllPositions(),
      ]);
      setOrders(ordersData);
      setPositions(positionsData);
      setError(null);
    } catch (err) {
      setError('Failed to load data. Make sure the backend is running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmitOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    setSuccess(null);
    setError(null);

    try {
      const order = await orderApi.createOrder(newOrder);
      setSuccess(`Order created successfully: ${order.orderId}`);
      setNewOrder({
        accountId: 1,
        symbol: '',
        orderType: 'MARKET',
        side: 'BUY',
        quantity: 0,
        timeInForce: 'DAY',
      });
      loadData();
    } catch (err) {
      setError('Failed to create order. Please try again.');
      console.error(err);
    }
  };

  const handleCancelOrder = async (orderId: string) => {
    try {
      await orderApi.cancelOrder(orderId);
      setSuccess(`Order ${orderId} cancelled successfully`);
      loadData();
    } catch (err) {
      setError('Failed to cancel order');
      console.error(err);
    }
  };

  return (
    <div>
      <div className="header">
        <div className="container">
          <h1>FINCo Trading Platform</h1>
        </div>
      </div>

      <div className="container">
        {error && <div className="error">{error}</div>}
        {success && <div className="success">{success}</div>}

        <div className="dashboard">
          {/* New Order Form */}
          <div className="card">
            <h2>Place New Order</h2>
            <form className="order-form" onSubmit={handleSubmitOrder}>
              <div className="form-group">
                <label>Account ID</label>
                <input
                  type="number"
                  value={newOrder.accountId}
                  onChange={(e) => setNewOrder({ ...newOrder, accountId: parseInt(e.target.value) })}
                  required
                />
              </div>
              <div className="form-group">
                <label>Symbol</label>
                <input
                  type="text"
                  value={newOrder.symbol}
                  onChange={(e) => setNewOrder({ ...newOrder, symbol: e.target.value.toUpperCase() })}
                  placeholder="e.g., AAPL"
                  required
                />
              </div>
              <div className="form-group">
                <label>Side</label>
                <select
                  value={newOrder.side}
                  onChange={(e) => setNewOrder({ ...newOrder, side: e.target.value })}
                >
                  <option value="BUY">BUY</option>
                  <option value="SELL">SELL</option>
                </select>
              </div>
              <div className="form-group">
                <label>Order Type</label>
                <select
                  value={newOrder.orderType}
                  onChange={(e) => setNewOrder({ ...newOrder, orderType: e.target.value })}
                >
                  <option value="MARKET">MARKET</option>
                  <option value="LIMIT">LIMIT</option>
                </select>
              </div>
              <div className="form-group">
                <label>Quantity</label>
                <input
                  type="number"
                  step="0.01"
                  value={newOrder.quantity}
                  onChange={(e) => setNewOrder({ ...newOrder, quantity: parseFloat(e.target.value) })}
                  required
                />
              </div>
              {newOrder.orderType === 'LIMIT' && (
                <div className="form-group">
                  <label>Price</label>
                  <input
                    type="number"
                    step="0.01"
                    value={newOrder.price || ''}
                    onChange={(e) => setNewOrder({ ...newOrder, price: parseFloat(e.target.value) })}
                    required
                  />
                </div>
              )}
              <button type="submit" className="button">
                Place Order
              </button>
            </form>
          </div>

          {/* Orders List */}
          <div className="card">
            <h2>Orders</h2>
            {loading ? (
              <div className="loading">Loading orders...</div>
            ) : orders.length === 0 ? (
              <div className="loading">No orders found</div>
            ) : (
              <table className="table">
                <thead>
                  <tr>
                    <th>Order ID</th>
                    <th>Symbol</th>
                    <th>Side</th>
                    <th>Type</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.map((order) => (
                    <tr key={order.id}>
                      <td>{order.orderId}</td>
                      <td>{order.symbol}</td>
                      <td className={order.side === 'BUY' ? 'side-buy' : 'side-sell'}>
                        {order.side}
                      </td>
                      <td>{order.orderType}</td>
                      <td>{order.quantity}</td>
                      <td>{order.price ? `$${order.price.toFixed(2)}` : 'N/A'}</td>
                      <td>
                        <span className={`status status-${order.status.toLowerCase()}`}>
                          {order.status}
                        </span>
                      </td>
                      <td>
                        {order.status === 'PENDING' && (
                          <button
                            className="button"
                            style={{ padding: '5px 10px', fontSize: '12px' }}
                            onClick={() => handleCancelOrder(order.orderId)}
                          >
                            Cancel
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>

          {/* Positions List */}
          <div className="card">
            <h2>Positions</h2>
            {loading ? (
              <div className="loading">Loading positions...</div>
            ) : positions.length === 0 ? (
              <div className="loading">No positions found</div>
            ) : (
              <table className="table">
                <thead>
                  <tr>
                    <th>Symbol</th>
                    <th>Quantity</th>
                    <th>Avg Price</th>
                    <th>Market Value</th>
                    <th>Unrealized P&L</th>
                    <th>Realized P&L</th>
                  </tr>
                </thead>
                <tbody>
                  {positions.map((position) => (
                    <tr key={position.id}>
                      <td>{position.symbol}</td>
                      <td>{position.quantity}</td>
                      <td>${position.averagePrice.toFixed(2)}</td>
                      <td>{position.marketValue ? `$${position.marketValue.toFixed(2)}` : 'N/A'}</td>
                      <td
                        style={{
                          color: position.unrealizedPnl && position.unrealizedPnl > 0 ? '#4ade80' : '#ef4444',
                        }}
                      >
                        {position.unrealizedPnl ? `$${position.unrealizedPnl.toFixed(2)}` : 'N/A'}
                      </td>
                      <td
                        style={{
                          color: position.realizedPnl > 0 ? '#4ade80' : '#ef4444',
                        }}
                      >
                        ${position.realizedPnl.toFixed(2)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
