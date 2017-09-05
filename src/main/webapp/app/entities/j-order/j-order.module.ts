import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDemoSharedModule } from '../../shared';
import {
    JOrderService,
    JOrderPopupService,
    JOrderComponent,
    JOrderDetailComponent,
    JOrderDialogComponent,
    JOrderPopupComponent,
    JOrderDeletePopupComponent,
    JOrderDeleteDialogComponent,
    jOrderRoute,
    jOrderPopupRoute,
    JOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...jOrderRoute,
    ...jOrderPopupRoute,
];

@NgModule({
    imports: [
        JhipsterDemoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JOrderComponent,
        JOrderDetailComponent,
        JOrderDialogComponent,
        JOrderDeleteDialogComponent,
        JOrderPopupComponent,
        JOrderDeletePopupComponent,
    ],
    entryComponents: [
        JOrderComponent,
        JOrderDialogComponent,
        JOrderPopupComponent,
        JOrderDeleteDialogComponent,
        JOrderDeletePopupComponent,
    ],
    providers: [
        JOrderService,
        JOrderPopupService,
        JOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterDemoJOrderModule {}
