import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import renderer from 'react-test-renderer';
import Index from '../pages/index';

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

describe('Index page', () => {
    it('Should show "This is Index Page: ABC" in Index page', () => {
        const index = shallow(<Index greeting="ABC" />);
        expect(index.find('h1').text()).toEqual('This is Index Page: ABC');
    });
});

describe('Index page with Snapshot Testing', () => {
    it('Should show "This is Index Page: ABC" in Index page', () => {
        const component = renderer.create(<Index greeting="ABC" />);
        const tree = component.toJSON();
        expect(tree).toMatchSnapshot();
    });
});