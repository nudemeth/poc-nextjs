import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';
import productApi from '../../api/product.api';

describe('Load Product Worker saga', () => {
    it('Should call fetching product by id', () => {
        const params = { payload: { id: 1 } };
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const generator = saga.loadProductWorker(params);
        const result = generator.next();
        expect(result.value).toEqual(effects.call(productApi.getProduct, params.payload.id));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadProductSuccess after successfully fetch product by id', () => {
        const params = { payload: { id: 1 } };
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
        const params = { payload: { id: 1 } };
        const err = new Error();
        const res = { json: () => { throw err }};
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        
        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadProductFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadProductFailure action', () => {
        const params = { payload: { id: 1 } };
        const err = new Error();
        const res = { json: () => { throw err }};
        const generator = saga.loadProductWorker(params);
        
        generator.next();
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadProductSuccess action', () => {
        const params = { payload: { id: 1 } };
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

describe('Load Products Worker saga', () => {
    it('Should call getSelectedCategoryIds', () => {
        const generator = saga.loadProductsWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.call(saga.getSelectedCategoryIds));
        expect(result.done).toBeFalsy();
    });

    it('Should call getProducts from product api', () => {
        const categoryIds = [1];
        const generator = saga.loadProductsWorker();

        generator.next();

        const result = generator.next(categoryIds);
        expect(result.value).toEqual(effects.call(productApi.getProducts, categoryIds));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadProductsSuccess after successfully fetch products by category ids', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const res = { json: () => data };
        const generator = saga.loadProductsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadProductsSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadProductsFailure after failed to fetch products by category ids', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = saga.loadProductsWorker();
        
        generator.next();
        generator.next();

        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadProductsFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadProductsSuccess action', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const res = { json: () => data };
        const generator = saga.loadProductsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadProductsFailure action', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = saga.loadProductsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});