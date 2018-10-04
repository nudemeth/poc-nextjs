import React from 'react'
import { shallow } from 'enzyme'
import { DrawerMenu } from '../../../components/layout/DrawerMenu'

const classes = {}
const theme = {}

describe('DrawerMenu component', () => {
    it('Should have 3 Link components', () => {
        const wrapper = shallow(<DrawerMenu classes={classes} theme={theme} />)
        const elements = wrapper.find('Link')
        expect(elements.length).toEqual(4)
        expect(elements.first().prop('href')).toEqual('/')
        expect(elements.at(1).prop('href')).toEqual('/catalogType')
        expect(elements.at(2).prop('href')).toEqual('/catalogBrand')
        expect(elements.last().prop('href')).toEqual('/about')
    })

    it('Should have 3 Icon components', () => {
        const wrapper = shallow(<DrawerMenu classes={classes} theme={theme} />)
        const elements = wrapper.find('WithStyles(Icon)')
        expect(elements.length).toEqual(4)
        expect(elements.first().render().text()).toEqual('home')
        expect(elements.at(1).render().text()).toEqual('label')
        expect(elements.at(2).render().text()).toEqual('label')
        expect(elements.last().render().text()).toEqual('info')
    })
})