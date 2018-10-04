const actionTypes = {
    LOAD_CATALOG_BRANDS: 'LOAD_CATALOG_BRANDS',
    LOAD_CATALOG_BRANDS_SUCCESS: 'LOAD_CATALOG_BRANDS_SUCCESS',
    LOAD_CATALOG_BRANDS_FAILURE: 'LOAD_CATALOG_BRANDS_FAILURE',
    UPDATE_SELECTED_CATALOG_BRAND: 'UPDATE_SELECTED_CATALOG_BRAND'
}

export default actionTypes

export const loadCatalogBrands = () => {
    return { type: actionTypes.LOAD_CATALOG_BRANDS }
}

export const loadCatalogBrandsSuccess = (catalogBrands) => {
    return { type: actionTypes.LOAD_CATALOG_BRANDS_SUCCESS, payload: { catalogBrands } }
}

export const loadCatalogBrandsFailure = (error) => {
    return { type: actionTypes.LOAD_CATALOG_BRANDS_FAILURE, payload: { error } }
}

export const updateSelectedCatalogBrand = (catalogBrand, isSelected) => {
    return { type: actionTypes.UPDATE_SELECTED_CATALOG_BRAND, payload: { catalogBrand, isSelected } }
}