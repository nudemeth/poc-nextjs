import React from 'react';
import { shallow } from 'enzyme';
import { CatalogTypeList } from '../../../../components/page/catalogType/CatalogTypeList';

const classes = {};
const theme = {};
const catalogTypes = [
    { id: 1 }, { id: 2 }, { id: 3 }
]

describe('CatalogTypeList component', () => {
    it('Should have 3 CatalogTypeList components', () => {
        const wrapper = shallow(<CatalogTypeList classes={classes} theme={theme} catalogTypes={catalogTypes} />);
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 CatalogTypeItem components under each CatalogTypeList', () => {
        const wrapper = shallow(<CatalogTypeList classes={classes} theme={theme} catalogTypes={catalogTypes} />);
        const elements = wrapper.find('WithStyles(GridList)');
        expect(elements.first().find('Connect(WithStyles(CatalogTypeItem))').length).toEqual(3);
        expect(elements.at(1).find('Connect(WithStyles(CatalogTypeItem))').length).toEqual(3);
        expect(elements.last().find('Connect(WithStyles(CatalogTypeItem))').length).toEqual(3);
    });
});