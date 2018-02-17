import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';

const appInitialState = {
    greeting: 'This is from server'
}

export const actionTypes = {
    UPDATE: 'UPDATE'
}

//REDUCER
export const reducer = (state = appInitialState, action) => {
    switch (action.type) {
        case actionTypes.UPDATE:
            return Object.assign({}, state, { greeting: action.gt });
        default:
            return state;
    }
}

//ACTIONS
export const initGreeting = (gt) => dispatch => {
    return dispatch({ type: actionTypes.UPDATE, gt: gt});
}

export const updateGreeting = () => dispatch => {
    return setTimeout(() => dispatch({ type: actionTypes.UPDATE, gt: 'This is from client'}), 3000);
}

export const initStore = (initialState = appInitialState) => {
    return createStore(reducer, initialState, applyMiddleware(thunkMiddleware));
}