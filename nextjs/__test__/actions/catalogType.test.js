import * as actions from '../../actions/catalogType.actions';
import actionTypes from '../../actions/actionTypes';

describe('CatalogTypes actions creator', () => {
    it('Should create an action to load catalogTypes', () => {
        const action = { type: actionTypes.LOAD_CATALOG_TYPES };
        expect(actions.loadCatalogTypes()).toEqual(action);
    });

    it('Should create an action to load catalogTypes success', () => {
        const catalogTypes = [{ id: 1 }];
        const action = { type: actionTypes.LOAD_CATALOG_TYPES_SUCCESS, payload: { catalogTypes } };
        expect(actions.loadCatalogTypesSuccess(catalogTypes)).toEqual(action);
    });

    it('Should create an action to load catalogTypes failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_CATALOG_TYPES_FAILURE, payload: { error } };
        expect(actions.loadCatalogTypesFailure(error)).toEqual(action);
    });

    it('Should create an action to update selected catalogType', () => {
        const catalogType = { id: 1, isSelected: false };
        const isSelected = true;
        const action = { type: actionTypes.UPDATE_SELECTED_CATALOG_TYPE, payload: { catalogType, isSelected } };
        expect(actions.updateSelectedCatalogType(catalogType, isSelected)).toEqual(action);
    });
});