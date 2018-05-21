import actionTypes from './actionTypes';

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
    return { type: actionTypes.UPDATE_SELECTED_CATEGORY, payload: { category, isSelected } };
}