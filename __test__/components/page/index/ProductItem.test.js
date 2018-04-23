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
        const wrapper = shallow(<ProductItem classes={classes} theme={theme} product={product} />);
        expect(wrapper.find('img').prop('src')).toEqual('ProductImageUrl');
        expect(wrapper.find('img').prop('alt')).toEqual('ProductImageAlt');
        expect(wrapper.find('WithStyles(GridListTileBar)').prop('title')).toEqual('Product Name');
    });
});