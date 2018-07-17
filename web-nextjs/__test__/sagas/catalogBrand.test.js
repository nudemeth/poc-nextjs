import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/catalogBrand.actions';
import * as catalogBrandSaga from '../../sagas/catalogBrand.saga';
import catalogApi from '../../api/catalog.api';

describe('Load CatalogBrands Worker saga', () => {
    it('Should get catalogBrands from the store ', () => {
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator if catalogBrands exist in the store', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: 1 }] }};
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.done).toBeTruthy();
    });

    it('Should call fetching catalog api', () => {
        const store = { catalogBrandReducer: {} };
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();

        const result = generator.next(store);
        expect(result.value).toEqual(effects.call(catalogApi.getCatalogBrands));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCatalogBrandsSuccess after successfully fetching catalogBrands', () => {
        const store = { catalogBrandReducer: {} };
        const data = { "id": 1 };
        const res = { json: () => data };
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);

        const result = generator.next(data);
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsSuccess(data)));
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCatalogBrandsFailure after failed to fetch catalogBrands', () => {
        const store = { catalogBrandReducer: {} };
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();
        generator.next(store);

        const result = generator.next(res);
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsFailure(err)));
        expect(result.done).toBeFalsy();
    });

    it('Should end the generator after putting to loadCatalogBrandsSuccess action', () => {
        const store = { catalogBrandReducer: {} };
        const data = { "id": 1 };
        const res = { json: () => data };
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);
        generator.next(data);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });

    it('Should end the generator after putting to loadCatalogBrandsFailure action', () => {
        const store = { catalogBrandReducer: {} };
        const err = new Error();
        const res = { json: () => { throw err; } };
        const generator = catalogBrandSaga.loadCatalogBrandsWorker();
        
        generator.next();
        generator.next(store);
        generator.next(res);

        const result = generator.next();
        expect(result.done).toBeTruthy();
    });
});

describe('Get Selected CatalogBrands Id Worker saga', () => {
    it('Should get selected catalogBrands id', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }};
        const generator = catalogBrandSaga.getSelectedCatalogBrandIds();

        generator.next();
        generator.next();
        
        const result = generator.next(store);
        expect(result.value.length).toBe(1);
        expect(result.value).toEqual([1]);
    });

    it('Should end the generator after getting selected catalogBrands id', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }};
        const generator = catalogBrandSaga.getSelectedCatalogBrandIds();

        generator.next();
        generator.next();

        const result =generator.next(store);
        expect(result.done).toBeTruthy();
    });
});

describe('Update Selected CatalogBrands Worker saga', () => {
    it('Should get catalogBrands from the store', () => {
        const action = { payload: { catalogBrand: { id: 1 }, isSelected: true } };
        const generator = catalogBrandSaga.updateSelectedCatalogBrand(action);
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });

    it('Should put action to loadCatalogBrandsSuccess after updated selected catalogBrand', () => {
        const action = { payload: { catalogBrand: { id: 2 }, isSelected: true } };
        const store = { catalogBrandReducer: { catalogBrands: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }};
        const generator = catalogBrandSaga.updateSelectedCatalogBrand(action);

        generator.next();
        
        const result = generator.next(store);
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsSuccess(store.catalogBrandReducer.catalogBrands)));
    });
});