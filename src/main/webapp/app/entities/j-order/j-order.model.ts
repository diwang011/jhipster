import { BaseEntity } from './../../shared';

export class JOrder implements BaseEntity {
    constructor(
        public id?: number,
        public orderNum?: string,
        public orderStatus?: string,
        public orderDate?: any,
        public orderDesc?: string,
        public items?: BaseEntity[],
    ) {
    }
}
