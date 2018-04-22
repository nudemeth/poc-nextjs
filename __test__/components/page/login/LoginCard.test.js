import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { LoginCard } from '../../../../components/page/login/LoginCard';

const classes = {};
const theme = {};

describe('LoginCard component', () => {
    it('Should show "Sign in to your account" label', () => {
        const loginCard = shallow(<LoginCard classes={classes} theme={theme} />);
        expect(loginCard.find('WithStyles(Typography)').render().text()).toEqual('Sign in to your account');
    });

    it('Should show Username and Password textbox', () => {
        const loginCard = shallow(<LoginCard classes={classes} theme={theme} />);
        expect(loginCard.find('TextField').length).toEqual(2);
        expect(loginCard.find('TextField').first().prop('id')).toEqual('username');
        expect(loginCard.find('TextField').first().prop('label')).toEqual('Username');
        expect(loginCard.find('TextField').last().prop('id')).toEqual('password');
        expect(loginCard.find('TextField').last().prop('type')).toEqual('password');
        expect(loginCard.find('TextField').last().prop('label')).toEqual('Password');
    });

    it('Should show "Sign In" button', () => {
        const loginCard = shallow(<LoginCard classes={classes} theme={theme} />);
        expect(loginCard.find('WithStyles(Button)').first().render().text()).toEqual('Sign In');
    });

    it('Should show "Sign In With Github" button', () => {
        const loginCard = shallow(<LoginCard classes={classes} theme={theme} />);
        expect(loginCard.find('WithStyles(Button)').last().render().text()).toEqual('Sign In With Github');
    });
});