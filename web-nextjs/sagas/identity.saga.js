import * as effects from 'redux-saga/effects'
import * as actions from '../actions/identity.actions'
import identityApi from '../api/identity.api'

function * getAccessTokenWorker(action) {
    const { issuer, code } = action.payload
    try {
        const res = yield effects.call(identityApi.getAccessToken, issuer, code)
        const data = yield res.json()
        yield effects.put(actions.getAccessTokenSuccess(data.access_token, data.token_type))
    } catch(err) {
        yield effects.put(actions.getAccessTokenFailure(err))
    }
}

export { getAccessTokenWorker }