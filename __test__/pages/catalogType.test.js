import React from 'react';
import Router from 'next/router'
import { shallow } from 'enzyme';
import toJSON from 'enzyme-to-json';
import sinon from 'sinon';
import ConnectedCatalogType, { CatalogType } from '../../pages/catalogType';

const mockedRouter = { prefetch: () => {} };
Router.router = mockedRouter;

describe('CatalogType page', () => {
    it('Should show catalogType list in CatalogType page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<CatalogType dispatch={spyDispatch} title="CatalogType" />);
        sinon.assert.calledWith(spyDispatch, { type: "LOAD_CATALOG_TYPES" });
    });
});

jest.mock('../../components/page/catalogType/CatalogTypeList', () => {
    return () => "<div>Mocked CatalogType List</div>";
});

describe('CatalogType page with Snapshot Testing', () => {
    it('Should show catalogType list in CatalogType page', () => {
        const spyDispatch = sinon.spy();
        const wrapper = shallow(<CatalogType dispatch={spyDispatch} />);
        const tree = toJSON(wrapper);
        expect(tree).toMatchSnapshot();
    });
});