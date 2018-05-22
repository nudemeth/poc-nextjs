import { initialState } from '../../reducers/root.reducer';
import reducer from '../../reducers/category.reducer';
import actionTypes from '../../actions/actionTypes';

describe('Categories reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState;
        expect(reducer(undefined, {})).toEqual(result);
    });

    it('Should handle LOAD_CATEGORIES_SUCCESS action type', () => {
        const action = { payload: { categories: [] }, type: actionTypes.LOAD_CATEGORIES_SUCCESS };
        const state = {};
        const result = { categories: action.payload.categories };
        expect(reducer(state, action)).toEqual(result);
    });

    it('Should handle LOAD_CATEGORIES_FAILURE action type', () => {
        const error = new Error();
        const action = { payload: { error: error }, type: actionTypes.LOAD_CATEGORIES_FAILURE };
        const state = {};
        const result = { error: action.payload.error };
        expect(reducer(state, action)).toEqual(result);
    });
});