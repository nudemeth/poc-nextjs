import React from 'react'
import Router from 'next/router'
import { shallow } from 'enzyme'
import toJSON from 'enzyme-to-json'
import sinon from 'sinon'
import { Index } from '../../pages/index'

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

describe('Index page', () => {
    it('Should show "Index Page: Prodcuts Page" in Index page', () => {
        const spyDispatch = sinon.spy()
        shallow(<Index dispatch={spyDispatch} title='Home' />)
        sinon.assert.calledWith(spyDispatch, { type: 'LOAD_ITEMS' })
    })
})

jest.mock('../../components/page/index/Catalog', () => {
    return () => '<div>Mocked Catalog</div>'
})

describe('Index page with Snapshot Testing', () => {
    it('Should show "Index Page: Prodcuts Page" in Index page', () => {
        const spyDispatch = sinon.spy()
        const wrapper = shallow(<Index dispatch={spyDispatch} title='Home' />)
        const tree = toJSON(wrapper)
        expect(tree).toMatchSnapshot()
    })
})