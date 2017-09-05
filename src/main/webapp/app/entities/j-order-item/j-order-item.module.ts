import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterDemoSharedModule } from '../../shared';
import {
    JOrderItemService,
    JOrderItemPopupService,
    JOrderItemComponent,
    JOrderItemDetailComponent,
    JOrderItemDialogComponent,
    JOrderItemPopupComponent,
    JOrderItemDeletePopupComponent,
    JOrderItemDeleteDialogComponent,
    jOrderItemRoute,
    jOrderItemPopupRoute,
    JOrderItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...jOrderItemRoute,
    ...jOrderItemPopupRoute,
];

@NgModule({
    imports: [
        JhipsterDemoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JOrderItemComponent,
        JOrderItemDetailComponent,
        JOrderItemDialogComponent,
        JOrderItemDeleteDialogComponent,
        JOrderItemPopupComponent,
        JOrderItemDeletePopupComponent,
    ],
    entryComponents: [
        JOrderItemComponent,
        JOrderItemDialogComponent,
        JOrderItemPopupComponent,
        JOrderItemDeleteDialogComponent,
        JOrderItemDeletePopupComponent,
    ],
    providers: [
        JOrderItemService,
        JOrderItemPopupService,
        JOrderItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterDemoJOrderItemModule {}
