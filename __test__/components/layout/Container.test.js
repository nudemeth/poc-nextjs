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
        const container = shallow(<Container classes={classes} theme={theme}><h1>This is child component</h1></Container>);
        expect(container.find('main').exists()).toBeTruthy();
        expect(container.find('main').children().find('h1').exists()).toBeTruthy();
        expect(container.find('main').text()).toEqual('This is child component');
    });

    it('Should show title', () => {
        const container = shallow(<Container classes={classes} theme={theme} title="This is title" />);
        expect(container.find('title').exists()).toBeTruthy();
        expect(container.find('title').text()).toEqual('This is title');
    });

    it('Should have Header component', () => {
        const container = shallow(<Container classes={classes} theme={theme} />);
        expect(container.find('WithStyles(Header)').dive().find('Header').exists()).toBeTruthy();
    });

    it('Should have Sidebar component', () => {
        const container = shallow(<Container classes={classes} theme={theme} />);
        expect(container.find('WithStyles(SideBar)').dive().find('SideBar').exists()).toBeTruthy();
    });
});