import { initialState } from '../../reducers/root.reducer'
import reducer from '../../reducers/catalogType.reducer'
import actionTypes from '../../actions/actionTypes'

describe('CatalogTypes reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState
        expect(reducer(undefined, {})).toEqual(result)
    })

    it('Should handle LOAD_CATALOG_TYPES_SUCCESS action type', () => {
        const action = { payload: { catalogTypes: [] }, type: actionTypes.LOAD_CATALOG_TYPES_SUCCESS }
        const state = {}
        const result = { catalogTypes: action.payload.catalogTypes }
        expect(reducer(state, action)).toEqual(result)
    })

    it('Should handle LOAD_CATALOG_TYPES_FAILURE action type', () => {
        const error = new Error()
        const action = { payload: { error: error }, type: actionTypes.LOAD_CATALOG_TYPES_FAILURE }
        const state = {}
        const result = { error: action.payload.error }
        expect(reducer(state, action)).toEqual(result)
    })
})