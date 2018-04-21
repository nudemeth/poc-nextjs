import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { SideBar } from '../../../components/layout/SideBar';

const classes = {
    appBar: {},
    menuButton: {},
    flex: {}
}
const theme = {}

describe('SideBar component', () => {
    it('Should have 2 Drawer components', () => {
        const sidebar = shallow(<SideBar classes={classes} theme={theme} />);
        expect(sidebar.find('WithStyles(Drawer)').length).toEqual(2);
        expect(sidebar.find('WithStyles(Drawer)').first().dive().find('Drawer').exists()).toBeTruthy();
    });
});