import actionTypes from './actionTypes';

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