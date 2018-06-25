import * as effects from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as productSaga from './product.saga';
import * as categorySaga from './category.saga';
import * as aboutSaga from './about.saga';

function * rootWatcher() {
    yield effects.all([
        effects.call(aboutSaga.updateGreetingSaga),
        effects.takeEvery(actionTypes.LOAD_PRODUCTS, productSaga.loadProductsWorker),
        effects.takeEvery(actionTypes.LOAD_PRODUCT, productSaga.loadProductWorker),
        effects.takeEvery(actionTypes.LOAD_CATEGORIES, categorySaga.loadCategoriesWorker),
        effects.takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, categorySaga.updateSelectedCategory)
    ]);
}

export default rootWatcher;