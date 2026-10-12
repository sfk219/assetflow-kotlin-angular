import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';
import { Asset, AssetDraft, AssetStatus, AssetSummary, newDraft } from './asset.model';
import { AssetService } from './asset.service';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  private readonly api = inject(AssetService);
  readonly statuses: AssetStatus[] = ['AVAILABLE', 'ASSIGNED', 'MAINTENANCE', 'RETIRED'];
  assets: Asset[] = [];
  summary?: AssetSummary;
  search = '';
  statusFilter = '';
  page = 0;
  totalPages = 0;
  totalElements = 0;
  draft: AssetDraft = newDraft();
  editingId: string | null = null;
  busy = false;
  error = '';
  notice = '';
  ngOnInit(): void { this.load(); }
  load(): void {
    this.error = '';
    this.busy = true;
    this.api.list(this.search, this.statusFilter, this.page).pipe(finalize(() => this.busy = false)).subscribe({
      next: result => { this.assets = result.content; this.totalElements = result.totalElements; this.totalPages = result.totalPages; },
      error: e => this.showError(e)
    });
    this.api.summary().subscribe({next: s => this.summary = s, error: () => {/* list error surfaced separately */}});
  }
  applyFilters(): void { this.page = 0; this.load(); }
  go(next: number): void { if (next >= 0 && next < this.totalPages) { this.page = next; this.load(); } }
  edit(asset: Asset): void {
    this.editingId = asset.id;
    this.draft = {name: asset.name, serialNumber: asset.serialNumber, category: asset.category, ownerTeam: asset.ownerTeam,
      status: asset.status, purchaseValue: asset.purchaseValue};
    window.scrollTo({top: 0, behavior: 'smooth'});
  }
  reset(): void { this.editingId = null; this.draft = newDraft(); }
  save(): void {
    this.error = ''; this.notice = '';
    if (!this.draft.name.trim() || !this.draft.serialNumber.trim() || !this.draft.ownerTeam.trim() || !this.draft.category.trim()) {
      this.error = 'All text fields are required.'; return;
    }
    if (this.draft.purchaseValue < 0) { this.error = 'Value must be nonnegative.'; return; }
    this.busy = true;
    const request = this.editingId ? this.api.update(this.editingId, this.draft) : this.api.create(this.draft);
    request.pipe(finalize(() => this.busy = false)).subscribe({
      next: () => { this.notice = this.editingId ? 'Asset updated.' : 'Asset created.'; this.reset(); this.load(); },
      error: e => this.showError(e)
    });
  }
  remove(asset: Asset): void {
    if (!window.confirm(`Delete ${asset.name}?`)) return;
    this.error = '';
    this.api.delete(asset.id).subscribe({
      next: () => { this.notice = 'Asset removed.'; this.load(); },
      error: e => this.showError(e)
    });
  }
  private showError(e: HttpErrorResponse): void { this.error = e.error?.message || 'Request failed. Ensure the API and database are running.'; }
}
