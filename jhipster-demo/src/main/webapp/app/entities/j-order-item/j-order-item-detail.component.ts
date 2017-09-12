import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { JOrderItem } from './j-order-item.model';
import { JOrderItemService } from './j-order-item.service';

@Component({
    selector: 'jhi-j-order-item-detail',
    templateUrl: './j-order-item-detail.component.html'
})
export class JOrderItemDetailComponent implements OnInit, OnDestroy {

    jOrderItem: JOrderItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jOrderItemService: JOrderItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJOrderItems();
    }

    load(id) {
        this.jOrderItemService.find(id).subscribe((jOrderItem) => {
            this.jOrderItem = jOrderItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJOrderItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jOrderItemListModification',
            (response) => this.load(this.jOrderItem.id)
        );
    }
}
