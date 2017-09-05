import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JOrder } from './j-order.model';
import { JOrderPopupService } from './j-order-popup.service';
import { JOrderService } from './j-order.service';

@Component({
    selector: 'jhi-j-order-dialog',
    templateUrl: './j-order-dialog.component.html'
})
export class JOrderDialogComponent implements OnInit {

    jOrder: JOrder;
    isSaving: boolean;
    orderDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private jOrderService: JOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jOrderService.update(this.jOrder));
        } else {
            this.subscribeToSaveResponse(
                this.jOrderService.create(this.jOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<JOrder>) {
        result.subscribe((res: JOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JOrder) {
        this.eventManager.broadcast({ name: 'jOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-j-order-popup',
    template: ''
})
export class JOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jOrderPopupService: JOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jOrderPopupService
                    .open(JOrderDialogComponent as Component, params['id']);
            } else {
                this.jOrderPopupService
                    .open(JOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
