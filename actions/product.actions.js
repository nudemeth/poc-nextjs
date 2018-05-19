import actionTypes from './actionTypes';

export const loadProducts = () => {
    return { type: actionTypes.LOAD_PRODUCTS };
}

export const loadProductsSuccess = (products) => {
    return { type: actionTypes.LOAD_PRODUCTS_SUCCESS, payload: { products } };
}

export const loadProductsFailure = (error) => {
    return { type: actionTypes.LOAD_PRODUCTS_FAILURE, payload: { error } };
}

export const loadProduct = (id) => {
    return { type: actionTypes.LOAD_PRODUCT, payload: { id } };
}

export const loadProductSuccess = (product) => {
    return { type: actionTypes.LOAD_PRODUCT_SUCCESS, payload: { product } };
}

export const loadProductFailure = (error) => {
    return { type: actionTypes.LOAD_PRODUCT_FAILURE, payload: { error } };
}