import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { ProductItem } from '../../../../components/page/index/ProductItem';

const classes = {};
const theme = {};
const product = {
    id: 1,
    name: 'Product Name',
    imageUrl: 'ProductImageUrl',
    imageAlt: 'ProductImageAlt'
};

describe('ProductItem component', () => {
    it('Should show ProductItem component', () => {
        const productItem = shallow(<ProductItem classes={classes} theme={theme} product={product} />);
        expect(productItem.find('img').prop('src')).toEqual('ProductImageUrl');
        expect(productItem.find('img').prop('alt')).toEqual('ProductImageAlt');
        expect(productItem.find('WithStyles(GridListTileBar)').prop('title')).toEqual('Product Name');
    });
});