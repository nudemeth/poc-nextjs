import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';
import categoryApi from '../../api/category.api';

describe('Load Categories Worker saga', () => {
    it('Should get categories from the store ', () => {
        const generator = saga.loadCategoriesWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator if categories exist in the store', () => {
        const store = { categories: [{ id: 1 }] };
        const generator = saga.loadCategoriesWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.done).toBeTruthy();
    });

    it('Should call fetching categories api', () => {
        const store = {};
        const generator = saga.loadCategoriesWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.value).toEqual(effects.call(categoryApi.getCategories));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCategoriesSuccess after successfully fetching categories', () => {
        const store = {};
        const data = { "id": 1 };
        const res = { json: () => data };
        const generator = saga.loadCategoriesWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadCategoriesSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCategoriesFailure after failed to fetch categories', () => {
        const store = {};
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = saga.loadCategoriesWorker();
        
        generator.next();
        generator.next(store);

        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadCategoriesFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadCategoriesSuccess action', () => {
        const store = {};
        const data = { "id": 1 };
        const res = { json: () => data };
        const generator = saga.loadCategoriesWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadCategoriesFailure action', () => {
        const store = {};
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = saga.loadCategoriesWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});