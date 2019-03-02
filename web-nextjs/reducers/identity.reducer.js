import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function getAccessTokenReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.GET_ACCESS_TOKEN_SUCCESS:
        return {
            ...state,
            ...{ token: action.payload.token, type: action.payload.type }
        }
    case actionTypes.GET_ACCESS_TOKEN_FAILURE:
        return {
            ...state,
            ...{ error: action.payload.error }
        }
    default: return state
    }
}