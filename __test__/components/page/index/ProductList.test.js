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
        const wrapper = shallow(<ProductList classes={classes} theme={theme} products={products} />);
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 ProductItem components under each ProductList', () => {
        const wrapper = shallow(<ProductList classes={classes} theme={theme} products={products} />);
        const elements = wrapper.find('WithStyles(GridList)');
        expect(elements.first().find('WithStyles(ProductItem)').length).toEqual(3);
        expect(elements.at(1).find('WithStyles(ProductItem)').length).toEqual(3);
        expect(elements.last().find('WithStyles(ProductItem)').length).toEqual(3);
    });
});