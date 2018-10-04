import * as effects from 'redux-saga/effects'
import * as actions from '../../actions/catalogType.actions'
import * as catalogTypeSaga from '../../sagas/catalogType.saga'
import catalogApi from '../../api/catalog.api'

describe('Load CatalogTypes Worker saga', () => {
    it('Should get catalogTypes from the store ', () => {
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        const result = generator.next()
        expect(result.value).toEqual(effects.select())
        expect(result.done).toBeFalsy()
    })

    it('Should end the generator if catalogTypes exist in the store', () => {
        const store = { catalogTypeReducer: { catalogTypes: [{ id: 1 }] }}
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()

        const result = generator.next(store)
        expect(result.done).toBeTruthy()
    })

    it('Should call fetching catalog api', () => {
        const store = { catalogTypeReducer: {} }
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()

        const result = generator.next(store)
        expect(result.value).toEqual(effects.call(catalogApi.getCatalogTypes))
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogTypesSuccess after successfully fetching catalogTypes', () => {
        const store = { catalogTypeReducer: {} }
        const data = { 'id': 1 }
        const res = { json: () => data }
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)

        const result = generator.next(data)
        expect(result.value).toEqual(effects.put(actions.loadCatalogTypesSuccess(data)))
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogTypesFailure after failed to fetch catalogTypes', () => {
        const store = { catalogTypeReducer: {} }
        const err = new Error()
        const res = { json: () => { throw err } }
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()
        generator.next(store)

        const result = generator.next(res)
        expect(result.value).toEqual(effects.put(actions.loadCatalogTypesFailure(err)))
        expect(result.done).toBeFalsy()
    })

    it('Should end the generator after putting to loadCatalogTypesSuccess action', () => {
        const store = { catalogTypeReducer: {} }
        const data = { 'id': 1 }
        const res = { json: () => data }
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)
        generator.next(data)

        const result = generator.next()
        expect(result.done).toBeTruthy()
    })

    it('Should end the generator after putting to loadCatalogTypesFailure action', () => {
        const store = { catalogTypeReducer: {} }
        const err = new Error()
        const res = { json: () => { throw err } }
        const generator = catalogTypeSaga.loadCatalogTypesWorker()
        
        generator.next()
        generator.next(store)
        generator.next(res)

        const result = generator.next()
        expect(result.done).toBeTruthy()
    })
})

describe('Get Selected CatalogTypes Id Worker saga', () => {
    it('Should get selected catalogTypes id', () => {
        const store = { catalogTypeReducer: { catalogTypes: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }}
        const generator = catalogTypeSaga.getSelectedCatalogTypeIds()

        generator.next()
        generator.next()
        
        const result = generator.next(store)
        expect(result.value.length).toBe(1)
        expect(result.value).toEqual([1])
    })

    it('Should end the generator after getting selected catalogTypes id', () => {
        const store = { catalogTypeReducer: { catalogTypes: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }}
        const generator = catalogTypeSaga.getSelectedCatalogTypeIds()

        generator.next()
        generator.next()

        const result =generator.next(store)
        expect(result.done).toBeTruthy()
    })
})

describe('Update Selected CatalogTypes Worker saga', () => {
    it('Should get catalogTypes from the store', () => {
        const action = { payload: { catalogType: { id: 1 }, isSelected: true } }
        const generator = catalogTypeSaga.updateSelectedCatalogType(action)
        const result = generator.next()
        expect(result.value).toEqual(effects.select())
        expect(result.done).toBeFalsy()
    })

    it('Should put action to loadCatalogTypesSuccess after updated selected catalogType', () => {
        const action = { payload: { catalogType: { id: 2 }, isSelected: true } }
        const store = { catalogTypeReducer: { catalogTypes: [{ id: 1, isSelected: true }, { id: 2, isSelected: false }] }}
        const generator = catalogTypeSaga.updateSelectedCatalogType(action)

        generator.next()
        
        const result = generator.next(store)
        expect(result.value).toEqual(effects.put(actions.loadCatalogTypesSuccess(store.catalogTypeReducer.catalogTypes)))
    })
})