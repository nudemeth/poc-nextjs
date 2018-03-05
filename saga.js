import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import { actionTypes, updateGreeting, loadProductsFailure, loadProductsSuccess } from './actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(updateGreeting('This is from client'));
}

function * loadProductsWorker() {
    try {
        const res = yield fetch('http://localhost:5000/api/v1/products');
        const data = yield res.json();
        yield put(loadProductsSuccess(data));
    } catch(err) {
        yield put(loadProductsFailure(err));
    }
}

function * rootWatcher() {
    yield all([
        call(updateGreetingSaga),
        takeLatest(actionTypes.LOAD_PRODUCTS, loadProductsWorker)
    ]);
}

export default rootWatcher;