export const actionTypes = {
    INIT: 'INIT',
    UPDATE: 'UPDATE'
}

//ACTIONS
export const initGreeting = (gt) => {
    return { type: actionTypes.INIT, gt: gt};
}

export const updateGreeting = () => {
    return { type: actionTypes.UPDATE, gt: 'This is from client'};
}