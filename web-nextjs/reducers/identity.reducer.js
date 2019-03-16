import { initialState } from './root.reducer'
import actionTypes from '../actions/actionTypes'

export default function getAboutReducer(state = initialState, action) {
    switch (action.type) {
    case actionTypes.STORE_USER:
        return {
            ...state,
            ...{ user: action.user }
        }
    default: return state
    }
}