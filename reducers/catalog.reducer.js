import { initialState } from './root.reducer';
import actionTypes from '../actions/actionTypes';

export default function getCatalogReducer(state = initialState, action) {
    switch (action.type) {
        case actionTypes.LOAD_ITEMS_SUCCESS:
            return {
                ...state,
                ...{ items: action.payload.items }
            };
        case actionTypes.LOAD_ITEMS_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            };
        case actionTypes.LOAD_ITEM_SUCCESS:
            return {
                ...state,
                ...{ item: action.payload.item }
            };
        case actionTypes.LOAD_ITEM_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            };
        default: return state;
    }
}