import { actionTypes } from './actions';

export const initialState = {
    greeting: ''
}

//REDUCER
const rootReducer = (state = initialState, action) => {
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
        case actionTypes.RESET:
            return initialState;
        default:
            return state;
    }
}

export default rootReducer;