import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import actionTypes from '../actions/actions';
import * as actions from '../actions/actions';
import productApi from '../api/product.api';
import * as categorySaga from './category.saga';

function * loadProductsWorker() {
    try {
        const categories = yield call(categorySaga.getSelectedCategoryIds);
        const res = yield call(productApi.getProducts, categories);
        const data = yield res.json();
        yield put(actions.loadProductsSuccess(data));
    } catch(err) {
        yield put(actions.loadProductsFailure(err));
    }
}

function * loadProductWorker(action) {
    try {
        const res = yield call(productApi.getProduct, action.payload.id);
        const data = yield res.json();
        yield put(actions.loadProductSuccess(data));
    } catch(err) {
        yield put(actions.loadProductFailure(err));
    }
}

export { loadProductsWorker };
export { loadProductWorker };