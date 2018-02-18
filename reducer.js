import { actionTypes } from './actions';

export const appInitialState = {
    greeting: 'sdf'
}

//REDUCER
const reducer = (state = appInitialState, action) => {
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
        default:
            return state;
    }
}

export default reducer;