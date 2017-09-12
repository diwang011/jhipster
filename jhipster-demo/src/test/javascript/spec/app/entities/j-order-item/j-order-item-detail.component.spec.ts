/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterDemoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JOrderItemDetailComponent } from '../../../../../../main/webapp/app/entities/j-order-item/j-order-item-detail.component';
import { JOrderItemService } from '../../../../../../main/webapp/app/entities/j-order-item/j-order-item.service';
import { JOrderItem } from '../../../../../../main/webapp/app/entities/j-order-item/j-order-item.model';

describe('Component Tests', () => {

    describe('JOrderItem Management Detail Component', () => {
        let comp: JOrderItemDetailComponent;
        let fixture: ComponentFixture<JOrderItemDetailComponent>;
        let service: JOrderItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterDemoTestModule],
                declarations: [JOrderItemDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JOrderItemService,
                    JhiEventManager
                ]
            }).overrideTemplate(JOrderItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JOrderItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JOrderItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JOrderItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jOrderItem).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
