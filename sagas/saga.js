import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import actionTypes from '../actions/actions';
import * as actions from '../actions/actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(actions.updateGreeting('This is from client'));
}

function * loadProductsWorker() {
    const categories = yield call(getSelectedCategoryIds);
    const selectedCategories = categories.reduce((p, c) => p + 'categoryId=' + c + '&', '')
    try {
        const res = yield call(fetch, 'http://localhost:5000/products?' + selectedCategories);
        const data = yield res.json();
        yield put(actions.loadProductsSuccess(data));
    } catch(err) {
        yield put(actions.loadProductsFailure(err));
    }
}

function * loadProductWorker(action) {
    try {
        const res = yield call(fetch, 'http://localhost:5000/product/' + action.payload.id);
        const data = yield res.json();
        yield put(actions.loadProductSuccess(data));
    } catch(err) {
        yield put(actions.loadProductFailure(err));
    }
}

function * loadCategoriesWorker() {
    const { categories } = yield select();
    if (!categories || categories.length === 0) {
        try {
            const res = yield call(fetch, 'http://localhost:5000/categories');
            const data = yield res.json();
            yield put(actions.loadCategoriesSuccess(data));
        } catch(err) {
            yield put(actions.loadCategoriesFailure(err));
        }
    }
}

function * getSelectedCategoryIds() {
    yield call(loadCategoriesWorker);
    const { categories } = yield select();
    return categories.filter((category) => category.isSelected).map((category) => category.id);
}

function * updateSelectedCategory(action) {
    const { category, isSelected } = action.payload;
    const { categories } = yield select();
    category.isSelected = isSelected;
    const idx = categories.findIndex(c => c.id === category.id);
    categories[idx] = category;
    yield put(actions.loadCategoriesSuccess(categories));
}

function * rootWatcher() {
    yield all([
        call(updateGreetingSaga),
        takeEvery(actionTypes.LOAD_PRODUCTS, loadProductsWorker),
        takeEvery(actionTypes.LOAD_PRODUCT, loadProductWorker),
        takeEvery(actionTypes.LOAD_CATEGORIES, loadCategoriesWorker),
        takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, updateSelectedCategory)
    ]);
}

export default rootWatcher;