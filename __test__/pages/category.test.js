import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedCategory, { Category } from '../../pages/category';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('Category page', () => {
    it('Should show category list in Category page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<Category dispatch={spyDispatch} title="Category" />);
        sinon.assert.calledWith(spyDispatch, { type: "LOAD_CATEGORIES" });
    });
});

jest.mock('../../components/page/category/CategoryList', () => {
    return () => "<div>Mocked Category List</div>";
});

describe('Category page with Snapshot Testing', () => {
    it('Should show category list in Category page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<Category dispatch={spyDispatch} />);
        const tree = toJSON(wrapper);
        expect(tree).toMatchSnapshot();
    });
});