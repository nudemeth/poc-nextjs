import * as actions from '../../actions/catalogBrand.actions';
import actionTypes from '../../actions/actionTypes';

describe('CatalogBrands actions creator', () => {
    it('Should create an action to load catalogBrands', () => {
        const action = { type: actionTypes.LOAD_CATALOG_BRANDS };
        expect(actions.loadCatalogBrands()).toEqual(action);
    });

    it('Should create an action to load catalogBrands success', () => {
        const catalogBrands = [{ id: 1 }];
        const action = { type: actionTypes.LOAD_CATALOG_BRANDS_SUCCESS, payload: { catalogBrands } };
        expect(actions.loadCatalogBrandsSuccess(catalogBrands)).toEqual(action);
    });

    it('Should create an action to load catalogBrands failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_CATALOG_BRANDS_FAILURE, payload: { error } };
        expect(actions.loadCatalogBrandsFailure(error)).toEqual(action);
    });

    it('Should create an action to update selected catalogBrand', () => {
        const catalogBrand = { id: 1, isSelected: false };
        const isSelected = true;
        const action = { type: actionTypes.UPDATE_SELECTED_CATALOG_BRAND, payload: { catalogBrand, isSelected } };
        expect(actions.updateSelectedCatalogBrand(catalogBrand, isSelected)).toEqual(action);
    });
});