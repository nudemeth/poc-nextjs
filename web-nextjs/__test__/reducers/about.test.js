import { initialState } from '../../reducers/root.reducer'
import reducer from '../../reducers/about.reducer'
import actionTypes from '../../actions/actionTypes'

describe('About reducers tests', () => {
    it('Should return the initial state when no matched action type', () => {
        const result = initialState
        expect(reducer(undefined, {})).toEqual(result)
    })

    it('Should handle INIT action type', () => {
        const action = { gt: 'Hello World!', type: actionTypes.INIT }
        const state = {}
        const result = { greeting: action.gt }
        expect(reducer(state, action)).toEqual(result)
    })

    it('Should handle UPDATE action type', () => {
        const action = { gt: 'Hello World!', type: actionTypes.UPDATE }
        const state = {}
        const result = { greeting: action.gt }
        expect(reducer(state, action)).toEqual(result)
    })
})