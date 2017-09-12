import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JOrderItem } from './j-order-item.model';
import { JOrderItemPopupService } from './j-order-item-popup.service';
import { JOrderItemService } from './j-order-item.service';

@Component({
    selector: 'jhi-j-order-item-delete-dialog',
    templateUrl: './j-order-item-delete-dialog.component.html'
})
export class JOrderItemDeleteDialogComponent {

    jOrderItem: JOrderItem;

    constructor(
        private jOrderItemService: JOrderItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jOrderItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jOrderItemListModification',
                content: 'Deleted an jOrderItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-j-order-item-delete-popup',
    template: ''
})
export class JOrderItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jOrderItemPopupService: JOrderItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jOrderItemPopupService
                .open(JOrderItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
