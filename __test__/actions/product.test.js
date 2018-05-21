import * as actions from '../../actions/product.actions';
import actionTypes from '../../actions/actionTypes';

describe('Products actions creator', () => {
    it('Should create an action to load products', () => {
        const action = { type: actionTypes.LOAD_PRODUCTS };
        expect(actions.loadProducts()).toEqual(action);
    });

    it('Should create an action to load products success', () => {
        const products = [{ id: 1 }];
        const action = { type: actionTypes.LOAD_PRODUCTS_SUCCESS, payload: { products } };
        expect(actions.loadProductsSuccess(products)).toEqual(action);
    });

    it('Should create an action to load products failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_PRODUCTS_FAILURE, payload: { error } };
        expect(actions.loadProductsFailure(error)).toEqual(action);
    });
});

describe('Product actions creator', () => {
    it('Should create an action to load product', () => {
        const id = 1;
        const action = { type: actionTypes.LOAD_PRODUCT, payload: { id } };
        expect(actions.loadProduct(id)).toEqual(action);
    });

    it('Should create an action to load product success', () => {
        const product = { id: 1 };
        const action = { type: actionTypes.LOAD_PRODUCT_SUCCESS, payload: { product } };
        expect(actions.loadProductSuccess(product)).toEqual(action);
    });

    it('Should create an action to load product failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_PRODUCT_FAILURE, payload: { error } };
        expect(actions.loadProductFailure(error)).toEqual(action);
    });
})