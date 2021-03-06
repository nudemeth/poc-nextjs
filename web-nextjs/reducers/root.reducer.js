import { combineReducers } from 'redux'
import catalogReducer from './catalog.reducer'
import catalogTypeReducer from './catalogType.reducer'
import catalogBrandReducer from './catalogBrand.reducer'
import aboutReducer from './about.reducer'
import identityReducer from './identity.reducer'

export const initialState = {
    error: false,
    items: [],
    item: {},
    catalogTypes: [],
    accessToken: null,
    sites: [],
    greeting: ''
}

export default combineReducers({
    catalogReducer,
    catalogTypeReducer,
    catalogBrandReducer,
    aboutReducer,
    identityReducer
})