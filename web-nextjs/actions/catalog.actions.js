const actionTypes = {
    LOAD_ITEMS: 'LOAD_ITEMS',
    LOAD_ITEMS_SUCCESS: 'LOAD_ITEMS_SUCCESS',
    LOAD_ITEMS_FAILURE: 'LOAD_ITEMS_FAILURE',
    LOAD_ITEM: 'LOAD_ITEM',
    LOAD_ITEM_SUCCESS: 'LOAD_ITEM_SUCCESS',
    LOAD_ITEM_FAILURE: 'LOAD_ITEM_FAILURE'
}

export default actionTypes

export const loadItems = () => {
    return { type: actionTypes.LOAD_ITEMS }
}

export const loadItemsSuccess = (items) => {
    return { type: actionTypes.LOAD_ITEMS_SUCCESS, payload: { items } }
}

export const loadItemsFailure = (error) => {
    return { type: actionTypes.LOAD_ITEMS_FAILURE, payload: { error } }
}

export const loadItem = (id) => {
    return { type: actionTypes.LOAD_ITEM, payload: { id } }
}

export const loadItemSuccess = (item) => {
    return { type: actionTypes.LOAD_ITEM_SUCCESS, payload: { item } }
}

export const loadItemFailure = (error) => {
    return { type: actionTypes.LOAD_ITEM_FAILURE, payload: { error } }
}