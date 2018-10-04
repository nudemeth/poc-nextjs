import React from 'react'
import { shallow } from 'enzyme'
import { SideBar } from '../../../components/layout/SideBar'

const classes = {}
const theme = {}

describe('SideBar component', () => {
    it('Should have 2 Drawer components', () => {
        const wrapper = shallow(<SideBar classes={classes} theme={theme} menuOpen={true} handleDrawerToggle={()=>{}} />)
        expect(wrapper.find('WithStyles(Drawer)').length).toEqual(2)
        expect(wrapper.find('WithStyles(Drawer)').first().dive().find('Drawer').exists()).toBeTruthy()
    })
})