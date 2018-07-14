import React from 'react';
import { shallow } from 'enzyme';
import sinon from 'sinon';
import { CatalogBrandItem } from '../../../../components/page/catalogBrand/CatalogBrandItem';

const classes = {
    icon: '',
    selectedIcon: '',
    catalogBrandTile: {},
    selectedCatalogBrandTile: {},
    iconButtonRoot: {},
    iconButtonLabel: {}
}
const theme = {}
const catalogBrand = {
    image: 'CatalogBrandIcon',
    name: 'CatalogBrandName',
    isSelected: true
}

describe('CatalogBrandItem component', () => {
    it('Should show CatalogBrandItem component', () => {
        const wrapper = shallow(<CatalogBrandItem classes={classes} theme={theme} catalogBrand={catalogBrand} />);
        expect(wrapper.find('WithStyles(Icon)').render().text()).toEqual('CatalogBrandIcon');
        expect(wrapper.find('WithStyles(Typography)').render().text()).toEqual('CatalogBrandName');
    });

    it('Should have isSelected state to be true', () => {
        const wrapper = shallow(<CatalogBrandItem classes={classes} theme={theme} catalogBrand={catalogBrand} />);
        expect(wrapper.state('isSelected')).toBeTruthy();
    });

    it('Should toggle isSelected state when clicking on catalogBrand', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<CatalogBrandItem dispatch={spyDispatch} classes={classes} theme={theme} catalogBrand={catalogBrand} />);
        wrapper.find('WithStyles(IconButton)').simulate('click');
        expect(wrapper.state('isSelected')).toBeFalsy();
    });
});