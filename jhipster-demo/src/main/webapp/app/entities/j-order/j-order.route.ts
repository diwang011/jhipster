import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JOrderComponent } from './j-order.component';
import { JOrderDetailComponent } from './j-order-detail.component';
import { JOrderPopupComponent } from './j-order-dialog.component';
import { JOrderDeletePopupComponent } from './j-order-delete-dialog.component';

@Injectable()
export class JOrderResolvePagingParams implements Resolve<any> {

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

export const jOrderRoute: Routes = [
    {
        path: 'j-order',
        component: JOrderComponent,
        resolve: {
            'pagingParams': JOrderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'j-order/:id',
        component: JOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jOrderPopupRoute: Routes = [
    {
        path: 'j-order-new',
        component: JOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'j-order/:id/edit',
        component: JOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'j-order/:id/delete',
        component: JOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterDemoApp.jOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
