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
        const wrapper = shallow(<CategoryItem classes={classes} theme={theme} category={category} />);
        expect(wrapper.find('WithStyles(Icon)').render().text()).toEqual('CategoryIcon');
        expect(wrapper.find('WithStyles(Typography)').render().text()).toEqual('CategoryName');
    });

    it('Should have isSelected state to be true', () => {
        const wrapper = shallow(<CategoryItem classes={classes} theme={theme} category={category} />);
        expect(wrapper.state('isSelected')).toBeTruthy();
    });

    it('Should toggle isSelected state when clicking on category', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<CategoryItem dispatch={spyDispatch} classes={classes} theme={theme} category={category} />);
        wrapper.find('WithStyles(IconButton)').simulate('click');
        expect(wrapper.state('isSelected')).toBeFalsy();
    });
});