const actionTypes = {
    STORE_USER: 'STORE_USER'
}

export default actionTypes

export const storeUser = (user) => {
    return { type: actionTypes.STORE_USER, user: user}
}