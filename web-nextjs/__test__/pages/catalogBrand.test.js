import React from 'react'
import Router from 'next/router'
import { shallow } from 'enzyme'
import toJSON from 'enzyme-to-json'
import sinon from 'sinon'
import { CatalogBrand } from '../../pages/catalogBrand'

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

describe('CatalogBrand page', () => {
    it('Should show catalogBrand list in CatalogBrand page', () => {
        const spyDispatch = sinon.spy()
        shallow(<CatalogBrand dispatch={spyDispatch} title='CatalogBrand' />)
        sinon.assert.calledWith(spyDispatch, { type: 'LOAD_CATALOG_BRANDS' })
    })
})

jest.mock('../../components/page/catalogBrand/CatalogBrandList', () => {
    return () => '<div>Mocked CatalogBrand List</div>'
})

describe('CatalogBrand page with Snapshot Testing', () => {
    it('Should show catalogBrand list in CatalogBrand page', () => {
        const spyDispatch = sinon.spy()
        const wrapper = shallow(<CatalogBrand dispatch={spyDispatch} />)
        const tree = toJSON(wrapper)
        expect(tree).toMatchSnapshot()
    })
})