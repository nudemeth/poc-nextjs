import { initialState } from '../../reducers/root.reducer';
import reducer from '../../reducers/catalog.reducer';
import actionTypes from '../../actions/actionTypes';

describe('Items reducers', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState;
        expect(reducer(undefined, {})).toEqual(result);
    });

    it('Should handle LOAD_ITEMS_SUCCESS action type', () => {
        const action = { payload: { items: [] }, type: actionTypes.LOAD_ITEMS_SUCCESS };
        const state = {};
        const result = { items: action.payload.items };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_ITEMS_FAILURE action type', () => {
        const error = new Error();
        const action = { payload: { error: error }, type: actionTypes.LOAD_ITEMS_FAILURE };
        const state = {};
        const result = { error: action.payload.error };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_ITEM_SUCCESS action type', () => {
        const action = { payload: { items: {} }, type: actionTypes.LOAD_ITEM_SUCCESS };
        const state = {};
        const result = { item: action.payload.item };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_ITEM_FAILURE action type', () => {
        const error = new Error();
        const action = { payload: { error: error }, type: actionTypes.LOAD_ITEM_FAILURE };
        const state = {};
        const result = { error: action.payload.error };
        expect(reducer(state, action)).toEqual(result);
    });
});