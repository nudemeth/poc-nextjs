const actionTypes = {
    STORE_ACCESS_TOKEN: 'STORE_ACCESS_TOKEN',
    STORE_AUTH_SITES: 'STORE_AUTH_SITES',
    REVOKE_ACCESS_TOKEN: 'REVOKE_ACCESS_TOKEN',
}

export default actionTypes

export const storeAccessToken = (accessToken) => {
    return { type: actionTypes.STORE_ACCESS_TOKEN, payload: { accessToken }}
}

export const storeAuthSites = (sites) => {
    return { type: actionTypes.STORE_AUTH_SITES, payload: { sites } }
}

export const revokeAccessToken = () => {
    return { type: actionTypes.REVOKE_ACCESS_TOKEN, payload: { accessToken: null }}
}