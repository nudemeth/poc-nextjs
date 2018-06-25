import * as effects from 'redux-saga/effects';
import * as actions from '../actions/category.actions';
import categoryApi from '../api/category.api';

function * loadCategoriesWorker() {
    const { categoryReducer: { categories } } = yield effects.select();
    if (!categories || categories.length === 0) {
        try {
            const res = yield effects.call(categoryApi.getCategories);
            const data = yield res.json();
            yield effects.put(actions.loadCategoriesSuccess(data));
        } catch(err) {
            yield effects.put(actions.loadCategoriesFailure(err));
        }
    }
}

function * getSelectedCategoryIds() {
    yield effects.call(loadCategoriesWorker);
    const { categoryReducer: { categories } } = yield effects.select();
    return categories.filter((category) => category.isSelected).map((category) => category.id);
}

function * updateSelectedCategory(action) {
    const { category, isSelected } = action.payload;
    const { categoryReducer: { categories } } = yield effects.select();
    category.isSelected = isSelected;
    const idx = categories.findIndex(c => c.id === category.id);
    categories[idx] = category;
    yield effects.put(actions.loadCategoriesSuccess(categories));
}

export { getSelectedCategoryIds };
export { loadCategoriesWorker };
export { updateSelectedCategory };