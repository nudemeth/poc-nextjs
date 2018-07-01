import React from 'react';
import { shallow } from 'enzyme';
import { Header } from '../../../components/layout/Header';

const classes = {
    appBar: {},
    menuButton: {},
    flex: {}
}
const theme = {}

describe('Header component', () => {
    it('Should show child component', () => {
        const wrapper = shallow(<Header classes={classes} theme={theme}><h1>This is child component</h1></Header>);
        const element = wrapper.find('div');
        expect(element.children().find('h1').exists()).toBeTruthy();
        expect(element.children().text()).toEqual('This is child component');
    });

    it('Should show menu button', () => {
        const wrapper = shallow(<Header classes={classes} theme={theme}/>);
        expect(wrapper.find('WithStyles(Icon)').render().text()).toEqual('menu');
    });

    it('Should show login button', () => {
        const wrapper = shallow(<Header classes={classes} theme={theme}/>);
        expect(wrapper.find('WithStyles(Button)').render().text()).toEqual('Login');
    });
});