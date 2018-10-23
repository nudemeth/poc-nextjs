import * as effects from 'redux-saga/effects'
import * as actions from '../../actions/catalogBrand.actions'
import * as catalogBrandSaga from '../../sagas/catalogBrand.saga'
import catalogApi from '../../api/catalog.api'

describe('Load CatalogBrands Worker saga', () => {
    it('Should get catalogBrands from the store ', () => {
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        const result = generator.next()
        expect(result.value).toEqual(effects.select())
        expect(result.done).toBeFalsy()
    })

    it('Should end the generator if catalogBrands exist in the store', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c' }] }}
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()

        const result = generator.next(store)
        expect(result.done).toBeTruthy()
    })

    it('Should call fetching catalog api', () => {
        const store = { catalogBrandReducer: {} }
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()

        const result = generator.next(store)
        expect(result.value).toEqual(effects.call(catalogApi.getCatalogBrands))
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogBrandsSuccess after successfully fetching catalogBrands', () => {
        const store = { catalogBrandReducer: {} }
        const data = { id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c' }
        const res = { json: () => data }
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)

        const result = generator.next(data)
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsSuccess(data)))
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogBrandsFailure after failed to fetch catalogBrands', () => {
        const store = { catalogBrandReducer: {} }
        const err = new Error()
        const res = { json: () => { throw err } }
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()
        generator.next(store)

        const result = generator.next(res)
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsFailure(err)))
        expect(result.done).toBeFalsy()
    })

    it('Should end the generator after putting to loadCatalogBrandsSuccess action', () => {
        const store = { catalogBrandReducer: {} }
        const data = { id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c' }
        const res = { json: () => data }
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)
        generator.next(data)

        const result = generator.next()
        expect(result.done).toBeTruthy()
    })

    it('Should end the generator after putting to loadCatalogBrandsFailure action', () => {
        const store = { catalogBrandReducer: {} }
        const err = new Error()
        const res = { json: () => { throw err } }
        const generator = catalogBrandSaga.loadCatalogBrandsWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)

        const result = generator.next()
        expect(result.done).toBeTruthy()
    })
})

describe('Get Selected CatalogBrands Id Worker saga', () => {
    it('Should get selected catalogBrands id', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c', isSelected: true }, { id: 'daf69c8e-afea-4991-8dd5-c3837c19d5b4', isSelected: false }] }}
        const generator = catalogBrandSaga.getSelectedCatalogBrandIds()

        generator.next()
        generator.next()
        
        const result = generator.next(store)
        expect(result.value.length).toBe(1)
        expect(result.value).toEqual(['37204a9c-de5d-450e-9d2a-0634f0f5a04c'])
    })

    it('Should end the generator after getting selected catalogBrands id', () => {
        const store = { catalogBrandReducer: { catalogBrands: [{ id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c', isSelected: true }, { id: 'daf69c8e-afea-4991-8dd5-c3837c19d5b4', isSelected: false }] }}
        const generator = catalogBrandSaga.getSelectedCatalogBrandIds()

        generator.next()
        generator.next()

        const result =generator.next(store)
        expect(result.done).toBeTruthy()
    })
})

describe('Update Selected CatalogBrands Worker saga', () => {
    it('Should get catalogBrands from the store', () => {
        const action = { payload: { catalogBrand: { id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c' }, isSelected: true } }
        const generator = catalogBrandSaga.updateSelectedCatalogBrand(action)
        const result = generator.next()
        expect(result.value).toEqual(effects.select())
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogBrandsSuccess after updated selected catalogBrand', () => {
        const action = { payload: { catalogBrand: { id: 2 }, isSelected: true } }
        const store = { catalogBrandReducer: { catalogBrands: [{ id: '37204a9c-de5d-450e-9d2a-0634f0f5a04c', isSelected: true }, { id: 'daf69c8e-afea-4991-8dd5-c3837c19d5b4', isSelected: false }] }}
        const generator = catalogBrandSaga.updateSelectedCatalogBrand(action)

        generator.next()
        
        const result = generator.next(store)
        expect(result.value).toEqual(effects.put(actions.loadCatalogBrandsSuccess(store.catalogBrandReducer.catalogBrands)))
    })
})