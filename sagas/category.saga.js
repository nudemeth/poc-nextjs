import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import actionTypes from '../actions/actionTypes';
import * as actions from '../actions/category.actions';
import categoryApi from '../api/category.api';

function * loadCategoriesWorker() {
    const { categoryReducer: { categories } } = yield select();
    if (!categories || categories.length === 0) {
        try {
            const res = yield call(categoryApi.getCategories);
            const data = yield res.json();
            yield put(actions.loadCategoriesSuccess(data));
        } catch(err) {
            yield put(actions.loadCategoriesFailure(err));
        }
    }
}

function * getSelectedCategoryIds() {
    yield call(loadCategoriesWorker);
    const { categoryReducer: { categories } } = yield select();
    return categories.filter((category) => category.isSelected).map((category) => category.id);
}

function * updateSelectedCategory(action) {
    const { category, isSelected } = action.payload;
    const { categoryReducer: { categories } } = yield select();
    category.isSelected = isSelected;
    const idx = categories.findIndex(c => c.id === category.id);
    categories[idx] = category;
    yield put(actions.loadCategoriesSuccess(categories));
}

export { getSelectedCategoryIds };
export { loadCategoriesWorker };
export { updateSelectedCategory };