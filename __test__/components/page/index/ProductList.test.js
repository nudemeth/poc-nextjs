import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { ProductList } from '../../../../components/page/index/ProductList';

const classes = {};
const theme = {};
const products = [
    { id: 1 }, { id: 2 }, { id: 3 }
];

describe('ProductList component', () => {
    it('Should have 3 ProductList components', () => {
        const productList = shallow(<ProductList classes={classes} theme={theme} products={products} />);
        expect(productList.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 ProductItem components under each ProductList', () => {
        const wrapper = shallow(<ProductList classes={classes} theme={theme} products={products} />);
        const productList = wrapper.find('WithStyles(GridList)');
        const firstProductList = productList.first();
        const secondProductList = productList.at(1);
        const thirdProductList = productList.last();
        expect(firstProductList.find('WithStyles(ProductItem)').length).toEqual(3);
        expect(secondProductList.find('WithStyles(ProductItem)').length).toEqual(3);
        expect(thirdProductList.find('WithStyles(ProductItem)').length).toEqual(3);
    });
});