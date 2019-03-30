import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function identityReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.STORE_USER:
        return {
            ...state,
            ...{ user: action.payload.user }
        }
    case actionTypes.STORE_AUTH_SITES:
        return {
            ...state,
            ...{ sites: action.payload.sites }
        }
    default: return state
    }
}