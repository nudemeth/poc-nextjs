import React from 'react';
import { shallow } from 'enzyme';
import { ItemList } from '../../../../components/page/index/ItemList';

const classes = {};
const theme = {};
const items = [
    { id: 1 }, { id: 2 }, { id: 3 }
];

describe('ItemList component', () => {
    it('Should have 3 ItemList components', () => {
        const wrapper = shallow(<ItemList classes={classes} theme={theme} items={items} />);
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3);
    });

    it('Should have 3 ItemItem components under each ItemList', () => {
        const wrapper = shallow(<ItemList classes={classes} theme={theme} items={items} />);
        const elements = wrapper.find('WithStyles(GridList)');
        expect(elements.first().find('WithStyles(Item)').length).toEqual(3);
        expect(elements.at(1).find('WithStyles(Item)').length).toEqual(3);
        expect(elements.last().find('WithStyles(Item)').length).toEqual(3);
    });
});