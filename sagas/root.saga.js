import * as effects from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as catalogSaga from './catalog.saga';
import * as categorySaga from './category.saga';
import * as aboutSaga from './about.saga';

function * rootWatcher() {
    yield effects.all([
        effects.call(aboutSaga.updateGreetingSaga),
        effects.takeEvery(actionTypes.LOAD_ITEMS, catalogSaga.loadItemsWorker),
        effects.takeEvery(actionTypes.LOAD_ITEM, catalogSaga.loadItemWorker),
        effects.takeEvery(actionTypes.LOAD_CATEGORIES, categorySaga.loadCategoriesWorker),
        effects.takeEvery(actionTypes.UPDATE_SELECTED_CATEGORY, categorySaga.updateSelectedCategory)
    ]);
}

export default rootWatcher;