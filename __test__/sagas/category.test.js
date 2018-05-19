import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/category.actions';
import * as categorySaga from '../../sagas/category.saga';
import categoryApi from '../../api/category.api';

describe('Load Categories Worker saga', () => {
    it('Should get categories from the store ', () => {
        const generator = categorySaga.loadCategoriesWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator if categories exist in the store', () => {
        const store = { categories: [{ id: 1 }] };
        const generator = categorySaga.loadCategoriesWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.done).toBeTruthy();
    });

    it('Should call fetching categories api', () => {
        const store = {};
        const generator = categorySaga.loadCategoriesWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.value).toEqual(effects.call(categoryApi.getCategories));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCategoriesSuccess after successfully fetching categories', () => {
        const store = {};
        const data = { "id": 1 };
        const res = { json: () => data };
        const generator = categorySaga.loadCategoriesWorker();
        
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
        const generator = categorySaga.loadCategoriesWorker();
        
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
        const generator = categorySaga.loadCategoriesWorker();
        
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
        const generator = categorySaga.loadCategoriesWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});

describe('Get Selected Categories Id Worker saga', () => {
    it('Should get selected categories id', () => {
        const store = { categories: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] };
        const generator = categorySaga.getSelectedCategoryIds();

        generator.next();
        generator.next();
        
        const result = generator.next(store);
        expect(result.value.length).toBe(1);
        expect(result.value).toEqual([1]);
    });

    it('Should end the generator after getting selected categories id', () => {
        const store = { categories: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] };
        const generator = categorySaga.getSelectedCategoryIds();

        generator.next();
        generator.next();

        const result =generator.next(store);
        expect(result.done).toBeTruthy();
    });
});

describe('Update Selected Categories Worker saga', () => {
    it('Should get categories from the store', () => {
        const action = { payload: { category: { id: 1 }, isSelected: true } };
        const generator = categorySaga.updateSelectedCategory(action);
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCategoriesSuccess after updated selected category', () => {
        const action = { payload: { category: { id: 2 }, isSelected: true } };
        const store = { categories: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] };
        const generator = categorySaga.updateSelectedCategory(action);

        generator.next();
        
        const result = generator.next(store);
        expect(result.value).toEqual(effects.put(actions.loadCategoriesSuccess(store.categories)));
    });
});