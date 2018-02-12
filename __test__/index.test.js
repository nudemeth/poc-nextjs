import { shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer';
import Index from '../pages/index';

describe('Index page', () => {
    it('Should shows "This is Index Page: " in Index page', () => {
        const index = shallow(<Index />);
        expect(index.find('h1').text()).toEqual('This is Index Page: ');
    });
});