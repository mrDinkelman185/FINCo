export interface Order {
  id: number;
  orderId: string;
  accountId: number;
  symbol: string;
  orderType: string;
  side: string;
  quantity: number;
  price?: number;
  status: string;
  filledQuantity: number;
  averageFillPrice?: number;
  timeInForce: string;
  createdAt: string;
  updatedAt: string;
  executedAt?: string;
}

export interface OrderRequest {
  accountId: number;
  symbol: string;
  orderType: string;
  side: string;
  quantity: number;
  price?: number;
  timeInForce?: string;
}

export interface Position {
  id: number;
  accountId: number;
  symbol: string;
  quantity: number;
  averagePrice: number;
  marketValue?: number;
  unrealizedPnl?: number;
  realizedPnl: number;
  createdAt: string;
  updatedAt: string;
}
