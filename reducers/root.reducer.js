import { combineReducers } from 'redux';
import productReducer from './product.reducer';
import categoryReducer from './category.reducer';
import aboutReducer from './about.reducer';

export const initialState = {
    payload: {
        error: false,
        products: [],
        product: {},
        categories: []
    },
    greeting: ''
}

export default combineReducers({
    productReducer,
    categoryReducer,
    aboutReducer
});