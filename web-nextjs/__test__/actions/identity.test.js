import * as actions from '../../actions/identity.actions'
import actionTypes from '../../actions/actionTypes'

describe('About actions creator', () => {
    it('Should create an action to store user', () => {
        const user = 'test user'
        const action = { type: actionTypes.STORE_USER, payload: { user: user }}
        expect(actions.storeUser(user)).toEqual(action)
    })
})