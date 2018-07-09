import { combineReducers } from 'redux';
import itemReducer from './item.reducer';
import categoryReducer from './category.reducer';
import aboutReducer from './about.reducer';

export const initialState = {
    payload: {
        error: false,
        items: [],
        item: {},
        categories: []
    },
    greeting: ''
}

export default combineReducers({
    itemReducer,
    categoryReducer,
    aboutReducer
});