export const actionTypes = {
    INIT: 'INIT',
    UPDATE: 'UPDATE',
    RESET: 'RESET'
}

//ACTIONS
export const initGreeting = (gt) => {
    return { type: actionTypes.INIT, gt: gt};
}

export const updateGreeting = (gt) => {
    return { type: actionTypes.UPDATE, gt: gt};
}