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
        const wrapper = shallow(<DrawerMenu classes={classes} theme={theme} />);
        const elements = wrapper.find('Link');
        expect(elements.length).toEqual(3);
        expect(elements.first().prop('href')).toEqual('/');
        expect(elements.at(1).prop('href')).toEqual("/category");
        expect(elements.last().prop('href')).toEqual("/about");
    });

    it('Should have 3 Icon components', () => {
        const wrapper = shallow(<DrawerMenu classes={classes} theme={theme} />);
        const elements = wrapper.find('WithStyles(Icon)');
        expect(elements.length).toEqual(3);
        expect(elements.first().render().text()).toEqual("home");
        expect(elements.at(1).render().text()).toEqual("label");
        expect(elements.last().render().text()).toEqual("info");
    });
});