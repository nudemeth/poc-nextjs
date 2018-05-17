import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import actionTypes from '../actions/actions';
import * as productSaga from './product.saga';
import * as categorySaga from './category.saga';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(actions.updateGreeting('This is from client'));
}

function * rootWatcher() {
    yield all([
        call(updateGreetingSaga),
        takeEvery(actionTypes.LOAD_PRODUCTS, productSaga.loadProductsWorker),
        takeEvery(actionTypes.LOAD_PRODUCT, productSaga.loadProductWorker),
        takeEvery(actionTypes.LOAD_CATEGORIES, categorySaga.loadCategoriesWorker),
        takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, categorySaga.updateSelectedCategory)
    ]);
}

export default rootWatcher;