import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { CategoryList } from '../../../../components/page/category/CategoryList';

const classes = {};
const theme = {};
const categories = [
    { id: 1 }, { id: 2 }, { id: 3 }
]

describe('CategoryList component', () => {
    it('Should have 3 CategoryList components', () => {
        const wrapper = shallow(<CategoryList classes={classes} theme={theme} categories={categories} />);
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 CategoryItem components under each CategoryList', () => {
        const wrapper = shallow(<CategoryList classes={classes} theme={theme} categories={categories} />);
        const elements = wrapper.find('WithStyles(GridList)');
        expect(elements.first().find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
        expect(elements.at(1).find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
        expect(elements.last().find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
    });
});