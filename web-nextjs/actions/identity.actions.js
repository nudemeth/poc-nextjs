const actionTypes = {
    STORE_USER: 'STORE_USER',
    STORE_AUTH_SITES: 'STORE_AUTH_SITES'
}

export default actionTypes

export const storeUser = (user) => {
    return { type: actionTypes.STORE_USER, user: user}
}

export const storeAuthSites = (sites) => {
    return { type: actionTypes.STORE_AUTH_SITES, sites: sites }
}