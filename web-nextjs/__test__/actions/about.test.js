import * as actions from '../../actions/about.actions'
import actionTypes from '../../actions/actionTypes'

describe('About actions creator', () => {
    it('Should create an action to init greeting', () => {
        const gt = 'Hello World!'
        const action = { type: actionTypes.INIT, gt: gt}
        expect(actions.initGreeting(gt)).toEqual(action)
    })

    it('Should create an action to update greeting', () => {
        const gt = 'Hello World!'
        const action = { type: actionTypes.UPDATE, gt: gt}
        expect(actions.updateGreeting(gt)).toEqual(action)
    })
})