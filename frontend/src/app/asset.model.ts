export type AssetStatus = 'AVAILABLE' | 'ASSIGNED' | 'MAINTENANCE' | 'RETIRED';
export interface Asset {
  id: string;
  name: string;
  serialNumber: string;
  category: string;
  ownerTeam: string;
  status: AssetStatus;
  purchaseValue: number;
  createdAt: string;
  updatedAt: string;
}
export type AssetDraft = Pick<Asset, 'name'|'serialNumber'|'category'|'ownerTeam'|'status'|'purchaseValue'>;
export interface Page<T> { content: T[]; totalElements: number; totalPages: number; number: number; size: number; }
export interface AssetSummary { total: number; byStatus: Record<AssetStatus, number>; }
export function newDraft(): AssetDraft {
  return {name: '', serialNumber: '', category: 'IT', ownerTeam: 'Platform', status: 'AVAILABLE', purchaseValue: 0};
}
