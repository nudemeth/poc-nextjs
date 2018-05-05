import * as effects from 'redux-saga/effects';
import sinon from 'sinon';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';
import ProductApi from '../../api/product.api';

const api = new ProductApi('http://localhost:5000/');

describe('Load Product Worker saga', () => {
    it('Should call fetching product by id', () => {       
        const params = { payload: { id: 1 } }
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const res = { json: () => data };
        const generator = saga.loadProductWorker(params);
        const result = generator.next();
        expect(result.value).toEqual(effects.call(api.getProduct, params.payload.id));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadProductSuccess after successfully fetch product by id', () => {
        const params = { payload: { id: 1 } }
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const res = { json: () => data };
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadProductSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadProductFailure when an error occurs on fetching product by id', () => {
        const params = { payload: { id: 1 } }
        const err = new Error();
        const res = { json: () => { throw err }};
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        
        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadProductFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadProductFailure action', () => {
        const params = { payload: { id: 1 } }
        const err = new Error();
        const res = { json: () => { throw err }};
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadProductSuccess action', () => {
        const params = { payload: { id: 1 } }
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const res = { json: () => data };
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});