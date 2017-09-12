/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterDemoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JOrderDetailComponent } from '../../../../../../main/webapp/app/entities/j-order/j-order-detail.component';
import { JOrderService } from '../../../../../../main/webapp/app/entities/j-order/j-order.service';
import { JOrder } from '../../../../../../main/webapp/app/entities/j-order/j-order.model';

describe('Component Tests', () => {

    describe('JOrder Management Detail Component', () => {
        let comp: JOrderDetailComponent;
        let fixture: ComponentFixture<JOrderDetailComponent>;
        let service: JOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterDemoTestModule],
                declarations: [JOrderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JOrderService,
                    JhiEventManager
                ]
            }).overrideTemplate(JOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jOrder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
