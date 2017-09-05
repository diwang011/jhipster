import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JOrder } from './j-order.model';
import { JOrderPopupService } from './j-order-popup.service';
import { JOrderService } from './j-order.service';

@Component({
    selector: 'jhi-j-order-delete-dialog',
    templateUrl: './j-order-delete-dialog.component.html'
})
export class JOrderDeleteDialogComponent {

    jOrder: JOrder;

    constructor(
        private jOrderService: JOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jOrderListModification',
                content: 'Deleted an jOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-j-order-delete-popup',
    template: ''
})
export class JOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jOrderPopupService: JOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jOrderPopupService
                .open(JOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
