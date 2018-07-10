import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/item.actions';
import * as itemSaga from '../../sagas/item.saga';
import * as categorySaga from '../../sagas/category.saga';
import itemApi from '../../api/item.api';

describe('Load Item Worker saga', () => {
    it('Should call fetching item by id', () => {
        const params = { payload: { id: 1 } };
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const generator = itemSaga.loadItemWorker(params);
        const result = generator.next();
        expect(result.value).toEqual(effects.call(itemApi.getItemById, params.payload.id));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadItemSuccess after successfully fetch item by id', () => {
        const params = { payload: { id: 1 } };
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const res = { json: () => data };
        const generator = itemSaga.loadItemWorker(params);
        
        generator.next();
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadItemSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadItemFailure when an error occurs on fetching item by id', () => {
        const params = { payload: { id: 1 } };
        const err = new Error();
        const res = { json: () => { throw err; }};
        const generator = itemSaga.loadItemWorker(params);
        
        generator.next();
        
        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadItemFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadItemFailure action', () => {
        const params = { payload: { id: 1 } };
        const err = new Error();
        const res = { json: () => { throw err; }};
        const generator = itemSaga.loadItemWorker(params);
        
        generator.next();
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadItemSuccess action', () => {
        const params = { payload: { id: 1 } };
        const data = { "id": 1, "name": "Item 1", "categoryId": 1 };
        const res = { json: () => data };
        const generator = itemSaga.loadItemWorker(params);
        
        generator.next();
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});

describe('Load Items Worker saga', () => {
    it('Should call getSelectedCategoryIds', () => {
        const generator = itemSaga.loadItemsWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.call(categorySaga.getSelectedCategoryIds));
        expect(result.done).toBeFalsy();
    });

    it('Should call getItems from item api', () => {
        const categoryIds = [1];
        const generator = itemSaga.loadItemsWorker();

        generator.next();

        const result = generator.next(categoryIds);
        expect(result.value).toEqual(effects.call(itemApi.getItems, categoryIds));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadItemsSuccess after successfully fetch items by category ids', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const res = { json: () => data };
        const generator = itemSaga.loadItemsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadItemsSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadItemsFailure after failed to fetch items by category ids', () => {
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = itemSaga.loadItemsWorker();
        
        generator.next();
        generator.next();

        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadItemsFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadItemsSuccess action', () => {
        const data = { payload: [{ id: 1 }, { id: 2 }] };
        const res = { json: () => data };
        const generator = itemSaga.loadItemsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadItemsFailure action', () => {
        const err = new Error();
        const res = { json: () => { throw err; }};
        const generator = itemSaga.loadItemsWorker();
        
        generator.next();
        generator.next();
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});