import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedIndex, { Index } from '../pages/index';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('Index page', () => {
    it('Should show "Index Page: Prodcuts Page" in Index page', () => {
        const spyDispatch = sinon.spy();
        const index = shallow(<Index dispatch={spyDispatch} title='Home' />);
        sinon.assert.calledWith(spyDispatch, { type: "LOAD_PRODUCTS" });
    });
});

jest.mock('../components/page/index/ProductList', () => {
    return () => "<div>Mocked Product List</div>";
});

describe('Index page with Snapshot Testing', () => {
    it('Should show "Index Page: Prodcuts Page" in Index page', () => {
        const spyDispatch = sinon.spy();
        const index = shallow(<Index dispatch={spyDispatch} title='Home' />);
        const tree = toJSON(index);
        expect(tree).toMatchSnapshot();
    });
});