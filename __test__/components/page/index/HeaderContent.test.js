import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import { HeaderContent } from '../../../../components/page/index/HeaderContent';

const classes = {};
const theme = {};

describe('HeaderContent component', () => {
    it('Should show Search component', () => {
        const headerContent = shallow(<HeaderContent classes={classes} theme={theme} />);
        expect(headerContent.find('WithStyles(Input)').prop('type')).toEqual('search');
    });
});