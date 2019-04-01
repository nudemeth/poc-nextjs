import React from 'react'
import { shallow } from 'enzyme'
import { LoginCard } from '../../../../components/page/login/LoginCard'

const classes = {}
const theme = {}

describe('LoginCard component', () => {
    it('Should show "Sign in to your account" label', () => {
        const wrapper = shallow(<LoginCard classes={classes} theme={theme} sites={[]} />)
        expect(wrapper.find('WithStyles(Typography)').render().text()).toEqual('Sign in to your account')
    })

    it('Should show Username and Password textbox', () => {
        const wrapper = shallow(<LoginCard classes={classes} theme={theme} sites={[]} />)
        const elements = wrapper.find('TextField')
        expect(elements.length).toEqual(2)
        expect(elements.first().prop('id')).toEqual('username')
        expect(elements.first().prop('label')).toEqual('Username')
        expect(elements.last().prop('id')).toEqual('password')
        expect(elements.last().prop('type')).toEqual('password')
        expect(elements.last().prop('label')).toEqual('Password')
    })

    it('Should show "Sign In" button', () => {
        const wrapper = shallow(<LoginCard classes={classes} theme={theme} sites={[]} />)
        expect(wrapper.find('WithStyles(Button)').first().render().text()).toEqual('Sign In')
    })

    it('Should show "Sign In With Github" button', () => {
        const wrapper = shallow(<LoginCard classes={classes} theme={theme} sites={[]} />)
        expect(wrapper.find('WithStyles(Button)').last().render().text()).toEqual('Sign In With Github')
    })
})