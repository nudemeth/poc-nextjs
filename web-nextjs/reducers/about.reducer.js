import { initialState } from './root.reducer';
import actionTypes from '../actions/actionTypes';

export default function getAboutReducer(state = initialState, action) {
    switch (action.type) {
        case actionTypes.INIT:
            return {
                ...state,
                ...{ greeting: action.gt }
            };
        case actionTypes.UPDATE:
            return {
                ...state,
                ...{ greeting: action.gt }
            };
        default: return state;
    }
}