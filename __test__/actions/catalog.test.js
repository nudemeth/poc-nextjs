import * as actions from '../../actions/catalog.actions';
import actionTypes from '../../actions/actionTypes';

describe('Items actions creator', () => {
    it('Should create an action to load items', () => {
        const action = { type: actionTypes.LOAD_ITEMS };
        expect(actions.loadItems()).toEqual(action);
    });

    it('Should create an action to load items success', () => {
        const items = [{ id: 1 }];
        const action = { type: actionTypes.LOAD_ITEMS_SUCCESS, payload: { items } };
        expect(actions.loadItemsSuccess(items)).toEqual(action);
    });

    it('Should create an action to load items failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_ITEMS_FAILURE, payload: { error } };
        expect(actions.loadItemsFailure(error)).toEqual(action);
    });
});

describe('Item actions creator', () => {
    it('Should create an action to load item', () => {
        const id = 1;
        const action = { type: actionTypes.LOAD_ITEM, payload: { id } };
        expect(actions.loadItem(id)).toEqual(action);
    });

    it('Should create an action to load item success', () => {
        const item = { id: 1 };
        const action = { type: actionTypes.LOAD_ITEM_SUCCESS, payload: { item } };
        expect(actions.loadItemSuccess(item)).toEqual(action);
    });

    it('Should create an action to load item failure', () => {
        const error = new Error();
        const action = { type: actionTypes.LOAD_ITEM_FAILURE, payload: { error } };
        expect(actions.loadItemFailure(error)).toEqual(action);
    });
})