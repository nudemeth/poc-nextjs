import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import actionTypes from './actions';
import * as actions from './actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(actions.updateGreeting('This is from client'));
}

function * loadProductsWorker() {
    try {
        const res = yield fetch('http://localhost:5000/products');
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
    try {
        const res = yield fetch('http://localhost:5000/categories');
        const data = yield res.json();
        yield put(actions.loadCategoriesSuccess(data));
    } catch(err) {
        yield put(actions.loadCategoriesFailure(err));
    }
}

function * rootWatcher() {
    yield all([
        call(updateGreetingSaga),
        takeLatest(actionTypes.LOAD_PRODUCTS, loadProductsWorker),
        takeEvery(actionTypes.LOAD_PRODUCT, loadProductWorker),
        takeLatest(actionTypes.LOAD_CATEGORIES, loadCategoriesWorker)
    ]);
}

export default rootWatcher;