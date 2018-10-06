import React from 'react'
import { shallow } from 'enzyme'
import { Item } from '../../../../components/page/index/Item'

const classes = {}
const theme = {}
const item = {
    id: 1,
    name: 'Item Name'
}

describe('Item component', () => {
    it('Should show Item component', () => {
        const wrapper = shallow(<Item classes={classes} theme={theme} item={item} />)
        expect(wrapper.find('img').prop('src')).toEqual(`http://localhost:5000/api/v1/catalog/items/${item.id}/img`)
        expect(wrapper.find('img').prop('alt')).toEqual('Item Name')
        expect(wrapper.find('WithStyles(GridListTileBar)').prop('title')).toEqual('Item Name')
    })
})