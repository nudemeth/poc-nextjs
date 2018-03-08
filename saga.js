import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import { actionTypes, updateGreeting, loadProductsFailure, loadProductsSuccess, loadProductSuccess, loadProductFailure } from './actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(updateGreeting('This is from client'));
}

function * loadProductsWorker() {
    try {
        const res = yield fetch('http://localhost:5000/products');
        const data = yield res.json();
        yield put(loadProductsSuccess(data));
    } catch(err) {
        yield put(loadProductsFailure(err));
    }
}

function * loadProductWorker(action) {
    try {
        const res = yield fetch('http://localhost:5000/product/' + action.id);
        const data = yield res.json();
        yield put(loadProductSuccess(data));
    } catch(err) {
        yield put(loadProductFailure(err));
    }
}

function * rootWatcher() {
    yield all([
        call(updateGreetingSaga),
        takeLatest(actionTypes.LOAD_PRODUCTS, loadProductsWorker),
        takeEvery(actionTypes.LOAD_PRODUCT, loadProductWorker)
    ]);
}

export default rootWatcher;