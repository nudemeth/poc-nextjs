import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import renderer from 'react-test-renderer';
import Index from '../pages/index';

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

describe('Index page', () => {
    it('Should show "This is Index Page: " in Index page', () => {
        const index = shallow(<Index />);
        expect(index.find('h1').text()).toEqual('This is Index Page: ');
    });
});

describe('Index page with Snapshot Testing', () => {
    it('Should show "This is Index Page: " in Index page', () => {
        const component = renderer.create(<Index />);
        const tree = component.toJSON();
        expect(tree).toMatchSnapshot();
    });
});