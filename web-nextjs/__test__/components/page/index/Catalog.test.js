import React from 'react'
import { shallow } from 'enzyme'
import { Catalog } from '../../../../components/page/index/Catalog'

const classes = {}
const theme = {}
const items = [
    { id: 1 }, { id: 2 }, { id: 3 }
]

describe('Catalog component', () => {
    it('Should have 3 Catalog components', () => {
        const wrapper = shallow(<Catalog classes={classes} theme={theme} items={items} />)
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3)
    })

    it('Should have 3 ItemItem components under each Catalog', () => {
        const wrapper = shallow(<Catalog classes={classes} theme={theme} items={items} />)
        const elements = wrapper.find('WithStyles(GridList)')
        expect(elements.first().find('WithStyles(Item)').length).toEqual(3)
        expect(elements.at(1).find('WithStyles(Item)').length).toEqual(3)
        expect(elements.last().find('WithStyles(Item)').length).toEqual(3)
    })
})