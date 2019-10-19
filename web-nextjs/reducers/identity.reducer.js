import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function identityReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.STORE_ACCESS_TOKEN:
        return {
            ...state,
            ...{ accessToken: action.payload.accessToken }
        }
    case actionTypes.STORE_AUTH_SITES:
        return {
            ...state,
            ...{ sites: action.payload.sites }
        }
    case actionTypes.REVOKE_ACCESS_TOKEN:
        return {
            ...state,
            ...{ accessToken: action.payload.accessToken }
        }
    default: return state
    }
}