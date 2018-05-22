import { initialState } from '../../reducers/root.reducer';
import reducer from '../../reducers/product.reducer';
import actionTypes from '../../actions/actionTypes';

describe('Products reducers', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState;
        expect(reducer(undefined, {})).toEqual(result);
    });

    it('Should handle LOAD_PRODUCTS_SUCCESS action type', () => {
        const action = { payload: { products: [] }, type: actionTypes.LOAD_PRODUCTS_SUCCESS };
        const state = {};
        const result = { products: action.payload.products };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_PRODUCTS_FAILURE action type', () => {
        const error = new Error();
        const action = { payload: { error: error }, type: actionTypes.LOAD_PRODUCTS_FAILURE };
        const state = {};
        const result = { error: action.payload.error };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_PRODUCT_SUCCESS action type', () => {
        const action = { payload: { products: {} }, type: actionTypes.LOAD_PRODUCT_SUCCESS };
        const state = {};
        const result = { product: action.payload.product };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_PRODUCT_FAILURE action type', () => {
        const error = new Error();
        const action = { payload: { error: error }, type: actionTypes.LOAD_PRODUCT_FAILURE };
        const state = {};
        const result = { error: action.payload.error };
        expect(reducer(state, action)).toEqual(result);
    });
});