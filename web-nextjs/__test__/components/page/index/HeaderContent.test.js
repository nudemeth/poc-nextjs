import React from 'react';
import { shallow } from 'enzyme';
import { HeaderContent } from '../../../../components/page/index/HeaderContent';

const classes = {};
const theme = {};

describe('HeaderContent component', () => {
    it('Should show Search component', () => {
        const wrapper = shallow(<HeaderContent classes={classes} theme={theme} />);
        expect(wrapper.find('WithStyles(Input)').prop('type')).toEqual('search');
    });
});