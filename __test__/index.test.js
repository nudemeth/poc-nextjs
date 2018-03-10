import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedIndex, { Index } from '../pages/index';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('Index page', () => {
    it('Should show "This is Index Page: ABC" in Index page', () => {
        const spyDispatch = sinon.spy();
        const index = shallow(<Index dispatch={spyDispatch} greeting='ABC' />);
        expect(index.find('h1').text()).toEqual('This is Index Page: ABC');
        sinon.assert.calledWith(spyDispatch, { type: "UPDATE", gt: undefined });
    });
});

describe('Index page with Snapshot Testing', () => {
    it('Should show "This is Index Page: This is from client" in Index page', () => {
        const spyDispatch = sinon.spy();
        const index = shallow(<Index dispatch={spyDispatch} greeting='ABC' />);
        const tree = toJSON(index);
        expect(tree).toMatchSnapshot();
    });
});