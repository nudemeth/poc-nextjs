import { initialState } from '../../reducers/root.reducer'
import reducer from '../../reducers/catalogBrand.reducer'
import actionTypes from '../../actions/actionTypes'

describe('CatalogBrands reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState
        expect(reducer(undefined, {})).toEqual(result)
    })

    it('Should handle LOAD_CATALOG_BRANDS_SUCCESS action type', () => {
        const action = { payload: { catalogBrands: [] }, type: actionTypes.LOAD_CATALOG_BRANDS_SUCCESS }
        const state = {}
        const result = { catalogBrands: action.payload.catalogBrands }
        expect(reducer(state, action)).toEqual(result)
    })

    it('Should handle LOAD_CATALOG_BRANDS_FAILURE action type', () => {
        const error = new Error()
        const action = { payload: { error: error }, type: actionTypes.LOAD_CATALOG_BRANDS_FAILURE }
        const state = {}
        const result = { error: action.payload.error }
        expect(reducer(state, action)).toEqual(result)
    })
})