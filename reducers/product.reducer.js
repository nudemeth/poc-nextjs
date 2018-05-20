import { initialState } from './root.reducer';
import actionTypes from '../actions/actionTypes';

export default function getProductReducer(state = initialState, action) {
    switch (action.type) {
        case actionTypes.LOAD_PRODUCTS_SUCCESS:
            return {
                ...state,
                ...{ products: action.payload.products }
            };
        case actionTypes.LOAD_PRODUCTS_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            };
        case actionTypes.LOAD_PRODUCT_SUCCESS:
            return {
                ...state,
                ...{ product: action.payload.product }
            };
        case actionTypes.LOAD_PRODUCT_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            };
        default: return state;
    }
}