import { initialState } from '../../reducers/root.reducer'
import reducer from '../../reducers/identity.reducer'
import actionTypes from '../../actions/actionTypes'

describe('CatalogTypes reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState
        expect(reducer(undefined, {})).toEqual(result)
    })

    it('Should handle STORE_ACCESS_TOKEN action type', () => {
        const action = { payload: { accessToken: 'access token' }, type: actionTypes.STORE_ACCESS_TOKEN }
        const state = {}
        const result = { accessToken: action.payload.accessToken }
        expect(reducer(state, action)).toEqual(result)
    })

    it('Should handle STORE_AUTH_SITES action type', () => {
        const action = { payload: { sites: [ { name: 'github', url: 'https://github.com/login/oauth/authorize'} ] }, type: actionTypes.STORE_AUTH_SITES }
        const state = {}
        const result = { sites: action.payload.sites }
        expect(reducer(state, action)).toEqual(result)
    })
})