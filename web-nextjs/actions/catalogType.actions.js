const actionTypes = {
    LOAD_CATALOG_TYPES: 'LOAD_CATALOG_TYPES',
    LOAD_CATALOG_TYPES_SUCCESS: 'LOAD_CATALOG_TYPES_SUCCESS',
    LOAD_CATALOG_TYPES_FAILURE: 'LOAD_CATALOG_TYPES_FAILURE',
    UPDATE_SELECTED_CATALOG_TYPE: 'UPDATE_SELECTED_CATALOG_TYPE'
}

export default actionTypes

export const loadCatalogTypes = () => {
    return { type: actionTypes.LOAD_CATALOG_TYPES }
}

export const loadCatalogTypesSuccess = (catalogTypes) => {
    return { type: actionTypes.LOAD_CATALOG_TYPES_SUCCESS, payload: { catalogTypes } }
}

export const loadCatalogTypesFailure = (error) => {
    return { type: actionTypes.LOAD_CATALOG_TYPES_FAILURE, payload: { error } }
}

export const updateSelectedCatalogType = (catalogType, isSelected) => {
    return { type: actionTypes.UPDATE_SELECTED_CATALOG_TYPE, payload: { catalogType, isSelected } }
}