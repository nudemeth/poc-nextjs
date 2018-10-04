import React from 'react'
import { shallow } from 'enzyme'
import { CatalogBrandList } from '../../../../components/page/catalogBrand/CatalogBrandList'

const classes = {}
const theme = {}
const catalogBrands = [
    { id: 1 }, { id: 2 }, { id: 3 }
]

describe('CatalogBrandList component', () => {
    it('Should have 3 CatalogBrandList components', () => {
        const wrapper = shallow(<CatalogBrandList classes={classes} theme={theme} catalogBrands={catalogBrands} />)
        expect(wrapper.find('WithStyles(GridList)').length).toEqual(3)
    })

    it('Should have 3 CatalogBrandItem components under each CatalogBrandList', () => {
        const wrapper = shallow(<CatalogBrandList classes={classes} theme={theme} catalogBrands={catalogBrands} />)
        const elements = wrapper.find('WithStyles(GridList)')
        expect(elements.first().find('Connect(WithStyles(CatalogBrandItem))').length).toEqual(3)
        expect(elements.at(1).find('Connect(WithStyles(CatalogBrandItem))').length).toEqual(3)
        expect(elements.last().find('Connect(WithStyles(CatalogBrandItem))').length).toEqual(3)
    })
})