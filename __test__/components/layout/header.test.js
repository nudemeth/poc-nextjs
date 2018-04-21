import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { Header } from '../../../components/layout/Header';

const classes = {
    appBar: {},
    menuButton: {},
    flex: {}
}
const theme = {}

describe('Header component', () => {
    it('Should show child component', () => {
        const header = shallow(<Header classes={classes} theme={theme}><h1>This is child component</h1></Header>);
        expect(header.find('div').children().find('h1').exists()).toBeTruthy();
        expect(header.find('div').children().text()).toEqual('This is child component');
    });

    it('Should show menu button', () => {
        const header = shallow(<Header classes={classes} theme={theme}/>);
        expect(header.find('WithStyles(Icon)').render().text()).toEqual('menu');
    });

    it('Should show login button', () => {
        const header = shallow(<Header classes={classes} theme={theme}/>);
        expect(header.find('WithStyles(Button)').render().text()).toEqual('Login');
    });
});