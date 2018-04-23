import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedProduct, { Product } from '../../pages/product';

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
        const wrapper = shallow(<Product dispatch={spyDispatch} url={url} product={stubProduct} />);
        expect(wrapper.find('h1').text()).toEqual('Product Name: Item 1');
        const elements = wrapper.find('li');
        expect(elements.at(0).text()).toEqual('Category: Category 1');
        expect(elements.at(1).text()).toEqual('Create Date: 2018-01-01T00:00:00.000Z');
        expect(elements.at(2).text()).toEqual('Image URL: https://material-ui-next.com/static/images/cards/paella.jpg');
        sinon.assert.calledWith(spyDispatch, { type: "LOAD_PRODUCT", payload: { id: 1 } });
    });
});

describe('Product page with Snapshot Testing', () => {
    it('Should show "Product Name: Item 1" in Product page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<Product dispatch={spyDispatch} url={url} product={stubProduct} />);
        const tree = toJSON(wrapper);
        expect(tree).toMatchSnapshot();
    });
});