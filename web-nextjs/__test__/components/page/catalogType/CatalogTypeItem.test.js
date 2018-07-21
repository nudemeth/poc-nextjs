import React from 'react';
import { shallow } from 'enzyme';
import sinon from 'sinon';
import { CatalogTypeItem } from '../../../../components/page/catalogType/CatalogTypeItem';

const classes = {
    icon: '',
    selectedIcon: '',
    catalogTypeTile: {},
    selectedCatalogTypeTile: {},
    iconButtonRoot: {},
    iconButtonLabel: {}
}
const theme = {}
const catalogType = {
    icon: 'CatalogTypeIcon',
    type: 'CatalogTypeName',
    isSelected: true
}

describe('CatalogTypeItem component', () => {
    it('Should show CatalogTypeItem component', () => {
        const wrapper = shallow(<CatalogTypeItem classes={classes} theme={theme} catalogType={catalogType} />);
        expect(wrapper.find('WithStyles(Icon)').render().text()).toEqual('CatalogTypeIcon');
        expect(wrapper.find('WithStyles(Typography)').render().text()).toEqual('CatalogTypeName');
    });

    it('Should have isSelected state to be true', () => {
        const wrapper = shallow(<CatalogTypeItem classes={classes} theme={theme} catalogType={catalogType} />);
        expect(wrapper.state('isSelected')).toBeTruthy();
    });

    it('Should toggle isSelected state when clicking on catalogType', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<CatalogTypeItem dispatch={spyDispatch} classes={classes} theme={theme} catalogType={catalogType} />);
        wrapper.find('WithStyles(IconButton)').simulate('click');
        expect(wrapper.state('isSelected')).toBeFalsy();
    });
});