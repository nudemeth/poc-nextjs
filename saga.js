import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import actionTypes from './actions';
import * as actions from './actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(actions.updateGreeting('This is from client'));
}

function * loadProductsWorker() {
    const categories = yield call(getSelectedCategoryIds);
    const selectedCategories = categories.reduce((p, c) => p + 'categoryId=' + c + '&', '')
    try {
        const res = yield fetch('http://localhost:5000/products?' + selectedCategories);
        const data = yield res.json();
        yield put(actions.loadProductsSuccess(data));
    } catch(err) {
        yield put(actions.loadProductsFailure(err));
    }
}

function * loadProductWorker(action) {
    try {
        const res = yield fetch('http://localhost:5000/product/' + action.id);
        const data = yield res.json();
        yield put(actions.loadProductSuccess(data));
    } catch(err) {
        yield put(actions.loadProductFailure(err));
    }
}

function * loadCategoriesWorker() {
    const { categories } = yield select();
    if (categories.length > 0) {
        yield put(actions.loadCategoriesSuccess(categories));
    } else {
        try {
            const res = yield call(fetch, 'http://localhost:5000/categories');
            const data = yield res.json();
            yield put(actions.loadCategoriesSuccess(data));
        } catch(err) {
            yield put(actions.loadCategoriesFailure(err));
        }
    }
}

function * loadCategoriesWatcher() {
    yield takeEvery(actionTypes.LOAD_CATEGORIES, loadCategoriesWorker);
}

function * getSelectedCategoryIds() {
    const { categories } = yield select();
    if (categories.length > 0) {
        yield put.resolve(actions.loadCategoriesSuccess(categories));
    } else {
        try {
            const res = yield call(fetch, 'http://localhost:5000/categories');
            const data = yield res.json();
            yield put.resolve(actions.loadCategoriesSuccess(data));
        } catch(err) {
            yield put.resolve(actions.loadCategoriesFailure(err));
        }
    }
    const selectedCategories = yield select((state) => state.categories.filter((category) => category.isSelected).map((category) => category.id));
    return selectedCategories;
}

function * updateSelectedCategory(action) {
    const { category, isSelected } = action;
    const categories = yield call(getCategories);
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
        loadCategoriesWatcher(),
        takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, updateSelectedCategory)
    ]);
}

export default rootWatcher;