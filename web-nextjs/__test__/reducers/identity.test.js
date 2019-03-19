import { initialState } from '../../reducers/root.reducer'
import reducer from '../../reducers/identity.reducer'
import actionTypes from '../../actions/actionTypes'

describe('CatalogTypes reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState
        expect(reducer(undefined, {})).toEqual(result)
    })

    it('Should handle STORE_USER action type', () => {
        const action = { user: 'test user', type: actionTypes.STORE_USER }
        const state = {}
        const result = { user: action.user }
        expect(reducer(state, action)).toEqual(result)
    })
})