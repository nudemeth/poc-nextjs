import * as actions from '../../actions/category.actions';
import actionTypes from '../../actions/actionTypes';

describe('Categories actions creator', () => {
    it('Should create an action to load categories', () => {
        const action = { type: actionTypes.LOAD_CATEGORIES };
        expect(actions.loadCategories()).toEqual(action);
    });

    it('Should create an action to load categories success', () => {
        const categories = [{ id: 1 }];
        const action = { type: actionTypes.LOAD_CATEGORIES_SUCCESS, payload: { categories } };
        expect(actions.loadCategoriesSuccess(categories)).toEqual(action);
    });

    it('Should create an action to load categories failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_CATEGORIES_FAILURE, payload: { error } };
        expect(actions.loadCategoriesFailure(error)).toEqual(action);
    });

    it('Should create an action to update selected category', () => {
        const category = { id: 1, isSelected: false };
        const isSelected = true;
        const action = { type: actionTypes.UPDATE_SELECTED_CATEGORY, payload: { category, isSelected } };
        expect(actions.updateSelectedCategory(category, isSelected)).toEqual(action);
    });
});