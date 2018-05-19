import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as productSaga from './product.saga';
import * as categorySaga from './category.saga';
import * as aboutSaga from './about.saga';

function * rootWatcher() {
    yield all([
        call(aboutSaga.updateGreetingSaga),
        takeEvery(actionTypes.LOAD_PRODUCTS, productSaga.loadProductsWorker),
        takeEvery(actionTypes.LOAD_PRODUCT, productSaga.loadProductWorker),
        takeEvery(actionTypes.LOAD_CATEGORIES, categorySaga.loadCategoriesWorker),
        takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, categorySaga.updateSelectedCategory)
    ]);
}

export default rootWatcher;