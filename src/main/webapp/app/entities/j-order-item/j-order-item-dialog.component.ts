import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JOrderItem } from './j-order-item.model';
import { JOrderItemPopupService } from './j-order-item-popup.service';
import { JOrderItemService } from './j-order-item.service';
import { JOrder, JOrderService } from '../j-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-j-order-item-dialog',
    templateUrl: './j-order-item-dialog.component.html'
})
export class JOrderItemDialogComponent implements OnInit {

    jOrderItem: JOrderItem;
    isSaving: boolean;

    jorders: JOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private jOrderItemService: JOrderItemService,
        private jOrderService: JOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.jOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.jorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jOrderItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jOrderItemService.update(this.jOrderItem));
        } else {
            this.subscribeToSaveResponse(
                this.jOrderItemService.create(this.jOrderItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<JOrderItem>) {
        result.subscribe((res: JOrderItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JOrderItem) {
        this.eventManager.broadcast({ name: 'jOrderItemListModification', content: 'OK'});
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

    trackJOrderById(index: number, item: JOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-j-order-item-popup',
    template: ''
})
export class JOrderItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jOrderItemPopupService: JOrderItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jOrderItemPopupService
                    .open(JOrderItemDialogComponent as Component, params['id']);
            } else {
                this.jOrderItemPopupService
                    .open(JOrderItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
