import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { JOrder } from './j-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JOrderService {

    private resourceUrl = 'api/j-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(jOrder: JOrder): Observable<JOrder> {
        const copy = this.convert(jOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(jOrder: JOrder): Observable<JOrder> {
        const copy = this.convert(jOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<JOrder> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.orderDate = this.dateUtils
            .convertLocalDateFromServer(entity.orderDate);
    }

    private convert(jOrder: JOrder): JOrder {
        const copy: JOrder = Object.assign({}, jOrder);
        copy.orderDate = this.dateUtils
            .convertLocalDateToServer(jOrder.orderDate);
        return copy;
    }
}
