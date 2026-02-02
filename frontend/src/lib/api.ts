import axios from 'axios';
import { Order, OrderRequest, Position } from '@/types';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const orderApi = {
  createOrder: async (order: OrderRequest): Promise<Order> => {
    const response = await api.post<Order>('/orders', order);
    return response.data;
  },

  getAllOrders: async (accountId?: number): Promise<Order[]> => {
    const response = await api.get<Order[]>('/orders', {
      params: accountId ? { accountId } : undefined,
    });
    return response.data;
  },

  getOrder: async (orderId: string): Promise<Order> => {
    const response = await api.get<Order>(`/orders/${orderId}`);
    return response.data;
  },

  updateOrder: async (orderId: string, order: OrderRequest): Promise<Order> => {
    const response = await api.put<Order>(`/orders/${orderId}`, order);
    return response.data;
  },

  cancelOrder: async (orderId: string): Promise<void> => {
    await api.delete(`/orders/${orderId}`);
  },
};

export const positionApi = {
  getAllPositions: async (accountId?: number): Promise<Position[]> => {
    const response = await api.get<Position[]>('/positions', {
      params: accountId ? { accountId } : undefined,
    });
    return response.data;
  },

  getPosition: async (symbol: string, accountId: number): Promise<Position> => {
    const response = await api.get<Position>(`/positions/${symbol}`, {
      params: { accountId },
    });
    return response.data;
  },
};
