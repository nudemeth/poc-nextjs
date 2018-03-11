import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedProduct, { Product } from '../pages/product';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

const stubProduct = {
    id: 1,
    name: "Item 1",
    category: "Category 1",
    createDate: "2018-01-01T00:00:00.000Z",
    imageUrl: "https://material-ui-next.com/static/images/cards/paella.jpg",
    imageAlt: "Contemplative Reptile"
}

const url = {
    query: {
        id: stubProduct.id
    }
}

describe('Product page', () => {
    it('Should show "Product Name: Item 1" in Product page', () => {
        const spyDispatch = sinon.spy();
        const product = shallow(<Product dispatch={spyDispatch} url={url} product={stubProduct} />);
        expect(product.find('h1').text()).toEqual('Product Name: Item 1');
        expect(product.find('li').at(0).text()).toEqual('Category: Category 1');
        expect(product.find('li').at(1).text()).toEqual('Create Date: 2018-01-01T00:00:00.000Z');
        expect(product.find('li').at(2).text()).toEqual('Image URL: https://material-ui-next.com/static/images/cards/paella.jpg');
        sinon.assert.calledWith(spyDispatch, { id: 1, type: "LOAD_PRODUCT" });
    });
});

describe('Product page with Snapshot Testing', () => {
    it('Should show "Product Name: Item 1" in Product page', () => {
        const spyDispatch = sinon.spy();
        const product = shallow(<Product dispatch={spyDispatch} url={url} product={stubProduct} />);
        const tree = toJSON(product);
        expect(tree).toMatchSnapshot();
    });
});