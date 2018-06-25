import * as effects from 'redux-saga/effects';
import * as actions from '../actions/product.actions';
import productApi from '../api/product.api';
import * as categorySaga from './category.saga';

function * loadProductsWorker() {
    try {
        const categories = yield effects.call(categorySaga.getSelectedCategoryIds);
        const res = yield effects.call(productApi.getProducts, categories);
        const data = yield res.json();
        yield effects.put(actions.loadProductsSuccess(data));
    } catch(err) {
        yield effects.put(actions.loadProductsFailure(err));
    }
}

function * loadProductWorker(action) {
    try {
        const res = yield effects.call(productApi.getProduct, action.payload.id);
        const data = yield res.json();
        yield effects.put(actions.loadProductSuccess(data));
    } catch(err) {
        yield effects.put(actions.loadProductFailure(err));
    }
}

export { loadProductsWorker };
export { loadProductWorker };