import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JOrderItemComponent } from './j-order-item.component';
import { JOrderItemDetailComponent } from './j-order-item-detail.component';
import { JOrderItemPopupComponent } from './j-order-item-dialog.component';
import { JOrderItemDeletePopupComponent } from './j-order-item-delete-dialog.component';

@Injectable()
export class JOrderItemResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const jOrderItemRoute: Routes = [
    {
        path: 'j-order-item',
        component: JOrderItemComponent,
        resolve: {
            'pagingParams': JOrderItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'j-order-item/:id',
        component: JOrderItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jOrderItemPopupRoute: Routes = [
    {
        path: 'j-order-item-new',
        component: JOrderItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'j-order-item/:id/edit',
        component: JOrderItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'j-order-item/:id/delete',
        component: JOrderItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
