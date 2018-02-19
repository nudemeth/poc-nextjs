import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import renderer from 'react-test-renderer';
import sinon from 'sinon';
import ConnectedIndex, { Index } from '../pages/index';

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

describe('Index page', () => {
    it('Should show "This is Index Page: ABC" in Index page', () => {
        const spyDispatch = sinon.spy();
        const index = shallow(<Index dispatch={spyDispatch} greeting='ABC' />);
        expect(index.find('h1').text()).toEqual('This is Index Page: ABC');
        sinon.assert.calledWith(spyDispatch, { type: "UPDATE", gt: "This is from client" });
    });
});

describe('Index page with Snapshot Testing', () => {
    it('Should show "This is Index Page: This is from client" in Index page', () => {
        const component = renderer.create(<ConnectedIndex />);
        const tree = component.toJSON();
        expect(tree).toMatchSnapshot();
    });
});