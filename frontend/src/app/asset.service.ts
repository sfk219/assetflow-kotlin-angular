import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset, AssetDraft, AssetSummary, Page } from './asset.model';
@Injectable({providedIn: 'root'})
export class AssetService {
  private readonly http = inject(HttpClient);
  private readonly base = '/api/assets';
  list(search = '', status = '', page = 0): Observable<Page<Asset>> {
    let params = new HttpParams().set('page', page).set('size', 10);
    if (search.trim()) params = params.set('search', search.trim());
    if (status) params = params.set('status', status);
    return this.http.get<Page<Asset>>(this.base, { params });
  }
  summary(): Observable<AssetSummary> { return this.http.get<AssetSummary>(`${this.base}/summary`); }
  create(draft: AssetDraft): Observable<Asset> { return this.http.post<Asset>(this.base, draft); }
  update(id: string, draft: AssetDraft): Observable<Asset> { return this.http.put<Asset>(`${this.base}/${id}`, draft); }
  delete(id: string): Observable<void> { return this.http.delete<void>(`${this.base}/${id}`); }
}
