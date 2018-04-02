import actionTypes from './actions';

export const initialState = {
    greeting: '',
    error: false,
    products: [],
    product: {},
    categories: []
}

//REDUCER
const rootReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.INIT:
            return {
                ...state,
                ...{ greeting: action.gt }
            };
        case actionTypes.UPDATE:
            return {
                ...state,
                ...{ greeting: action.gt }
            };
        case actionTypes.LOAD_PRODUCTS_SUCCESS:
            return {
                ...state,
                ...{ products: action.products }
            }
        case actionTypes.LOAD_PRODUCTS_FAILURE:
            return {
                ...state,
                ...{ error: action.error }
            }
        case actionTypes.LOAD_PRODUCT_SUCCESS:
            return {
                ...state,
                ...{ product: action.product }
            }
        case actionTypes.LOAD_PRODUCT_FAILURE:
            return {
                ...state,
                ...{ error: action.error }
            }
        case actionTypes.LOAD_CATEGORIES_SUCCESS:
            return {
                ...state,
                ...{ categories: action.categories }
            }
        case actionTypes.LOAD_CATEGORIES_FAILURE:
            return {
                ...state,
                ...{ error: action.error }
            }
        case actionTypes.RESET:
            return initialState;
        default:
            return state;
    }
}

export default rootReducer;