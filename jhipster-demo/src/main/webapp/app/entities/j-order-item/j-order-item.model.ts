import { BaseEntity } from './../../shared';

export class JOrderItem implements BaseEntity {
    constructor(
        public id?: number,
        public sku?: string,
        public qty?: number,
        public itemDesc?: string,
        public order?: BaseEntity,
    ) {
    }
}
