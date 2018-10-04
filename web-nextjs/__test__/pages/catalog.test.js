import React from 'react'
import Router from 'next/router'
import { shallow } from 'enzyme'
import toJSON from 'enzyme-to-json'
import sinon from 'sinon'
import { Item } from '../../pages/item'

const mockedRouter = { prefetch: () => {} }
Router.router = mockedRouter

const stubItem = {
    id: 1,
    name: 'Item 1',
    catalogType: 'CatalogType 1',
    createDate: '2018-01-01T00:00:00.000Z',
    imageUrl: 'https://material-ui-next.com/static/images/cards/paella.jpg',
    imageAlt: 'Contemplative Reptile'
}

const url = {
    query: {
        id: stubItem.id
    }
}

describe('Item page', () => {
    it('Should show "Item Name: Item 1" in Item page', () => {
        const spyDispatch = sinon.spy()
        const wrapper = shallow(<Item dispatch={spyDispatch} url={url} item={stubItem} />)
        expect(wrapper.find('h1').text()).toEqual('Item Name: Item 1')
        const elements = wrapper.find('li')
        expect(elements.at(0).text()).toEqual('CatalogType: CatalogType 1')
        expect(elements.at(1).text()).toEqual('Create Date: 2018-01-01T00:00:00.000Z')
        expect(elements.at(2).text()).toEqual('Image URL: https://material-ui-next.com/static/images/cards/paella.jpg')
        sinon.assert.calledWith(spyDispatch, { type: 'LOAD_ITEM', payload: { id: 1 } })
    })
})

describe('Item page with Snapshot Testing', () => {
    it('Should show "Item Name: Item 1" in Item page', () => {
        const spyDispatch = sinon.spy()
        const wrapper = shallow(<Item dispatch={spyDispatch} url={url} item={stubItem} />)
        const tree = toJSON(wrapper)
        expect(tree).toMatchSnapshot()
    })
})