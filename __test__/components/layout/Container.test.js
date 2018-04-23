import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { Container } from '../../../components/layout/Container';

const classes = {};
const theme = {};

describe('Container component', () => {
    it('Should show child component', () => {
        const wrapper = shallow(<Container classes={classes} theme={theme}><h1>This is child component</h1></Container>);
        const element = wrapper.find('main');
        expect(element.exists()).toBeTruthy();
        expect(element.children().find('h1').exists()).toBeTruthy();
        expect(element.text()).toEqual('This is child component');
    });

    it('Should show title', () => {
        const wrapper = shallow(<Container classes={classes} theme={theme} title="This is title" />);
        const element = wrapper.find('title');
        expect(element.exists()).toBeTruthy();
        expect(element.text()).toEqual('This is title');
    });

    it('Should have Header component', () => {
        const wrapper = shallow(<Container classes={classes} theme={theme} />);
        expect(wrapper.find('WithStyles(Header)').dive().find('Header').exists()).toBeTruthy();
    });

    it('Should have Sidebar component', () => {
        const wrapper = shallow(<Container classes={classes} theme={theme} />);
        expect(wrapper.find('WithStyles(SideBar)').dive().find('SideBar').exists()).toBeTruthy();
    });
});