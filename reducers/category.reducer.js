import { initialState } from './root.reducer';
import actionTypes from '../actions/actionTypes';

export default function getCategoryReducer(state = initialState, action) {
    switch (action.type) {
        case actionTypes.LOAD_CATEGORIES_SUCCESS:
            return {
                ...state,
                ...{ categories: action.payload.categories }
            };
        case actionTypes.LOAD_CATEGORIES_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            };
        default: return state;
    }
}