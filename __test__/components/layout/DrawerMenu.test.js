import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { DrawerMenu } from '../../../components/layout/DrawerMenu';

const classes = {};
const theme = {};

describe('DrawerMenu component', () => {
    it('Should have 3 Link components', () => {
        const drawerMenu = shallow(<DrawerMenu classes={classes} theme={theme} />);
        expect(drawerMenu.find('Link').length).toEqual(3);
        expect(drawerMenu.find('Link').first().prop('href')).toEqual('/');
        expect(drawerMenu.find('Link').at(1).prop('href')).toEqual("/category");
        expect(drawerMenu.find('Link').last().prop('href')).toEqual("/about");
    });

    it('Should have 3 Icon components', () => {
        const drawerMenu = shallow(<DrawerMenu classes={classes} theme={theme} />);
        expect(drawerMenu.find('WithStyles(Icon)').length).toEqual(3);
        expect(drawerMenu.find('WithStyles(Icon)').first().render().text()).toEqual("home");
        expect(drawerMenu.find('WithStyles(Icon)').at(1).render().text()).toEqual("label");
        expect(drawerMenu.find('WithStyles(Icon)').last().render().text()).toEqual("info");
    });
});