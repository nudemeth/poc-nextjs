import * as actions from '../../actions/identity.actions'
import actionTypes from '../../actions/actionTypes'

describe('About actions creator', () => {
    it('Should create an action to store access token', () => {
        const accessToken = 'access token'
        const action = { type: actionTypes.STORE_ACCESS_TOKEN, payload: { accessToken: accessToken }}
        expect(actions.storeAccessToken(accessToken)).toEqual(action)
    })
})