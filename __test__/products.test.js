import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedProducts, { Products } from '../pages/products';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('Products page', () => {
    it('Should show "Products Page: Prodcuts Page" in Products page', () => {
        const spyDispatch = sinon.spy();
        const products = shallow(<Products dispatch={spyDispatch} text='Products Page' />);
        expect(products.find('h1').text()).toEqual('Products Page: Products Page');
        sinon.assert.calledWith(spyDispatch, { type: "LOAD_PRODUCTS" });
    });
});

jest.mock('../components/page/products/ProductList', () => {
    return () => "<div>Mocked Product List</div>";
});

describe('Products page with Snapshot Testing', () => {
    it('Should show "Products Page: Prodcuts Page" in Products page', () => {
        const spyDispatch = sinon.spy();
        const products = shallow(<Products dispatch={spyDispatch} text='Products Page' />);
        const tree = toJSON(products);
        expect(tree).toMatchSnapshot();
    });
});