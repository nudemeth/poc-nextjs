const actionTypes = {
    INIT: 'INIT',
    UPDATE: 'UPDATE',
    RESET: 'RESET',
    LOAD_PRODUCTS: 'LOAD_PRODUCTS',
    LOAD_PRODUCTS_SUCCESS: 'LOAD_PRODUCTS_SUCCESS',
    LOAD_PRODUCTS_FAILURE: 'LOAD_PRODUCTS_FAILURE',
    LOAD_PRODUCT: 'LOAD_PRODUCT',
    LOAD_PRODUCT_SUCCESS: 'LOAD_PRODUCT_SUCCESS',
    LOAD_PRODUCT_FAILURE: 'LOAD_PRODUCT_FAILURE',
    LOAD_CATEGORIES: 'LOAD_CATEGORIES',
    LOAD_CATEGORIES_SUCCESS: 'LOAD_CATEGORIES_SUCCESS',
    LOAD_CATEGORIES_FAILURE: 'LOAD_CATEGORIES_FAILURE',
    UPDATE_SELECTED_CATEGORY: 'UPDATE_SELECTED_CATEGORY'
}

export default actionTypes;

//ACTIONS
export const initGreeting = (gt) => {
    return { type: actionTypes.INIT, gt: gt};
}

export const updateGreeting = (gt) => {
    return { type: actionTypes.UPDATE, gt: gt};
}

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

export const loadCategories = () => {
    return { type: actionTypes.LOAD_CATEGORIES };
}

export const loadCategoriesSuccess = (categories) => {
    return { type: actionTypes.LOAD_CATEGORIES_SUCCESS, payload: { categories } };
}

export const loadCategoriesFailure = (error) => {
    return { type: actionTypes.LOAD_CATEGORIES_FAILURE, payload: { error } };
}

export const updateSelectedCategory = (category, isSelected) => {
    return { type: actionTypes.UPDATE_SELECTED_CATEGORY, payload: { category, isSelected } }
}