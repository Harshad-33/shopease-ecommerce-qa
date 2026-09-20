import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1090;">
      <div
        *ngFor="let toast of toastService.toasts$ | async"
        class="toast show align-items-center text-white border-0 mb-2 shadow"
        [ngClass]="'bg-' + toast.type"
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
      >
        <div class="d-flex">
          <div class="toast-body fs-6 py-2 px-3">
            <i class="bi me-2" [ngClass]="{
              'bi-check-circle-fill': toast.type === 'success',
              'bi-exclamation-triangle-fill': toast.type === 'danger',
              'bi-info-circle-fill': toast.type === 'info',
              'bi-exclamation-circle-fill': toast.type === 'warning'
            }"></i>
            {{ toast.message }}
          </div>
          <button
            type="button"
            class="btn-close btn-close-white me-2 m-auto"
            (click)="toastService.remove(toast.id)"
            aria-label="Close"
          ></button>
        </div>
      </div>
    </div>
  `
})
export class ToastComponent {
  constructor(public toastService: ToastService) {}
}
