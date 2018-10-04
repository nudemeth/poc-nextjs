import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function getCatalogBrandReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.LOAD_CATALOG_BRANDS_SUCCESS:
        return {
            ...state,
            ...{ catalogBrands: action.payload.catalogBrands }
        }
    case actionTypes.LOAD_CATALOG_BRANDS_FAILURE:
        return {
            ...state,
            ...{ error: action.payload.error }
        }
    default: return state
    }
}