import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { CategoryItem } from '../../../../components/page/category/CategoryItem';

const classes = {
    icon: '',
    selectedIcon: '',
    categoryTile: {},
    selectedCategoryTile: {},
    iconButtonRoot: {},
    iconButtonLabel: {}
}
const theme = {}
const category = {
    image: 'CategoryIcon',
    name: 'CategoryName',
    isSelected: true
}

describe('CategoryItem component', () => {
    it('Should show CategoryItem component', () => {
        const categoryItem = shallow(<CategoryItem classes={classes} theme={theme} category={category} />);
        expect(categoryItem.find('WithStyles(Icon)').render().text()).toEqual('CategoryIcon');
        expect(categoryItem.find('WithStyles(Typography)').render().text()).toEqual('CategoryName');
    });

    it('Should have isSelected state to be true', () => {
        const categoryItem = shallow(<CategoryItem classes={classes} theme={theme} category={category} />);
        expect(categoryItem.state('isSelected')).toBeTruthy();
    });

    it('Should toggle isSelected state when clicking on category', () => {
        const spyDispatch = sinon.spy();
        const categoryItem = shallow(<CategoryItem dispatch={spyDispatch} classes={classes} theme={theme} category={category} />);
        categoryItem.find('WithStyles(IconButton)').simulate('click');
        expect(categoryItem.state('isSelected')).toBeFalsy();
    });
});