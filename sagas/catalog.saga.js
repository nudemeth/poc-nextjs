import * as effects from 'redux-saga/effects';
import * as actions from '../actions/catalog.actions';
import catalogApi from '../api/catalog.api';
import * as categorySaga from './category.saga';

function * loadItemsWorker() {
    try {
        const categories = yield effects.call(categorySaga.getSelectedCategoryIds);
        const res = yield effects.call(catalogApi.getItems, categories);
        const data = yield res.json();
        yield effects.put(actions.loadItemsSuccess(data));
    } catch(err) {
        yield effects.put(actions.loadItemsFailure(err));
    }
}

function * loadItemWorker(action) {
    try {
        const res = yield effects.call(catalogApi.getItemById, action.payload.id);
        const data = yield res.json();
        yield effects.put(actions.loadItemSuccess(data));
    } catch(err) {
        yield effects.put(actions.loadItemFailure(err));
    }
}

export { loadItemsWorker };
export { loadItemWorker };