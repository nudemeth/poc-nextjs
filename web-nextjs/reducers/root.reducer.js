import { combineReducers } from "redux"
import catalogReducer from "./catalog.reducer"
import catalogTypeReducer from "./catalogType.reducer"
import catalogBrandReducer from "./catalogBrand.reducer"
import aboutReducer from "./about.reducer"

export const initialState = {
    payload: {
        error: false,
        items: [],
        item: {},
        catalogTypes: []
    },
    greeting: ""
}

export default combineReducers({
    catalogReducer,
    catalogTypeReducer,
    catalogBrandReducer,
    aboutReducer
})