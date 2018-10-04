import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function getCatalogTypeReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.LOAD_CATALOG_TYPES_SUCCESS:
        return {
            ...state,
            ...{ catalogTypes: action.payload.catalogTypes }
        }
    case actionTypes.LOAD_CATALOG_TYPES_FAILURE:
        return {
            ...state,
            ...{ error: action.payload.error }
        }
    default: return state
    }
}