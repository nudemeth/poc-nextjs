import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedAbout, { About } from '../../pages/about';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('About page', () => {
    it('Should show "This is About Page: ABC" in About page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<About dispatch={spyDispatch} greeting='ABC' />);
        expect(wrapper.find('h1').text()).toEqual('This is About Page: ABC');
        sinon.assert.calledWith(spyDispatch, { type: "UPDATE", gt: undefined });
    });
});

describe('About page with Snapshot Testing', () => {
    it('Should show "This is About Page: This is from client" in About page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<About dispatch={spyDispatch} greeting='ABC' />);
        const tree = toJSON(wrapper);
        expect(tree).toMatchSnapshot();
    });
});