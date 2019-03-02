const actionTypes = {
    GET_ACCESS_TOKEN: 'GET_ACCESS_TOKEN',
    GET_ACCESS_TOKEN_SUCCESS: 'GET_ACCESS_TOKEN_SUCCESS',
    GET_ACCESS_TOKEN_FAILURE: 'GET_ACCESS_TOKEN_FAILURE'
}

export default actionTypes

export const getAccessToken = (issuer, code) => {
    return { type: actionTypes.GET_ACCESS_TOKEN, payload: { issuer, code } }
}

export const getAccessTokenSuccess = (token, type) => {
    return { type: actionTypes.GET_ACCESS_TOKEN_SUCCESS, payload: { token, type } }
}

export const getAccessTokenFailure = (error) => {
    return { type: actionTypes.GET_ACCESS_TOKEN_FAILURE, payload: { error } }
}