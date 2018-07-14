import * as effects from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as catalogSaga from './catalog.saga';
import * as catalogTypeSaga from './catalogType.saga';
import * as catalogBrandSaga from './catalogBrand.saga';
import * as aboutSaga from './about.saga';

function * rootWatcher() {
    yield effects.all([
        effects.call(aboutSaga.updateGreetingSaga),
        effects.takeEvery(actionTypes.LOAD_ITEMS, catalogSaga.loadItemsWorker),
        effects.takeEvery(actionTypes.LOAD_ITEM, catalogSaga.loadItemWorker),
        effects.takeEvery(actionTypes.LOAD_CATALOG_TYPES, catalogTypeSaga.loadCatalogTypesWorker),
        effects.takeEvery(actionTypes.UPDATE_SELECTED_CATALOG_TYPE, catalogTypeSaga.updateSelectedCatalogType),
        effects.takeEvery(actionTypes.LOAD_CATALOG_BRANDS, catalogBrandSaga.loadCatalogBrandsWorker),
        effects.takeEvery(actionTypes.UPDATE_SELECTED_CATALOG_BRAND, catalogBrandSaga.updateSelectedCatalogBrand)
    ]);
}

export default rootWatcher;