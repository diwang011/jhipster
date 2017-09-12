import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { JOrder } from './j-order.model';
import { JOrderService } from './j-order.service';

@Component({
    selector: 'jhi-j-order-detail',
    templateUrl: './j-order-detail.component.html'
})
export class JOrderDetailComponent implements OnInit, OnDestroy {

    jOrder: JOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jOrderService: JOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJOrders();
    }

    load(id) {
        this.jOrderService.find(id).subscribe((jOrder) => {
            this.jOrder = jOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jOrderListModification',
            (response) => this.load(this.jOrder.id)
        );
    }
}
