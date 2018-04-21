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
        const categoryList = shallow(<CategoryList classes={classes} theme={theme} categories={categories} />);
        expect(categoryList.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 CategoryItem components under each CategoryList', () => {
        const wrapper = shallow(<CategoryList classes={classes} theme={theme} categories={categories} />);
        const categoryList = wrapper.find('WithStyles(GridList)');
        const firstCategoryList = categoryList.first();
        const secondCategoryList = categoryList.at(1);
        const thirdCategoryList = categoryList.last();
        expect(firstCategoryList.find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
        expect(secondCategoryList.find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
        expect(thirdCategoryList.find('Connect(WithStyles(CategoryItem))').length).toEqual(3);
    });
});