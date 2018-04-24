import actionTypes from '../actions/actions';

export const initialState = {
    payload: {
        error: false,
        products: [],
        product: {},
        categories: []
    },
    greeting: ''
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
                ...{ products: action.payload.products }
            }
        case actionTypes.LOAD_PRODUCTS_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            }
        case actionTypes.LOAD_PRODUCT_SUCCESS:
            return {
                ...state,
                ...{ product: action.payload.product }
            }
        case actionTypes.LOAD_PRODUCT_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            }
        case actionTypes.LOAD_CATEGORIES_SUCCESS:
            return {
                ...state,
                ...{ categories: action.payload.categories }
            }
        case actionTypes.LOAD_CATEGORIES_FAILURE:
            return {
                ...state,
                ...{ error: action.payload.error }
            }
        case actionTypes.RESET:
            return initialState;
        default:
            return state;
    }
}

export default rootReducer;